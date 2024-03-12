package com.alexisboiz.boursewatcher.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.alexisboiz.boursewatcher.MainActivity
import com.alexisboiz.boursewatcher.R
import com.alexisboiz.boursewatcher.domain.UserRepository
import com.alexisboiz.boursewatcher.model.UsersModel.User
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val email_input = view.findViewById<TextInputEditText>(R.id.et_email)
        val password_input = view.findViewById<TextInputEditText>(R.id.et_password)
        val username_input = view.findViewById<TextInputEditText>(R.id.et_username)
        val register_button = view.findViewById<MaterialButton>(R.id.submit_button)
        val haveAccount = view.findViewById<TextView>(R.id.tv_not_registered)

        val userRepository = context?.let { UserRepository(it) }

        register_button.setOnClickListener {
            val email = email_input.text.toString()
            val password = password_input.text.toString()
            val username = username_input.text.toString()
            val user = User( username, email, password)

            MainScope().launch {
                userRepository?.insertUser(user)
            }
            val userConnected = userRepository?.getUser(email, password)
            if (userConnected != null){
                MainActivity.connected = true
                parentFragmentManager.beginTransaction().apply {
                    replace(R.id.main_fragment, FriendFragment.newInstance())
                    commit()
                }
            }

        }

        haveAccount.setOnClickListener(){
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.main_fragment, LoginFragment())
                commit()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            RegisterFragment()
    }
}