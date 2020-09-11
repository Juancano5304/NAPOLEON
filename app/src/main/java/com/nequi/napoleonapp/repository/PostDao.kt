package com.nequi.napoleonapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nequi.napoleonapp.repository.Post

@Dao
interface PostDao {
    @Query("SELECT * FROM post ORDER BY id ASC")
    fun getAll(): LiveData<List<Post>>

    @Query("SELECT * FROM post WHERE favorite = 1 ORDER BY id ASC")
    fun getFavorites(): LiveData<List<Post>>

    @Query("DELETE FROM post")
    fun deleteAll()

    @Query("DELETE FROM post WHERE id = :id")
    fun delete(id: Int)

    @Query("UPDATE post SET favorite = :favorite WHERE id = :id")
    fun changeFavoriteStatus(id: Int, favorite: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(postList: List<Post>)

    @Query("SELECT * FROM post WHERE id = :id")
    fun getDetails(id: Int): LiveData<Post>

    @Query("UPDATE post SET favorite = :favorite WHERE id = :id")
    fun updatePost(favorite: Int, id: Int)

    @Query("UPDATE post SET hadBeenReaden = :readen WHERE id = :id")
    fun hadBeenReaden(readen: Int, id: Int)

}