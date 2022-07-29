package com.example.favouriteplayertracker.ui.playerChosen.bottomNavDestinations.tweets

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.favouriteplayertracker.R
import com.example.favouriteplayertracker.data.repository.tweets.SingleTweet

class TweetsAdapter: RecyclerView.Adapter<TweetsAdapter.TweetViewHolder>() {

    private var data: List<SingleTweet> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tweet, parent, false)
        return TweetViewHolder(view)
    }

    override fun onBindViewHolder(holder: TweetViewHolder, position: Int) {
        val row = data[position]

        holder.tweetText.text = row.text

    }

    override fun getItemCount(): Int {
        return data.size
    }

    class TweetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val profileImage = itemView.findViewById<ImageView>(R.id.twitter_profile)
        val tweetText: TextView = itemView.findViewById(R.id.tweetText)

    }

    fun updateData(newData: List<SingleTweet>) {
        data = newData
    }

}