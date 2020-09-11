package com.nequi.napoleonapp.repository

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "post")
data class Post(
    @PrimaryKey @NonNull var id: Int,
    @NonNull @ColumnInfo(name = "userId") val userId: Int,
    @NonNull @ColumnInfo(name = "title") val title: String,
    @NonNull @ColumnInfo(name = "body") val body: String,
    @ColumnInfo(name = "favorite") var favorite: Boolean,
    @ColumnInfo(name = "hadBeenReaden") var readen: Boolean
)