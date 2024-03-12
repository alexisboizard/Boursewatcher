package com.alexisboiz.boursewatcher.views.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.alexisboiz.boursewatcher.R
import com.alexisboiz.boursewatcher.domain.datasource.DistantDataSource
import com.alexisboiz.boursewatcher.utils.UserError
import com.alexisboiz.boursewatcher.views.MainFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val email = this.findViewById<TextInputLayout>(R.id.til_email)
        val email_input = this.findViewById<EditText>(R.id.et_email)
        val password_input = this.findViewById<EditText>(R.id.et_password)
        val login_button = this.findViewById<MaterialButton>(R.id.submit_button)
        val haveNotAccount = this.findViewById<TextView>(R.id.tv_not_registered)

        email_input.setOnFocusChangeListener{
                _, hasFocus ->
            if(!hasFocus){
                if(email_input.text.toString().isEmpty()){
                    email_input.error = "Veuillez entrer votre email"
                    login_button.isEnabled = false
                }
                else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email_input.text.toString()).matches()){
                    email.endIconMode = TextInputLayout.END_ICON_NONE
                    email_input.error = "Veuillez entrer un email valide"
                    login_button.isEnabled = false
                    login_button.setBackgroundColor(getResources().getColor(R.color.seed, null))
                }else{
                    email.endIconMode = TextInputLayout.END_ICON_CLEAR_TEXT
                    email_input.error = null
                    login_button.isEnabled = true
                }
            }
        }

        password_input.setOnFocusChangeListener{
                _,hasFocus->
            if(!hasFocus){
                if(password_input.text.toString().isEmpty()){
                    password_input.error = "Veuillez entrer votre email"
                    login_button.isEnabled = false
                }else{
                    email.endIconMode = TextInputLayout.END_ICON_CLEAR_TEXT
                    email_input.error = null
                    login_button.isEnabled = true
                }
            }
        }

        login_button.setOnClickListener(){
            val email : String = email_input.text.toString()
            val password : String = password_input.text.toString()
            DistantDataSource.firebaseService.login(email, password).apply {
                if(this.errors.isNotEmpty()){
                    for(error in this.errors!!){
                        when(error){
                            UserError.USER_NOT_FOUND -> {
                                Toast.makeText(this@LoginActivity, "Utilisateur non trouvé", Toast.LENGTH_SHORT).show()
                            }
                            UserError.WRONG_PASSWORD -> {
                                Toast.makeText(this@LoginActivity, "Mot de passe incorrect", Toast.LENGTH_SHORT).show()
                            }
                            UserError.UNKNOWN_ERROR -> {
                                Toast.makeText(this@LoginActivity, "Erreur inconnue", Toast.LENGTH_SHORT).show()
                            }
                            else -> {
                                Toast.makeText(this@LoginActivity, "Erreur inconnue", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }else{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fragment, MainFragment.newInstance())
                        .commitNow()
                }
            }
        }

        haveNotAccount.setOnClickListener(){
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }


}