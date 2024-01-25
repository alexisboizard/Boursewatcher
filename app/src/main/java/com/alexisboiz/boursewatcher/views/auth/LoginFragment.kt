package com.alexisboiz.boursewatcher.views.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.alexisboiz.boursewatcher.R
import com.alexisboiz.boursewatcher.domain.datasource.DistantDataSource
import com.alexisboiz.boursewatcher.utils.UserError
import com.alexisboiz.boursewatcher.views.MainFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        val currentUser = DistantDataSource.auth.currentUser
        if (currentUser != null) {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.main_fragment, MainFragment())
                commit()
            }
        }
        val email = view.findViewById<TextInputLayout>(R.id.til_email)
        val email_input = view.findViewById<EditText>(R.id.et_email)
        val password_input = view.findViewById<EditText>(R.id.et_password)
        val login_button = view.findViewById<MaterialButton>(R.id.submit_button)
        val haveNotAccount = view.findViewById<TextView>(R.id.tv_not_registered)

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
                                Toast.makeText(requireContext(), "Utilisateur non trouvé", Toast.LENGTH_SHORT).show()
                            }
                            UserError.WRONG_PASSWORD -> {
                                Toast.makeText(requireContext(), "Mot de passe incorrect", Toast.LENGTH_SHORT).show()
                            }
                            UserError.UNKNOWN_ERROR -> {
                                Toast.makeText(requireContext(), "Erreur inconnue", Toast.LENGTH_SHORT).show()
                            }
                            else -> {
                                Toast.makeText(requireContext(), "Erreur inconnue", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }else{
                    parentFragmentManager.beginTransaction().apply {
                        replace(R.id.main_fragment, MainFragment())
                        commitNow()
                    }
                }
            }
        }

        haveNotAccount.setOnClickListener(){
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.main_fragment, RegisterFragment())
                commit()
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    companion object {

        fun newInstance() =
            LoginFragment()
    }

    fun updateUI(){

    }
}