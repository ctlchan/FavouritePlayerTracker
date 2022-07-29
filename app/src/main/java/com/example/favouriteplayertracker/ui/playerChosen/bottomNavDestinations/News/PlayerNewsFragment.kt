package com.example.favouriteplayertracker.ui.playerChosen.bottomNavDestinations.News

import android.content.Intent
import android.net.Uri
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
import com.example.favouriteplayertracker.data.remote.newsApi.Article
import com.example.favouriteplayertracker.databinding.FragmentPlayerNewsBinding
import com.example.favouriteplayertracker.ui.playerChosen.PlayerChosenViewModel
import com.example.favouriteplayertracker.utility.MarginItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class PlayerNewsFragment : Fragment(),
    NewsRecyclerAdapter.OnItemClickListener{

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

        val adapter = NewsRecyclerAdapter(this)
        val layoutManager = LinearLayoutManager(context)

        binding.newsRecycler.adapter = adapter
        binding.newsRecycler.layoutManager = layoutManager
        binding.newsRecycler.addItemDecoration(
            MarginItemDecoration(
                resources.getDimensionPixelSize(R.dimen.margin)
            )
        )

        observeNews(adapter)
    }

    private fun observeNews(adapter: NewsRecyclerAdapter) {
        lifecycleScope.launch {
            viewModel.getNews().observe(viewLifecycleOwner) {
                    adapter.updateData(it.articles)
                    adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onItemClick(article: Article) {
        Log.i(TAG, "Article clicked! Headline: ${article.title}")
        Log.i(TAG, "Article link: ${article.link}")

        // Convert link to Uri since that is needed for Intent
        val uri = Uri.parse(article.link)
        val intent = Intent(Intent.ACTION_VIEW, uri)

        startActivity(intent)

    }


}