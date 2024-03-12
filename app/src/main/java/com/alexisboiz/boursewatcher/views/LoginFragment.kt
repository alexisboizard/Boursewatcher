package com.alexisboiz.boursewatcher.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.alexisboiz.boursewatcher.MainActivity
import com.alexisboiz.boursewatcher.R
import com.alexisboiz.boursewatcher.domain.UserRepository
import com.alexisboiz.boursewatcher.model.UsersModel.User
import com.google.android.material.button.MaterialButton

class LoginFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        val email = view.findViewById<EditText>(R.id.et_email)
        val password = view.findViewById<EditText>(R.id.et_password)
        val login_button = view.findViewById<MaterialButton>(R.id.submit_button)
        val haveNotAccount = view.findViewById<TextView>(R.id.tv_not_registered)

        val userRepository = context?.let { UserRepository(it) }

        login_button.setOnClickListener(){
            val email_text = email.text.toString()
            val password_text = password.text.toString()

            val user : User? = userRepository?.getUser(email_text, password_text)

            if (user != null){
                MainActivity.connected = true
                parentFragmentManager.beginTransaction().apply {
                    replace(R.id.main_fragment, FriendFragment.newInstance())
                    commit()
                }
            }else{
                Toast.makeText(context, "Wrong email or password", Toast.LENGTH_SHORT).show()
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
}