package com.alexisboiz.boursewatcher.views

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.alexisboiz.boursewatcher.R
import com.alexisboiz.boursewatcher.domain.TradedAssetRepository
import io.github.g0dkar.qrcode.QRCode
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream

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
        val tradedAssetList = TradedAssetRepository(requireContext()).getAllTradedAsset()
        var jsonTradedAsset : String = "["

        for (tradedAsset in tradedAssetList) {
            jsonTradedAsset +=
                   "{\"symbol\": \"${tradedAsset.symbol}\","+
                    "\"purshasedPrice\": \"${tradedAsset.purshasedPrice}\"," +
                    "\"quantity\": \"${tradedAsset.quantity}\","+
                    "\"purshasedDate\": \"${tradedAsset.purshasedDate}\"},"
        }
        jsonTradedAsset += "]"

        if(jsonTradedAsset == "[]"){
            jsonTradedAsset = "{" +
                    "\"symbol\": null," +
                    "\"purshasedPrice\": null," +
                    "\"quantity\": null," +
                    "\"purshasedDate\": null }"

        }
        jsonTradedAsset = jsonTradedAsset.replace("},]", "}]")
        Log.e("jsonTradedAsset", jsonTradedAsset)

        createQR(jsonTradedAsset)

        val bitmap = BitmapFactory.decodeStream(createQR(jsonTradedAsset).inputStream())

        view.findViewById<ImageView>(R.id.qr_image).setImageBitmap(bitmap)
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