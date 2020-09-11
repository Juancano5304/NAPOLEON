package com.nequi.napoleonapp.favorites

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nequi.napoleonapp.R
import com.nequi.napoleonapp.databinding.FavoritesFragmentBinding
import com.nequi.napoleonapp.databinding.ListFragmentBinding
import com.nequi.napoleonapp.list.ListAdapter
import com.nequi.napoleonapp.list.ListViewModel
import kotlinx.android.synthetic.main.favorites_fragment.*
import kotlinx.android.synthetic.main.list_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FavoritesFragment : Fragment() {

    private lateinit var activityContext: Context
    private lateinit var viewModel: FavoritesViewModel
    private lateinit var favoriteRecyclerview: RecyclerView
    private lateinit var adapter: FavoriteAdapter
    private lateinit var itemTouchHelper: ItemTouchHelper
    private lateinit var itemTouchCallback: ItemTouchHelper.SimpleCallback
    private lateinit var binding: FavoritesFragmentBinding

    companion object {
        fun newInstance() = FavoritesFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.favorites_fragment, container, false)
        binding.lifecycleOwner = this
        adapter = FavoriteAdapter()

        val observer = object :RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                super.onChanged()
                val textVisibility = if(adapter.itemCount == 0) View.VISIBLE else View.INVISIBLE
                val recyclerVisibility = if(adapter.itemCount == 0) View.INVISIBLE else View.VISIBLE
                GlobalScope.launch(Dispatchers.Main) {
                    binding.textviewEmpty.visibility = textVisibility
                    binding.favoritesRecyclerview.visibility = recyclerVisibility
                }
            }
        }
        adapter.registerAdapterDataObserver(observer)
        observer.onChanged()

        viewModel = ViewModelProvider(this).get(FavoritesViewModel::class.java)
        viewModel.allFavorites.observe(viewLifecycleOwner, Observer { favorite ->
            adapter.setData(favorite.toMutableList())
        })



        return binding.root;
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FavoritesViewModel::class.java)
        favoriteRecyclerview = favorites_recyclerview
        favoriteRecyclerview.adapter = adapter
        favoriteRecyclerview.layoutManager = LinearLayoutManager(requireContext())

        itemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if(direction == ItemTouchHelper.LEFT) {
                    viewModel.delete(adapter.getPosition(viewHolder.adapterPosition))
                    Toast.makeText(activityContext, "Favorito borrado", Toast.LENGTH_SHORT).show()
                }
            }
        }

        itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(favoriteRecyclerview)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activityContext = context
    }

}