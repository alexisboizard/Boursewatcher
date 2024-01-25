package com.alexisboiz.boursewatcher.views.settings_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.cardview.widget.CardView
import com.alexisboiz.boursewatcher.R
import com.alexisboiz.boursewatcher.databinding.FragmentSettingExchangeBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SettingExchangeFragment : BottomSheetDialogFragment() {
    lateinit var binding: FragmentSettingExchangeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingExchangeBinding.inflate(inflater, container, false)
        val close = binding.root.findViewById<CardView>(R.id.close)

        close.setOnClickListener(){
            dismiss()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomSheet : FrameLayout = dialog?.findViewById(com.google.android.material.R.id.design_bottom_sheet)!!
        bottomSheet.layoutParams.height = 1000

        // Behavior of the bottom sheet
        val behavior = BottomSheetBehavior.from(bottomSheet)
        behavior.apply {
            peekHeight = resources.displayMetrics.heightPixels // Pop-up height
            state = BottomSheetBehavior.STATE_EXPANDED // Expanded state

            addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {

                }
            })
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SettingExchangeFragment()
    }
}