package com.alexisboiz.boursewatcher.views.friends_fragment

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.alexisboiz.boursewatcher.R
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import io.github.g0dkar.qrcode.QRCode
import java.io.ByteArrayOutputStream

class FriendQrFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friend_qr, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val friendCode = view.findViewById<TextView>(R.id.friend_code)
        val userId = Firebase.auth.currentUser!!.uid



        Firebase.firestore.collection("users").document(userId).get()
            .addOnSuccessListener {
                friendCode.text = it["friendCode"].toString()
                val bitmap = BitmapFactory.decodeStream(createQR(it["friendCode"].toString()).inputStream())
                view.findViewById<ImageView>(R.id.qr_image).setImageBitmap(bitmap)
            }
            .addOnFailureListener {
                Log.e("FriendQrFragment", "Error getting friend code", it)
            }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            FriendQrFragment()
    }

    fun createQR(content : String) : ByteArray{
        val qrCode = QRCode(content).render(30, margin=30)
        return ByteArrayOutputStream().also { qrCode.writeImage(it) }.toByteArray()
    }
}