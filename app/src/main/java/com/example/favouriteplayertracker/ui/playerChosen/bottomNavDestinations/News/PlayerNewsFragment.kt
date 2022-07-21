package com.example.favouriteplayertracker.ui.playerChosen.bottomNavDestinations.News

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.favouriteplayertracker.databinding.FragmentPlayerNewsBinding
import com.example.favouriteplayertracker.ui.playerChosen.PlayerChosenViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class PlayerNewsFragment : Fragment() {

    private val TAG = "PlayerNewsFragment"
    private var _binding: FragmentPlayerNewsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PlayerChosenViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPlayerNewsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = NewsRecyclerAdapter()
        val layoutManager = LinearLayoutManager(context)

        binding.newsRecycler.adapter = adapter
        binding.newsRecycler.layoutManager = layoutManager

        observeNews(adapter)
    }

    fun observeNews(adapter: NewsRecyclerAdapter) {
        // TODO: fix recyclerview layout: need more space for each item (vertically)
        lifecycleScope.launch {
            viewModel.getNews().observe(viewLifecycleOwner) {
                adapter.updateData(it.articles)
                adapter.notifyDataSetChanged()

            }
        }
    }


}