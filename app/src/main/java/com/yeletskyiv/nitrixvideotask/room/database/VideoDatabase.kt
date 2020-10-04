package com.yeletskyiv.nitrixvideotask.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yeletskyiv.nitrixvideotask.room.dao.VideoDao
import com.yeletskyiv.nitrixvideotask.room.entity.Video

@Database(entities = [Video::class], version = 1)
abstract class VideoDatabase : RoomDatabase(){

    abstract fun videoDao(): VideoDao
}