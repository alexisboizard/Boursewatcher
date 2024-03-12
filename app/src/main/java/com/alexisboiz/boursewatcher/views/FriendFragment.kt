package com.alexisboiz.boursewatcher.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alexisboiz.boursewatcher.R
import com.google.android.material.tabs.TabLayout

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

        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val fragment = when (tab?.position) {
                    0 -> FriendQrFragment.newInstance()
                    1 -> FriendAddFragment.newInstance()
                    else -> FriendQrFragment.newInstance()
                }
                childFragmentManager.beginTransaction()
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
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            FriendFragment()
    }
}