package com.nequi.napoleonapp.repository

import com.nequi.napoleonapp.repository.Post
import retrofit2.Call
import retrofit2.http.GET

interface PostsService {
    @GET("posts")
    fun getAll(): Call<List<Post>>
}