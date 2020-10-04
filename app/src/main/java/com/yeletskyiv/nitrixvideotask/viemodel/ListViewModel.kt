package com.yeletskyiv.nitrixvideotask.viemodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yeletskyiv.nitrixvideotask.room.dao.VideoDao
import com.yeletskyiv.nitrixvideotask.room.entity.Video
import com.yeletskyiv.nitrixvideotask.retrofit.BASE_URL
import com.yeletskyiv.nitrixvideotask.retrofit.VideoApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File

class ListViewModel (private val videoApi: VideoApi, private val dao: VideoDao) : ViewModel() {

    private val videoNames = mutableListOf<String>()

    val videoData: MutableLiveData<List<Video>> = MutableLiveData()

    init {
        for (i in 1..10) {
            videoNames.add("$i.mp4")
        }
    }

    fun showOnline() {
        val onlineLinks = mutableListOf<Video>()
        for (ref in videoNames) {
            onlineLinks.add(Video("Video$ref", BASE_URL + ref))
        }
        videoData.postValue(onlineLinks)
    }

    suspend fun downloadVideos(context: Context) = viewModelScope.launch(Dispatchers.IO) {
        for (ref in videoNames) {
            val response =videoApi.getVideos(ref)
            if(response.isSuccessful) {
                val bytes = async(Dispatchers.IO) { response.body()?.bytes() }
                val dir = context.cacheDir
                val file = File(dir.absolutePath + "/" + ref + ".txt")
                launch(Dispatchers.Default) {
                    bytes.await()?.let { file.writeBytes(it) }
                }
                launch(Dispatchers.Unconfined) {
                    dao.insertVideo(Video("Video$ref", file.absolutePath))
                }
            }
            else {
                throw IllegalArgumentException("Illegal argument exception")
            }
        }
    }

    fun getVideos() = runBlocking(Dispatchers.IO){
        val videos = dao.getAllVideos()
        videoData.postValue(videos)
    }
}