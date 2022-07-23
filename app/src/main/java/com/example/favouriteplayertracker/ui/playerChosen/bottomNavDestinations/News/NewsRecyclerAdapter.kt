package com.example.favouriteplayertracker.ui.playerChosen.bottomNavDestinations.News

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.favouriteplayertracker.R
import com.example.favouriteplayertracker.data.remote.newsApi.Article
import com.example.favouriteplayertracker.ui.tabs.destinationFragments.playerList.PlayerListAdapter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class NewsRecyclerAdapter(private val listener: NewsRecyclerAdapter.OnItemClickListener):
    RecyclerView.Adapter<NewsRecyclerAdapter.NewsViewHolder>() {

    private var data: List<Article> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = data[position]

        holder.headline.text = article.title
        holder.date.text = convertDate(article.published_date)
        // TODO: get image from "media" url

    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val newsImage: ImageView = itemView.findViewById(R.id.news_image)
        val headline: TextView = itemView.findViewById(R.id.headline)
        val date: TextView = itemView.findViewById(R.id.publish_date)

        init { itemView.setOnClickListener(this) }

        override fun onClick(p0: View?) {
            val position = adapterPosition

            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(data[position])
            }

        }

    }

    interface OnItemClickListener {

        fun onItemClick(article: Article) {
        }

    }

    private fun convertDate(formattedDate: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val date = LocalDate.parse(formattedDate, formatter)

        return "${date.month} ${date.dayOfMonth}, ${date.year}"
    }

    fun updateData(newData: List<Article>) {
        data = newData
    }


}