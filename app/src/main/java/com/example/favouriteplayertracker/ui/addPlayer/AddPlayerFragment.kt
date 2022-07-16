package com.example.favouriteplayertracker.ui.addPlayer

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.example.favouriteplayertracker.R
import com.example.favouriteplayertracker.data.local.FavouritePlayer
import com.example.favouriteplayertracker.data.local.LocalDatabase
import com.example.favouriteplayertracker.databinding.FragmentAddPlayerBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class AddPlayerFragment : Fragment() {

    private val TAG = "AddPlayerFragment"

    private var _binding: FragmentAddPlayerBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AddPlayerViewModel by viewModels()


    @Named("database")
    @Inject
    lateinit var database: LocalDatabase


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAddPlayerBinding.inflate(inflater, container, false)

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHostFragment = activity?.supportFragmentManager?.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding.cancelAddPlayer.setOnClickListener {
            navController.navigate(R.id.tabHostFragment)
        }

        binding.confirmAddPlayer.setOnClickListener {

            val newName = binding.addPlayerET.text.toString()

            if (newName != "") {

                lifecycleScope.launch {
                    viewModel.addPlayer(newName)
                }

                Log.i(TAG, "Add button pressed.")
            }
            navController.navigate(R.id.tabHostFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}