package com.example.favouriteplayertracker.ui.playerChosen.bottomNavDestinations.tweets

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.favouriteplayertracker.R
import com.example.favouriteplayertracker.databinding.FragmentPlayerTweetsBinding
import com.example.favouriteplayertracker.ui.playerChosen.PlayerChosenViewModel
import com.example.favouriteplayertracker.utility.MarginItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.internal.notify

@AndroidEntryPoint
class PlayerTweetsFragment : Fragment() {

    private val TAG = "PlayerTweetsFragment"
    private var _binding: FragmentPlayerTweetsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PlayerChosenViewModel by viewModels()

    private lateinit var adapter: TweetsAdapter


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

        adapter = TweetsAdapter()
        val layoutManager = LinearLayoutManager(context)

        binding.tweetList.adapter = adapter
        binding.tweetList.layoutManager = layoutManager
        binding.tweetList.addItemDecoration(
            MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.margin))
        )

        observeTweets()







    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeTweets() {
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.getTweets().observe(viewLifecycleOwner) {
                Log.i(TAG, "observing getTweets(), it = $it")
                if (it != null) {
                    adapter.updateData(it.tweets)
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }



}