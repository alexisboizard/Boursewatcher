package com.alexisboiz.boursewatcher.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.graphics.RectF
import android.util.Log
import android.widget.Toast
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alexisboiz.boursewatcher.model.UsersModel.UserDetail
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import retrofit2.converter.gson.GsonConverterFactory

class QrCodeAnalyzer(
    private val context: Context,
    private val barcodeBoxView: BarcodeBoxView,
    private val previewViewWidth: Float,
    private val previewViewHeight: Float
): ImageAnalysis.Analyzer {
    companion object {
        private var _processStatusLiveData : MutableLiveData<String> = MutableLiveData()
        val processStatusLiveData : LiveData<String> = _processStatusLiveData
    }
    private var scaleX = 1f
    private var scaleY = 1f

    private fun translateX(x: Float) = x * scaleX
    private fun translateY(y: Float) = y * scaleY

    private fun adjustBoundingRect(rect: Rect) = RectF(
        translateX(rect.left.toFloat()),
        translateY(rect.top.toFloat()),
        translateX(rect.right.toFloat()),
        translateY(rect.bottom.toFloat())
    )

    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(image: ImageProxy) {
        val img = image.image
        if (img != null) {
            // Update scale factors
            scaleX = previewViewWidth / img.height.toFloat()
            scaleY = previewViewHeight / img.width.toFloat()

            val inputImage = InputImage.fromMediaImage(img, image.imageInfo.rotationDegrees)

            // Process image searching for barcodes
            val options = BarcodeScannerOptions.Builder()
                .build()

            val scanner = BarcodeScanning.getClient(options)

            scanner.process(inputImage)
                .addOnSuccessListener { barcodes ->
                    if (barcodes.isNotEmpty()) {
                        for (barcode in barcodes) {
                            // Handle received barcodes
                            addFriend(barcode.rawValue.toString(),image)
                            // Update bounding rect
                            barcode.boundingBox?.let { rect ->
                                barcodeBoxView.setRect(
                                    adjustBoundingRect(
                                        rect
                                    )
                                )
                            }
                        }
                    } else {
                        // Remove bounding rect
                        barcodeBoxView.setRect(RectF())
                    }
                }
                .addOnFailureListener { }
        }

        image.close()
    }
    fun addFriend(friendCode : String, image: ImageProxy){
        val firestore = Firebase.firestore
        val friendRef = firestore.collection("users")
        val uid = Firebase.auth.currentUser!!.uid
        var displayName = ""
        friendRef.get()
            .addOnSuccessListener {
                for (document in it) {
                    if (document.data["friendCode"] == friendCode) {
                        displayName = document.data["displayName"].toString()
                        val friendId = document.id
                        val userFriendList = firestore.collection("users/${uid}/friendList")
                        userFriendList.get()
                            .addOnSuccessListener {
                                var friendExist = false
                                for (document2 in it) {
                                    if (document2.data["friendId"] == friendId) {
                                        friendExist = true
                                    }
                                }
                                if (!friendExist) {
                                    val friend = hashMapOf(
                                        "status" to "not_accepted",
                                        "friendId" to friendId
                                    )
                                    userFriendList.add(friend)
                                }
                            }
                        // ajout dans la liste de l'amis
                        val friendList = firestore.collection("users/${friendId}/friendList")
                        friendList.get()
                            .addOnSuccessListener {
                                var friendExist = false
                                for (document2 in it) {
                                    if (document2.data["friendId"] == uid) {
                                        friendExist = true
                                    }
                                }
                                if (!friendExist) {
                                    val friend = hashMapOf(
                                        "status" to "pending",
                                        "friendId" to uid
                                    )
                                    friendList.add(friend)
                                }
                                image.close()
                                _processStatusLiveData.postValue(displayName)
                            }
                    }
                }
            }
    }

}