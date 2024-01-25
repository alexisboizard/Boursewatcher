package com.alexisboiz.boursewatcher.views.settings_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.alexisboiz.boursewatcher.R
import com.alexisboiz.boursewatcher.databinding.FragmentSettingModeBinding
import com.alexisboiz.boursewatcher.databinding.FragmentSettingProfileBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout

class SettingProfileFragment : BottomSheetDialogFragment() {
    lateinit var binding: FragmentSettingProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingProfileBinding.inflate(inflater, container, false)
        val close = binding.root.findViewById<CardView>(R.id.close)

        close.setOnClickListener(){
            dismiss()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottomSheet : FrameLayout = dialog?.findViewById(com.google.android.material.R.id.design_bottom_sheet)!!
        bottomSheet.layoutParams.height = 1500

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

        val et_display_name = binding.root.findViewById<EditText>(R.id.et_display_name)
        val et_email = binding.root.findViewById<EditText>(R.id.et_email)
        val til_display_name = binding.root.findViewById<TextInputLayout>(R.id.til_display_name)
        val til_email = binding.root.findViewById<TextInputLayout>(R.id.til_email)
        val display_name = binding.root.findViewById<TextView>(R.id.display_name)
        val email = binding.root.findViewById<TextView>(R.id.email)
        val validate = binding.root.findViewById<MaterialButton>(R.id.btn_validate_profile)
        val cancel = binding.root.findViewById<MaterialButton>(R.id.btn_cancel_profile)
        val editProfile = binding.root.findViewById<MaterialButton>(R.id.btn_edit_profile)

        editProfile.setOnClickListener{
            display_name.visibility = View.INVISIBLE
            email.visibility = View.INVISIBLE
            editProfile.visibility = View.INVISIBLE

            til_display_name.visibility = View.VISIBLE
            til_email.visibility = View.VISIBLE
            validate.visibility = View.VISIBLE
            cancel.visibility = View.VISIBLE
        }
        validate.setOnClickListener{

        }
        cancel.setOnClickListener{
            display_name.visibility = View.VISIBLE
            email.visibility = View.VISIBLE
            editProfile.visibility = View.VISIBLE

            til_email.visibility = View.INVISIBLE
            til_display_name.visibility = View.INVISIBLE
            validate.visibility = View.INVISIBLE
            cancel.visibility = View.INVISIBLE
        }


    }
    companion object {
        @JvmStatic
        fun newInstance() =
            SettingModeFragment()
    }
}