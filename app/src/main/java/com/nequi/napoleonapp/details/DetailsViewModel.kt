package com.nequi.napoleonapp.details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.nequi.napoleonapp.repository.NapoleonDatabase
import com.nequi.napoleonapp.repository.NapoleonRepository
import com.nequi.napoleonapp.repository.Post

class DetailsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: NapoleonRepository
    val details: LiveData<Post>
        get() = repository.getDetails(1)

    init {
        val postDao = NapoleonDatabase.getDatabase(application).postDao()
        repository = NapoleonRepository(postDao)
    }
}
