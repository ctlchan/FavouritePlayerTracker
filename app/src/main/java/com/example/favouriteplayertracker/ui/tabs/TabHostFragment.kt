package com.example.favouriteplayertracker.ui.tabs

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.favouriteplayertracker.databinding.FragmentTabHostBinding
import com.example.favouriteplayertracker.ui.tabs.destinationFragments.DiscoverFragment
import com.example.favouriteplayertracker.ui.tabs.destinationFragments.playerList.PlayerListFragment
import com.google.android.material.tabs.TabLayoutMediator

private val TAG = "TabHostFragment"

class TabHostFragment : Fragment() {

    // _binding only valid between onCreateView and onDestroyView
    private var _binding: FragmentTabHostBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.i(TAG, "onCreateView()")

        _binding = FragmentTabHostBinding.inflate(inflater, container, false)

        // Return the inflated layout
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.i(TAG, "onViewCreated()")

//        binding = FragmentTabHostBinding.inflate(layoutInflater)

        val tabLayout = binding.tabLayout
        val viewPager = binding.viewPager


        // Create instance of Adapter, make function calls to add tab data.
        val adapter = ViewPagerAdapter(childFragmentManager, lifecycle)
        adapter.addFragment(PlayerListFragment(), "Players")
        adapter.addFragment(DiscoverFragment(), "Discover")

        // Add adapter to viewPager
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) {tab, position ->
            tab.text = adapter.getPageTitle(position)
        }.attach() // .attach connects tabLayout with viewPager

    }

    override fun onDestroyView() {
        super.onDestroyView()

        // Fragments outlive their views. Thus we are cleaning up any references to the binding
        // class instance in this method/part of the lifecycle.
        _binding = null
    }

}