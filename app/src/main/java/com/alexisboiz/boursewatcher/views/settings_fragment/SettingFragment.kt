package com.alexisboiz.boursewatcher.views.settings_fragment

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import com.alexisboiz.boursewatcher.MainActivity
import com.alexisboiz.boursewatcher.R
import com.alexisboiz.boursewatcher.databinding.FragmentSettingBinding
import com.alexisboiz.boursewatcher.domain.datasource.DistantDataSource
import com.alexisboiz.boursewatcher.views.auth.LoginFragment
import com.bumptech.glide.Glide
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import pub.devrel.easypermissions.EasyPermissions

class SettingFragment : Fragment(){
    private lateinit var auth: FirebaseAuth
    private lateinit var storage: FirebaseStorage
    private lateinit var binding : FragmentSettingBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSettingBinding.inflate(layoutInflater)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            val profil = view.findViewById<ConstraintLayout>(R.id.to_profile)
            profil.setOnClickListener {
                val bottomSheet = SettingProfileFragment()
                parentFragmentManager.let { bottomSheet.show(it,"SettingProfileFragment") }
            }
            val exchange = view.findViewById<ConstraintLayout>(R.id.to_default_exchange)
            exchange.setOnClickListener {
                val bottomSheet = SettingExchangeFragment()
                parentFragmentManager.let { bottomSheet.show(it,"SettingExchangeFragment") }
            }

            val currency = view.findViewById<ConstraintLayout>(R.id.to_default_currency)
            currency.setOnClickListener {
                val bottomSheet = SettingCurrencyFragment()
                parentFragmentManager.let { bottomSheet.show(it,"SettingCurrencyFragment") }
            }

            val mode = view.findViewById<ConstraintLayout>(R.id.to_mode)
            mode.setOnClickListener {
                val bottomSheet = SettingModeFragment()
                parentFragmentManager.let { bottomSheet.show(it,"SettingModeFragment") }
            }

            val langagesButton = view.findViewById<ConstraintLayout>(R.id.to_langages)
            langagesButton.setOnClickListener {
                val bottomSheet = SettingLangagesFragment()
                parentFragmentManager.let { bottomSheet.show(it,"SettingLangagesFragment") }
            }
        }

        val faq = view.findViewById<ConstraintLayout>(R.id.to_faq)
        faq.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.main_fragment, SettingFAQFragment.newInstance())
                commit()
            }

        }
        val logoutButton = view.findViewById<ConstraintLayout>(R.id.to_logout)
        logoutButton.setOnClickListener {
            Firebase.auth.signOut()
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.main_fragment, LoginFragment.newInstance())
                commit()
            }
        }

        // Update UI with user information
        val displayname = view.findViewById<TextView>(R.id.tv_name)
        val avatar = view.findViewById<ImageView>(R.id.profil_picture)
        DistantDataSource.auth.currentUser?.uid?.let { it1 -> Log.e("Main", it1) }

        DistantDataSource.firebaseService.getUserAvatar().downloadUrl.addOnSuccessListener {
            displayname.text = DistantDataSource.auth.currentUser?.displayName
            // TODO : Fix crash
            Glide.with(requireContext())
                .load(it)
                .into(avatar)
        }

        val notificationSwitch = view.findViewById<MaterialSwitch>(R.id.switch_notifications)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val packageManager = requireActivity().packageManager
            notificationSwitch.isChecked = packageManager.checkPermission(
                Manifest.permission.POST_NOTIFICATIONS,
                requireActivity().packageName
            ) == PackageManager.PERMISSION_GRANTED
        }
        notificationSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    requestNotificationPermissions()
                }else{
                    //TODO: add notification permission for android 10 and below
                }
            }else
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    val packageManager = requireActivity().packageManager
                    packageManager.removePermission(Manifest.permission.POST_NOTIFICATIONS)
                }else{
                    //TODO: add notification permission for android 10 and below
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SettingFragment()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestNotificationPermissions() {
        val perm = Manifest.permission.POST_NOTIFICATIONS
        if(EasyPermissions.hasPermissions(requireContext(), perm)) {
            // Already have permission, do the thing
            // ...
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "Please grant the notification permission", 1, perm)
        }
    }


}