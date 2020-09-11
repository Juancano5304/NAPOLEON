package com.nequi.napoleonapp.repository

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NapoleonRepository(private val postDao: PostDao) {
    val getAll: LiveData<List<Post>> = postDao.getAll()
    val getFavorite: LiveData<List<Post>> = postDao.getFavorites()
    private var progressBar = MutableLiveData<Boolean>(true)

    fun readPost(post: Post) {
        return when(post.readen) {
            true -> postDao.hadBeenReaden(1, post.id)
            false -> postDao.hadBeenReaden(0, post.id)
        }
    }

    fun updatePost(post: Post)
    {
        return when(post.favorite) {
            true -> postDao.updatePost(1, post.id)
            false -> postDao.updatePost(0, post.id)
        }
    }

    fun removeFavorite(post: Post){
        postDao.changeFavoriteStatus(post.id, 0)
    }

    fun insert(postList: List<Post>) {
        postDao.insert(postList)
    }

    fun deleteAll() {
        postDao.deleteAll()
    }

    fun delete(id: Int) {
        postDao.delete(id)
    }

    fun getDetails(id: Int): LiveData<Post> {
        return postDao.getDetails(id)
    }

    fun callServiceGetPosts(context: Context): MutableLiveData<Boolean> {
        val postsService: PostsService = RestEngine.getRestEngine()
            .create(PostsService::class.java)
        val result: Call<List<Post>> = postsService.getAll()
        result.enqueue(object : Callback<List<Post>> {
            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Toast.makeText(context, "No se puede acceder al servicio", Toast.LENGTH_SHORT).show()
                progressBar.value = true
            }

            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                when(response.code()) {
                    200 -> {
                        Toast.makeText(context, "Datos cargados", Toast.LENGTH_SHORT).show()
                        AsyncTask.execute {
                            deleteAll()
                            for(item in response.body()!!) {
                                item.favorite = false
                                item.readen = false
                                if(item.id <= 20) {
                                    item.readen = true
                                }
                            }
                            insert(response.body()!!)
                        }
                    }
                }
                progressBar.value = true
            }
        })
        return progressBar
    }
}