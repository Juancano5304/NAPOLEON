package com.nequi.napoleonapp.list

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.nequi.napoleonapp.repository.Post
import com.nequi.napoleonapp.R
import kotlinx.android.synthetic.main.cardview_recycler.view.*

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var postList = emptyList<Post>()
    private lateinit var listViewModel: ListViewModel
    private lateinit var context: Context

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context
        listViewModel = ViewModelProvider(context as FragmentActivity).get(ListViewModel::class.java)
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cardview_recycler, parent, false))
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = postList[position]
        holder.itemView.textview_id.text = item.id.toString()
        holder.itemView.textview_title.text = item.title
        //holder.itemView.animation = AnimationUtils.loadAnimation(context, R.anim.fade_transition)
        when(item.favorite) {
            true -> holder.itemView.imageview_rotate_star.setImageResource(R.drawable.ic_baseline_stars_24_yellow)
            false -> holder.itemView.imageview_rotate_star.setImageResource(R.drawable.ic_baseline_stars_24)
        }
        when(item.readen) {
            true -> holder.itemView.cardview_list.strokeWidth = 10
            false -> holder.itemView.cardview_list.strokeWidth = 0
        }

        holder.itemView.setOnClickListener {
            v ->
            val bundle = Bundle()
            postList[position].readen = false
            updateReaden(postList[position])
            setData(postList)
            bundle.putString("post", GsonBuilder().create().toJson(item))
            Navigation.findNavController(v).navigate(R.id.action_listFragment_to_detailsFragment, bundle)
        }

        holder.itemView.imageview_rotate_star.setOnClickListener {
            postList[position].favorite = !item.favorite
            updatePost(postList[position])
            setData(postList)
        }
    }

    private fun updatePost(post: Post) {
        listViewModel.updatePost(post)
    }

    private fun updateReaden(post: Post) {
        listViewModel.updateReaden(post)
    }

    fun setData(post: List<Post>) {
        this.postList = post
        Log.i("prueba", post.toString())
        notifyDataSetChanged()
    }

    fun getPosition(adapterPosition: Int): Int {
        return postList[adapterPosition].id
    }
}