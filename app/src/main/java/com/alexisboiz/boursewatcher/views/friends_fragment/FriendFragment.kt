package com.alexisboiz.boursewatcher.views.friends_fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.alexisboiz.boursewatcher.R
import com.alexisboiz.boursewatcher.views.auth.LoginFragment
import com.google.android.material.tabs.TabLayout
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class FriendFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friend, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tabs = view.findViewById<TabLayout>(R.id.tl_friends)

        val user = Firebase.auth.currentUser

        if (user == null) {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_fragment, LoginFragment.newInstance())
                .commit()
        }else{
            parentFragmentManager.beginTransaction()
                .replace(R.id.fcv_friends, FriendQrFragment())
                .commit()
        }


        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val fragment = when (tab?.position) {
                    0 -> FriendQrFragment()
                    1 -> FriendAddFragment()
                    else -> FriendQrFragment()
                }
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fcv_friends, fragment)
                    .commit()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Nothing to do
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Nothing to do
            }
        })

        val notification = view.findViewById<ImageButton>(R.id.btn_notifications)
        notification.setOnClickListener {
            val intent = Intent(this.context, FriendRequestActivity::class.java)
            startActivity(intent)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            FriendFragment()
    }
}