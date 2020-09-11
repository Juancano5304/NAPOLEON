package com.nequi.napoleonapp.list

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nequi.napoleonapp.*
import com.nequi.napoleonapp.databinding.ListFragmentBinding
import kotlinx.android.synthetic.main.list_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ListFragment : Fragment() {

    private lateinit var activityContext: Context
    private lateinit var viewModel: ListViewModel
    private lateinit var listRecyclerview: RecyclerView
    private lateinit var adapter: ListAdapter
    private lateinit var binding: ListFragmentBinding
    private lateinit var itemTouchHelper: ItemTouchHelper
    private lateinit var itemTouchCallback: ItemTouchHelper.SimpleCallback
    private lateinit var mediaPlayerDelete: MediaPlayer
    private lateinit var mediaPlayerReload: MediaPlayer
    private lateinit var mediaPlayerBell: MediaPlayer

    companion object {
        fun newInstance() = ListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModel.allPosts.observe(viewLifecycleOwner, Observer { post ->
            adapter.setData(post)
        })

        binding = DataBindingUtil.inflate(inflater, R.layout.list_fragment, container, false)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        return binding.root;
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = ListAdapter()
        listRecyclerview = list_recyclerview
        listRecyclerview.adapter = adapter
        listRecyclerview.layoutManager = LinearLayoutManager(requireContext())

        mediaPlayerDelete = MediaPlayer.create(activityContext, R.raw.trash)
        mediaPlayerReload = MediaPlayer.create(activityContext, R.raw.reload)
        mediaPlayerBell = MediaPlayer.create(activityContext, R.raw.bell)

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
                    Toast.makeText(activityContext, "Post borrado", Toast.LENGTH_SHORT).show()
                }
            }
        }

        itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(listRecyclerview)

        material_design_floating_action_menu_item1.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                mediaPlayerDelete.start()
                viewModel.deleteAll()
            }
        }

        material_design_floating_action_menu_item2.setOnClickListener {
            mediaPlayerBell.start()
            findNavController().navigate(R.id.action_listFragment_to_favoritesFragment)
        }

        material_design_floating_action_menu_item3.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                mediaPlayerReload.start()
                //progress_circular.visibility = View.VISIBLE
                viewModel.reload()
            }
        }

        /*viewModel.progressBar.observe(viewLifecycleOwner, Observer {
            Log.i("progress", it.toString())
            if(it) {
                progress_circular.visibility = View.INVISIBLE
            } else {
                progress_circular.visibility = View.VISIBLE
            }
        })*/

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activityContext = context
    }
}
