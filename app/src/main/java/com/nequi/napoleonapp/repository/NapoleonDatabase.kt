package com.nequi.napoleonapp.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Post::class], version = 5, exportSchema = false)
abstract class NapoleonDatabase: RoomDatabase() {
    abstract fun postDao(): PostDao

    companion object {
        @Volatile
        private var INSTANCE: NapoleonDatabase? = null

        fun getDatabase(context: Context): NapoleonDatabase {
            val tempInstance =
                INSTANCE
            if(tempInstance != null) return tempInstance
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NapoleonDatabase::class.java,
                    "napoleon_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}