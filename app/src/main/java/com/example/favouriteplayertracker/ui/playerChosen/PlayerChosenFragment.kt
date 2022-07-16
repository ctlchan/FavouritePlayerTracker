package com.example.favouriteplayertracker.ui.playerChosen

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
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
        // Inflate the layout for this fragment
        _binding = FragmentPlayerChosenBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getSelected().observe(viewLifecycleOwner) {
            Log.i(TAG, "selected: $it")
            binding.nameTV.text = it
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i(TAG, "$TAG: onDestroyView()")

        // update database to unselect player
        viewModel.unselect()
    }
}