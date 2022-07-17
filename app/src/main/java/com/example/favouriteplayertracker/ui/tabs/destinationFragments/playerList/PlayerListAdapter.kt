package com.example.favouriteplayertracker.ui.tabs.destinationFragments.playerList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.example.favouriteplayertracker.R
import com.example.favouriteplayertracker.data.local.UserList.FavouritePlayer


class PlayerListAdapter(private val listener: OnItemClickListener) : RecyclerView.Adapter<PlayerListAdapter.MyViewHolder>() {

    private var items: List<FavouritePlayer> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.playerNameTV.text = items[position].name
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener
    {
        val playerNameTV: TextView = itemView.findViewById(R.id.playerNameTV)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition

            val selected = playerNameTV.text.toString()

            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(selected)
            }

        }

    }

    interface OnItemClickListener {
        fun onItemClick(selectedPlayer: String)
    }


    fun updateList(user_player_list: List<FavouritePlayer>) {
        items = user_player_list
    }

}