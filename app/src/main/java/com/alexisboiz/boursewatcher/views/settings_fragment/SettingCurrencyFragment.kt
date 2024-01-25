package com.alexisboiz.boursewatcher.views.settings_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RadioGroup
import androidx.cardview.widget.CardView
import com.alexisboiz.boursewatcher.R
import com.alexisboiz.boursewatcher.databinding.FragmentSettingCurrencyBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SettingCurrencyFragment : BottomSheetDialogFragment() {
    lateinit var binding: FragmentSettingCurrencyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingCurrencyBinding.inflate(inflater, container, false)
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

        val radioGroup = binding.root.findViewById<RadioGroup>(R.id.rg_currency)
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.rb_1 -> {

                }
                R.id.rb_2 -> {

                }
                R.id.rb_3 -> {

                }
                R.id.rb_4 -> {

                }
            }
        }

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
            SettingCurrencyFragment()
    }
}