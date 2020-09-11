package com.nequi.napoleonapp

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.nequi.napoleonapp.repository.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(application: Application): AndroidViewModel(application) {
    private val repository: NapoleonRepository

    init {
        val postDao = NapoleonDatabase.getDatabase(application).postDao()
        repository = NapoleonRepository(postDao)
    }

    fun callServiceGetPosts(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.callServiceGetPosts(context)
        }
    }
}