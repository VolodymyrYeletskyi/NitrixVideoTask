package com.yeletskyiv.nitrixvideotask.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Video(
    @PrimaryKey val name: String,
    val videoPath: String
)