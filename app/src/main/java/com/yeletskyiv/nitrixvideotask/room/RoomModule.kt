package com.yeletskyiv.nitrixvideotask.room

import androidx.room.Room
import com.yeletskyiv.nitrixvideotask.room.database.VideoDatabase
import org.koin.dsl.module.module

val roomModule = module {
    single {
        Room.databaseBuilder(get(), VideoDatabase::class.java, "videos").build()
    }
    single { get<VideoDatabase>().videoDao() }
}