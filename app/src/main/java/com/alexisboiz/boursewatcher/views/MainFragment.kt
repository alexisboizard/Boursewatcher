package com.alexisboiz.boursewatcher.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alexisboiz.boursewatcher.R
import com.google.android.material.button.MaterialButton

class MainFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val walletDetail = view.findViewById<MaterialButton>(R.id.wallet_details)
        walletDetail.setOnClickListener(){
            val fragment = WalletFragment.newInstance()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.main_fragment, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MainFragment()
    }
}