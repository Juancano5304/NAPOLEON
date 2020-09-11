package com.nequi.napoleonapp.favorites

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.nequi.napoleonapp.repository.Post
import com.nequi.napoleonapp.R
import com.nequi.napoleonapp.list.ListViewModel
import kotlinx.android.synthetic.main.cardview_favorite.view.*

class FavoriteAdapter: RecyclerView.Adapter<FavoriteAdapter.MyViewHolder>() {

    private var favoritesList: MutableList<Post> = mutableListOf()
    private lateinit var favoritesViewModel: FavoritesViewModel
    private lateinit var context: Context

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context
        favoritesViewModel = ViewModelProvider(context as FragmentActivity).get(FavoritesViewModel::class.java)
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cardview_favorite, parent, false))
    }

    override fun getItemCount(): Int {
        return favoritesList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = favoritesList[position]
        holder.itemView.textview_favorite_id.text = item.id.toString()
        holder.itemView.textview_favorite_title.text = item.title
        holder.itemView.textview_favorite_body.text = item.body
        //holder.itemView.imageview_favorite_star.setBackgroundResource(R.drawable.ic_baseline_stars_24_yellow)

        holder.itemView.imageview_favorite_star.setOnClickListener {
                v ->
            Log.i("prueba", "Remover favorito "+item.id)
            removeFavorite(favoritesList[position])
            setData(favoritesList)
        }
    }

    fun setData(favorite: MutableList<Post>) {
        this.favoritesList = favorite
        notifyDataSetChanged()
    }

    fun getPosition(adapterPosition: Int): Int {
        return favoritesList[adapterPosition].id
    }

    private fun removeFavorite(post: Post) {
        favoritesViewModel.removeFavorite(post)
    }
}