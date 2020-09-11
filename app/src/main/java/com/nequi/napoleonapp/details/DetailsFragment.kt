package com.nequi.napoleonapp.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.gson.GsonBuilder
import com.nequi.napoleonapp.repository.Post
import com.nequi.napoleonapp.R
import com.nequi.napoleonapp.databinding.DetailsFragmentBinding


class DetailsFragment : Fragment() {
    private lateinit var post: Post
    private lateinit var binding: DetailsFragmentBinding

    companion object {
        fun newInstance() = DetailsFragment()
    }

    private lateinit var viewModel: DetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.details_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)
        binding.textviewUser.text = "Usuario Nº "+post.userId
        binding.textviewPostNumber.text = "Post Nº "+post.id
        binding.textviewTitle.text = post.title
        binding.textviewBody.text = post.body
        when(post.favorite) {
            true -> binding.imageviewDetailsFavorite.setImageResource(R.drawable.ic_baseline_stars_24_yellow)
            false -> binding.imageviewDetailsFavorite.setImageResource(R.drawable.ic_baseline_stars_24)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            post = GsonBuilder().create().fromJson<Post>(requireArguments().getString("post"), Post::class.java)
        }
    }
}
