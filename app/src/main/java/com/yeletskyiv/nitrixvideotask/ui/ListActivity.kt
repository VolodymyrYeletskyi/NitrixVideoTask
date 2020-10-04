package com.yeletskyiv.nitrixvideotask.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.yeletskyiv.nitrixvideotask.recyclerview.CellClickListener
import com.yeletskyiv.nitrixvideotask.viemodel.ListViewModel
import com.yeletskyiv.nitrixvideotask.R
import com.yeletskyiv.nitrixvideotask.recyclerview.VideoAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.runBlocking
import org.koin.android.viewmodel.ext.android.viewModel

class ListActivity : AppCompatActivity(), CellClickListener {

    private val videoAdapter = VideoAdapter(this)

    private val listViewModel: ListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listViewModel.getVideos()
        listViewModel.videoData.observe(this) {
            if (it.isEmpty()) {
                listViewModel.showOnline()
                runBlocking { listViewModel.downloadVideos(applicationContext) }
            }
            else videoAdapter.setElements(it)
        }
        val divider = DividerItemDecoration(this, RecyclerView.VERTICAL)
        videoRv.addItemDecoration(divider)
        videoRv.adapter = videoAdapter
    }

    override fun onCellClickListener(position: Int) {
        val intent = Intent(this, VideoActivity::class.java)
        intent.putExtra("position", position)
        intent.putIntegerArrayListExtra("video_number", arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
        startActivity(intent)
    }
}