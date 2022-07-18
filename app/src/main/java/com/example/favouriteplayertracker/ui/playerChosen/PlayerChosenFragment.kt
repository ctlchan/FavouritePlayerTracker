package com.example.favouriteplayertracker.ui.playerChosen

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.favouriteplayertracker.R
import com.example.favouriteplayertracker.databinding.FragmentPlayerChosenBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlayerChosenFragment : Fragment() {

    private val TAG = "PlayerChosenFragment"
    private var _binding: FragmentPlayerChosenBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PlayerChosenViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayerChosenBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val navHostFragment = activity?.supportFragmentManager?.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navHostFragment = binding.bottomNavFragmentContainer.getFragment<NavHostFragment>()
        val navController = navHostFragment.navController

        Log.i(TAG, navController.toString())

        binding.bottomNav.setupWithNavController(navController)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i(TAG, "onDestroyView()")
        _binding = null

        viewModel.unselect()
    }


}