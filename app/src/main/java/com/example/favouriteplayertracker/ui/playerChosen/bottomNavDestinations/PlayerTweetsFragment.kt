package com.example.favouriteplayertracker.ui.playerChosen.bottomNavDestinations

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.favouriteplayertracker.R
import com.example.favouriteplayertracker.databinding.FragmentPlayerTweetsBinding
import com.example.favouriteplayertracker.ui.playerChosen.PlayerChosenViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PlayerTweetsFragment : Fragment() {

    private val TAG = "PlayerTweetsFragment"
    private var _binding: FragmentPlayerTweetsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PlayerChosenViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayerTweetsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        Log.i(TAG, viewModel.toString())

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {

            // TODO: Fix bug - it isn't returning anything?
            Log.i(TAG, viewModel.getTweets()!!::class.simpleName.toString())
        }

    }



}