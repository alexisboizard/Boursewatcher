package com.alexisboiz.boursewatcher.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.alexisboiz.boursewatcher.MainActivity
import com.alexisboiz.boursewatcher.R

class NavigationFragment : Fragment(), View.OnClickListener {
    var homeButton : ImageButton? = null
    var marketButton : ImageButton? = null
    var friendsButton : ImageButton? = null
    var newsButton : ImageButton? = null
    var settingsButton : ImageButton? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_navigation, container, false)
        homeButton  = view.findViewById(R.id.home_button)
        marketButton  = view.findViewById(R.id.market_button)
        friendsButton = view.findViewById(R.id.friends_button)
        newsButton = view.findViewById(R.id.news_button)
        settingsButton = view.findViewById(R.id.settings_button)

        homeButton?.setOnClickListener(this)
        marketButton?.setOnClickListener(this)
        friendsButton?.setOnClickListener(this)
        newsButton?.setOnClickListener(this)
        settingsButton?.setOnClickListener(this)

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            NavigationFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onClick(p0: View?) {

        when(p0?.id){
            R.id.home_button -> {
                Log.e("HOME","Clicked")
                homeButton?.setImageResource(R.drawable.selected_home_button)
                marketButton?.findViewById<ImageButton>(R.id.market_button)?.setImageResource(R.drawable.market_button)
                friendsButton?.findViewById<ImageButton>(R.id.friends_button)?.setImageResource(R.drawable.friends_button)
                newsButton?.findViewById<ImageButton>(R.id.news_button)?.setImageResource(R.drawable.news_button)
                settingsButton?.findViewById<ImageButton>(R.id.settings_button)?.setImageResource(R.drawable.settings_button)
                parentFragmentManager.beginTransaction().apply {
                    replace(R.id.main_fragment, MainFragment())
                    commit()
                }
            }
            R.id.market_button -> {
                Log.e("MARKET","Clicked")
                homeButton?.setImageResource(R.drawable.home_button)
                marketButton?.findViewById<ImageButton>(R.id.market_button)?.setImageResource(R.drawable.selected_market_button)
                friendsButton?.findViewById<ImageButton>(R.id.friends_button)?.setImageResource(R.drawable.friends_button)
                newsButton?.findViewById<ImageButton>(R.id.news_button)?.setImageResource(R.drawable.news_button)
                settingsButton?.findViewById<ImageButton>(R.id.settings_button)?.setImageResource(R.drawable.settings_button)
                // Navigate to MarketFragment
                parentFragmentManager.beginTransaction().apply {
                    replace(R.id.main_fragment, MarketFragment())
                    commit()
                }
            }
            R.id.friends_button -> {
                Log.e("FRIEND","Clicked")
                homeButton?.setImageResource(R.drawable.home_button)
                marketButton?.findViewById<ImageButton>(R.id.market_button)?.setImageResource(R.drawable.market_button)
                friendsButton?.findViewById<ImageButton>(R.id.friends_button)?.setImageResource(R.drawable.selected_friends_button)
                newsButton?.findViewById<ImageButton>(R.id.news_button)?.setImageResource(R.drawable.news_button)
                settingsButton?.findViewById<ImageButton>(R.id.settings_button)?.setImageResource(R.drawable.settings_button)

                if (!MainActivity.connected) {
                    // Navigate to LoginFragment
                    parentFragmentManager.beginTransaction().apply {
                        replace(R.id.main_fragment, LoginFragment())
                        commit()
                    }
                }else{
                    // Navigate to FriendFragment
                    parentFragmentManager.beginTransaction().apply {
                        replace(R.id.main_fragment, FriendFragment())
                        commit()
                    }
                }

            }
            R.id.news_button -> {
                homeButton?.setImageResource(R.drawable.home_button)
                marketButton?.findViewById<ImageButton>(R.id.market_button)?.setImageResource(R.drawable.market_button)
                friendsButton?.findViewById<ImageButton>(R.id.friends_button)?.setImageResource(R.drawable.friends_button)
                newsButton?.findViewById<ImageButton>(R.id.news_button)?.setImageResource(R.drawable.selected_news_button)
                settingsButton?.findViewById<ImageButton>(R.id.settings_button)?.setImageResource(R.drawable.settings_button)
                parentFragmentManager.beginTransaction().apply {
                    replace(R.id.main_fragment, NewsFragment())
                    commit()
                }

            }
            R.id.settings_button -> {
                homeButton?.setImageResource(R.drawable.home_button)
                marketButton?.findViewById<ImageButton>(R.id.market_button)?.setImageResource(R.drawable.market_button)
                friendsButton?.findViewById<ImageButton>(R.id.friends_button)?.setImageResource(R.drawable.friends_button)
                newsButton?.findViewById<ImageButton>(R.id.news_button)?.setImageResource(R.drawable.news_button)
                settingsButton?.findViewById<ImageButton>(R.id.settings_button)?.setImageResource(R.drawable.selected_settings_button)

            }
        }
    }

    fun resetUnselectedButton(view: View) : Unit{
        // Reset the image of all buttons except the one selected
        view.findViewById<ImageButton>(R.id.home_button)
            ?.setImageResource(R.drawable.home_button)
        view.findViewById<ImageButton>(R.id.market_button)
            ?.setImageResource(R.drawable.market_button)
        view.findViewById<ImageButton>(R.id.friends_button)
            ?.setImageResource(R.drawable.friends_button)
        view.findViewById<ImageButton>(R.id.news_button)
            ?.setImageResource(R.drawable.news_button)
        view.findViewById<ImageButton>(R.id.settings_button)
            ?.setImageResource(R.drawable.settings_button)
        }

    }