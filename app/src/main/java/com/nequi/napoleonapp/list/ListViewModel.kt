package com.nequi.napoleonapp.list

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nequi.napoleonapp.repository.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: NapoleonRepository
    val allPosts: LiveData<List<Post>>
    private val applicationContext: Application
    var progressBar: MutableLiveData<Boolean> = MutableLiveData(false)

    init {
        val postDao = NapoleonDatabase.getDatabase(application).postDao()
        repository = NapoleonRepository(postDao)
        applicationContext = application
        allPosts = repository.getAll
    }

    fun updatePost(post: Post) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updatePost(post)
            Log.i("prueba", "registro actualizado")
        }
    }

    fun updateReaden(post: Post) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.readPost(post)
            Log.i("prueba", "registro readen actualizado")
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
            Log.i("prueba", "registros borrados")
        }
    }

    fun delete(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(id)
        }
    }

    fun reload() {
        viewModelScope.launch(Dispatchers.IO) {
            progressBar = repository.callServiceGetPosts(applicationContext)
        }
    }
}
