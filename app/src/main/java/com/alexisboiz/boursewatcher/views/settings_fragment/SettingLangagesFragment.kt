package com.alexisboiz.boursewatcher.views.settings_fragment

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.os.LocaleList
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.cardview.widget.CardView
import androidx.core.os.LocaleListCompat
import com.alexisboiz.boursewatcher.MainActivity
import com.alexisboiz.boursewatcher.R
import com.alexisboiz.boursewatcher.databinding.FragmentSettingLangagesBinding
import com.alexisboiz.boursewatcher.utils.LocaleManager
import com.google.android.gms.common.internal.Constants
import com.google.android.gms.common.internal.service.Common
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Locale

class SettingLangagesFragment : BottomSheetDialogFragment() {
    lateinit var binding: FragmentSettingLangagesBinding
    private var localeManager: LocaleManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingLangagesBinding.inflate(inflater, container, false)
        val close = binding.root.findViewById<CardView>(R.id.close)

        close.setOnClickListener(){
            dismiss()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val radioGroup = binding.root.findViewById<RadioGroup>(R.id.rg_langages)
        val sharedPreference =  requireActivity().getSharedPreferences("LANGUAGE", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        var language : String =""

        radioGroup.check(R.id.rb_french)
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.rb_french -> {
                    language = "fr"
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.main_fragment, SettingFragment.newInstance())
                        .commit()
                }
                R.id.rb_uk -> {
                    editor.putString("LANGUAGE", "en")
                    LocaleManager.setLocale(requireActivity(), "en")
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.main_fragment, SettingFragment.newInstance())
                        .commit()
                }
                R.id.rb_china -> {
                    editor.putString("LANGUAGE", "zh")
                    LocaleManager.setLocale(requireActivity(), "zh")
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.main_fragment, SettingFragment.newInstance())
                        .commitNow()
                }
                R.id.rb_spain -> {
                    editor.putString("LANGUAGE", "es")
                    LocaleManager.setLocale(requireActivity(), "es")
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.main_fragment, SettingFragment.newInstance())
                        .commit()
                }
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                context?.let {
                    context?.getSystemService(LocaleManager::class.java)
                        ?.setLocale(it,"fr")
                }
            } else {
                AppCompatDelegate.setApplicationLocales(
                    LocaleListCompat.forLanguageTags(
                        "fr"
                    )
                )
            }
            editor.apply()
        }

        val bottomSheet : FrameLayout = dialog?.findViewById(com.google.android.material.R.id.design_bottom_sheet)!!
        bottomSheet.layoutParams.height = 1000

        // Behavior of the bottom sheet
        val behavior = BottomSheetBehavior.from(bottomSheet)
        behavior.apply {
            peekHeight = resources.displayMetrics.heightPixels // Pop-up height
            state = BottomSheetBehavior.STATE_EXPANDED // Expanded state

            addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) = Unit

                override fun onSlide(bottomSheet: View, slideOffset: Float) = Unit
            })
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SettingLangagesFragment()
    }

}