package com.example.favouriteplayertracker.ui.tabs.destinationFragments.playerList

import android.os.Bundle
import android.util.Log
import android.util.Log.i
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.favouriteplayertracker.R
import com.example.favouriteplayertracker.databinding.FragmentPlayerListBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PlayerListFragment : Fragment(), PlayerListAdapter.OnItemClickListener {

    val TAG = "PlayerListFragment"
    private var _binding:FragmentPlayerListBinding? = null
    private val binding get() = _binding!!
    private val adapter = PlayerListAdapter(this)

    private val viewModel: PlayerListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.i(TAG, "PlayerListFragment reached.")

        _binding = FragmentPlayerListBinding.inflate(inflater, container, false)


        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fab = binding.fab


        val navHostFragment = activity?.supportFragmentManager?.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        fab.setOnClickListener {

            navController.navigate(R.id.addPlayerFragment)

        }

        binding.playerListRV.adapter = adapter
        binding.playerListRV.layoutManager = LinearLayoutManager(context)

        viewModel.userList.observe(viewLifecycleOwner) { newList ->
            adapter.updateList(newList)
            adapter.notifyDataSetChanged()
        }


    }

    // Interface implementation to set up the ability to choose a player from the list.
    override fun onItemClick(selectedPlayer: String) {
        Log.i(TAG, "$selectedPlayer clicked")

        viewModel.selectPlayer(selectedPlayer)
        findNavController().navigate(R.id.playerChosenFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}