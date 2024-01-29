package com.example.myapplication.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.myapplication.entity.Watch

@Dao
interface WatchDao {

    @Query("SELECT * FROM watch ORDER BY id DESC")
    fun getAllWatches(): List<Watch>

}