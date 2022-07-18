package com.example.favouriteplayertracker.ui.playerChosen.bottomNavDestinations

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.favouriteplayertracker.databinding.FragmentPlayerInfoBinding
import com.example.favouriteplayertracker.ui.playerChosen.PlayerChosenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlayerInfoFragment : Fragment() {

    private val TAG = "PlayerInfoFragment"
    private var _binding: FragmentPlayerInfoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PlayerChosenViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPlayerInfoBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getSelected().observe(viewLifecycleOwner) {

            Log.i(TAG, "selected: $it")

            if (it != null) {

                if (it.team_id != 0) {
                    observeTeamInfo(it.team_id)
                }


                val heightF = when(it.height_feet) {
                    null -> "?"
                    else -> it.height_feet
                }
                val heightI = when(it.height_inches) {
                    null -> "?"
                    else -> it.height_inches
                }
                val weightP = when(it.weight_pounds) {
                    null -> "?"
                    else -> it.weight_pounds
                }


                binding.apply {
                    nameTV.text = it.name
                    playerPhysicals.text = playerPhysicals.text.toString().plus(
                        """ $heightF'$heightI", ${weightP}lb"""
                    )
                    playerPosition.text = playerPosition.text.toString().plus(
                        " ${it.position}"
                    )
                }
            }
        }

    }

    private fun observeTeamInfo(id: Int) {
        viewModel.getTeamInfo(id).observe(viewLifecycleOwner) {
            binding.playerTeam.text = binding.playerTeam.text.toString().plus(
                " ${it.abbreviation}"
            )
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}