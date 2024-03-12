package com.alexisboiz.boursewatcher.views.auth

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.cardview.widget.CardView
import androidx.fragment.app.activityViewModels
import com.alexisboiz.boursewatcher.MainActivity
import com.alexisboiz.boursewatcher.R
import com.alexisboiz.boursewatcher.domain.datasource.DistantDataSource
import com.alexisboiz.boursewatcher.utils.UserError
import com.alexisboiz.boursewatcher.views.MainFragment
import com.alexisboiz.boursewatcher.views.friends_fragment.FriendFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import pub.devrel.easypermissions.EasyPermissions

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var storage: FirebaseStorage
    val authViewModel by viewModels<AuthViewModel>()


    val REQUEST_CODE_EXTERNAL_STORAGE_PERMISSION = 1002
    lateinit var avatar : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val email_input = this.findViewById<TextInputEditText>(R.id.et_email_register)
        val password_input = this.findViewById<EditText>(R.id.et_password_register)
        val name_input = this.findViewById<TextInputEditText>(R.id.et_lastname)
        val firstname_input = this.findViewById<TextInputEditText>(R.id.et_firstname)
        val register_button = this.findViewById<MaterialButton>(R.id.submit_button)
        val avatar_container = this.findViewById<CardView>(R.id.avatar_container)
        val strength_bar = this.findViewById<ProgressBar>(R.id.password_strength)
        avatar = this.findViewById(R.id.avatar)
        val haveAccount = this.findViewById<TextView>(R.id.tv_already_registered)

        password_input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val strength = passwordStrengthMeter(password_input.text.toString())
                Log.e("Stre", strength.toString())
                register_button.isEnabled = false
                when(strength){
                    -1-> {
                        strength_bar.progress = 0
                        strength_bar.progressTintList = (ColorStateList.valueOf(Color.parseColor("#F0453A")))
                    }
                    1 -> {
                        strength_bar.progress = 25
                        strength_bar.progressTintList = (ColorStateList.valueOf(Color.parseColor("#F0453A")))
                    }
                    2 -> {
                        strength_bar.progress = 50
                        strength_bar.progressTintList = (ColorStateList.valueOf(Color.parseColor("#FF9800")))
                    }
                    3 -> {
                        strength_bar.progress = 75
                        strength_bar.progressTintList = (ColorStateList.valueOf(Color.parseColor("#25C485")))
                        register_button.isEnabled = true
                    }
                    4 -> {
                        strength_bar.progress = 100
                        strength_bar.progressTintList = (ColorStateList.valueOf(Color.parseColor("#11DA00")))
                        register_button.isEnabled = true
                    }
                }
            }
        })

        avatar_container.setOnClickListener{
            requestPermission(this@RegisterActivity)
        }

        authViewModel.registerLiveData.observe(this){
            if(it.user != null){
                if(it.errors.isEmpty()){
                    MainActivity.connected = true
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.main_fragment, FriendFragment.newInstance())
                        commit()
                    }
                }
            }else{
                if(it.errors.isNotEmpty()){
                    it.errors.forEach { error ->
                        when(error){
                            UserError.EMAIL_ALREADY_USED -> {
                                Toast.makeText(
                                    this,
                                    "Email déjà utilisé",
                                    Toast.LENGTH_SHORT,
                                ).show()
                            }
                            UserError.EMAIL_NOT_VALID -> {
                                Toast.makeText(
                                    this,
                                    "Email non valide",
                                    Toast.LENGTH_SHORT,
                                ).show()
                            }
                            UserError.EMPTY_FIELD -> {
                                Toast.makeText(
                                    this,
                                    "Champ(s) vide(s)",
                                    Toast.LENGTH_SHORT,
                                ).show()
                            }
                            UserError.PROFILE_PICTURE_ERROR -> {
                                Toast.makeText(
                                    this,
                                    "Erreur lors de l'upload de l'image",
                                    Toast.LENGTH_SHORT,
                                ).show()
                            }
                            UserError.UNKNOWN_ERROR -> {
                                Toast.makeText(
                                    this,
                                    "Erreur inconnue",
                                    Toast.LENGTH_SHORT,
                                ).show()
                            }
                            else -> {
                                Toast.makeText(
                                    this,
                                    "Erreur inconnue",
                                    Toast.LENGTH_SHORT,
                                ).show()}
                        }
                    }
                }else{
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.main_fragment, MainFragment.newInstance())
                        commit()
                    }
                }
            }
        }
        register_button.setOnClickListener(){
            authViewModel.register(
                email_input.text.toString(),
                password_input.text.toString(),
                avatar,
                firstname_input.text.toString(),
                name_input.text.toString()
            )
        }

        haveAccount.setOnClickListener(){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
    fun hasStoragePermission(context: Context) =
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
            EasyPermissions.hasPermissions(
                context,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        } else {
            EasyPermissions.hasPermissions(
                context,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }

    private fun requestPermission(context : Context) {

        if (this.hasStoragePermission(context)) {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            openGallery.launch(intent)
            return
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            EasyPermissions.requestPermissions(
                this,
                "You need to accept the storage permission to have a profile picture",
                REQUEST_CODE_EXTERNAL_STORAGE_PERMISSION,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        } else {
            EasyPermissions.requestPermissions(
                this,
                "You need to accept the storage permission to have a profile picture",
                REQUEST_CODE_EXTERNAL_STORAGE_PERMISSION,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
    }

    private val openGallery =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                val data = it.data
                val imgUri = data?.data
                avatar.setImageURI(imgUri)
            }
        }
    private fun passwordStrengthMeter(password : String) : Int{
        if(password.isEmpty()){
            return -1
        }
        var strength = 0;
        val regexContainNumOrSymbBetweenLetter = "\\b[a-zA-Z0-9]+\\W[a-zA-Z0-9]+\\b".toRegex()
        val numRegex = "\\b[0-9]\\b".toRegex()
        var numberCounter = 0
        val lowerCharRegex = "\\b[a-z]\\b".toRegex()
        var lowerCharCounter = 0
        val upperCharRegex = "\\b[A-Z]\\b".toRegex()
        var upperCharCounter = 0
        val charSpe = arrayOf('!', '#', '$', '%', '&', '(', ')', '*', '+',',', '-', '.', '/', ':', ';', '<', '=', '>', '?', '@', '[', ']', '^', '_', '`', '{', '|', '}', '~' )
        var charSpeCounter = 0
        var middleNumOrCharSpeCounter = 0
        var haveOnlyLetter = true
        var haveOnlyDigits = true
        var lastChar = password[0]
        var deduction = 0
        var repeatCharCounter = 0
        var repeatLowerCounter = 0
        var repeatUpperCounter = 0
        var repeatNumberCounter = 0
        // Password Strength calcul by https://www.uic.edu/apps/strong-password/
        strength += password.length * 4

        for(i in password.indices){
            if(lastChar.uppercase() == password[i].uppercase()){
                repeatCharCounter++
            }else{
                strength -= 2*repeatCharCounter
                repeatCharCounter = 0
            }
            // Char is a number
            if(numRegex.matches(password[i].toString())){
                numberCounter++
                strength += numberCounter*4
                if(numRegex.matches(lastChar.toString())){
                    repeatNumberCounter++
                }
            }else{
                strength -= repeatNumberCounter*2
                repeatNumberCounter = 0
            }
            // Char is a symbol or num between letter TODO Modifier
            if(regexContainNumOrSymbBetweenLetter.matches(password[i].toString())){
                middleNumOrCharSpeCounter++
                strength += middleNumOrCharSpeCounter * 2
            }
            // Char is a char spe
            if(password[i] in charSpe){
                charSpeCounter++
                strength += charSpeCounter*6
            }
            // Char is uppercase
            if(upperCharRegex.matches(password[i].toString())){
                upperCharCounter++
                strength += (password.length - upperCharCounter)*2
                if(upperCharRegex.matches(lastChar.toString())){
                    repeatUpperCounter++
                }
            }else{
                strength -= repeatUpperCounter*2
                repeatUpperCounter = 0
            }
            // Char is lowercase
            if(lowerCharRegex.matches(password[i].toString())){
                lowerCharCounter++
                strength += (password.length - lowerCharCounter)*2
                if(lowerCharRegex.matches(lastChar.toString())){
                    repeatLowerCounter++
                }
            }else{
                strength -= repeatLowerCounter*2
                repeatLowerCounter = 0
            }
            if(lowerCharCounter + upperCharCounter == password.length){
                // only char (no digits)
                strength -= lowerCharCounter + upperCharCounter
            }else if (lowerCharCounter + upperCharCounter == 0){
                // no char (only digits)
                strength -= (lowerCharCounter + upperCharCounter) - password.length
            }
        }

        return getLevelByScore(strength)
    }

    private fun getLevelByScore(strengthLevel : Int) : Int{
        // Level
        // Weak -> Score >= 35
        // Good -> Score >= 50
        // Strong -> Score >= 80
        // Very Strong -> Score < 90
        return when(strengthLevel){
            in 0..35  -> 1
            in 35..50 -> 2
            in 51..80 -> 3
            else -> 4
        }
    }
}