package com.nequi.napoleonapp

import android.util.Log
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.nequi.napoleonapp.repository.NapoleonDatabase
import com.nequi.napoleonapp.repository.Post
import com.nequi.napoleonapp.repository.PostDao
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.lang.Exception

@RunWith(AndroidJUnit4::class)
class NapoleonDatabaseTest {
    private lateinit var postDao: PostDao
    private lateinit var napoleonDatabase: NapoleonDatabase

    @Before
    fun createDatabase(){
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        napoleonDatabase = Room.inMemoryDatabaseBuilder(context, NapoleonDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        postDao = napoleonDatabase.postDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDatabase() {
        napoleonDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetPost() {
        val post = Post(1, 0, "titulo", "body", true)
        postDao.insert(mutableListOf(post, post, post))
    }
}