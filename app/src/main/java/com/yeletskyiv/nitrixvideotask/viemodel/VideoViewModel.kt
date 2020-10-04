package com.yeletskyiv.nitrixvideotask.viemodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.exoplayer2.MediaItem
import com.yeletskyiv.nitrixvideotask.room.dao.VideoDao
import com.yeletskyiv.nitrixvideotask.room.entity.Video
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class VideoViewModel(private val dao: VideoDao) : ViewModel() {

    private val videoItems = mutableListOf<MediaItem>()
    private val videos = mutableListOf<Video>()

    val videoData: MutableLiveData<List<MediaItem>> = MutableLiveData()

    init {
        runBlocking(Dispatchers.IO) { videos.addAll(dao.getAllVideos()) }
        for (video in videos) {
            videoItems.add(MediaItem.fromUri(video.videoPath))
        }
        videoData.value = videoItems
    }
}