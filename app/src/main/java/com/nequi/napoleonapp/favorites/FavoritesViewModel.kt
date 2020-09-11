package com.nequi.napoleonapp.favorites

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.nequi.napoleonapp.repository.NapoleonDatabase
import com.nequi.napoleonapp.repository.NapoleonRepository
import com.nequi.napoleonapp.repository.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: NapoleonRepository
    var allFavorites: LiveData<List<Post>>
    private val applicationContext: Application

    init {
        val postDao = NapoleonDatabase.getDatabase(application).postDao()
        repository = NapoleonRepository(postDao)
        applicationContext = application
        allFavorites = repository.getFavorite
    }

    fun removeFavorite(favorite: Post) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeFavorite(favorite)
        }
    }

    fun delete(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(id)
        }
    }
}