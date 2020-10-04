package com.yeletskyiv.nitrixvideotask.recyclerview

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.yeletskyiv.nitrixvideotask.R
import com.yeletskyiv.nitrixvideotask.room.entity.Video
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.video_item.view.*

class VideoAdapter(val cellClickListener: CellClickListener) :
    RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    private val items = mutableListOf<Video>()

    fun setElements(elements: List<Video>) {
        items.addAll(elements)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val layoutId = getLayoutId()
        val view = inflater.inflate(layoutId, parent, false)
        return getViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    private fun getLayoutId() = R.layout.video_item

    private fun getViewHolder(view: View) = VideoViewHolder(view)

    inner class VideoViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bind(data: Video) {
            containerView.setOnClickListener {
                cellClickListener.onCellClickListener(adapterPosition)
            }

            containerView.videoNameTextView.text = data.name

            val player = SimpleExoPlayer.Builder(containerView.context).build()
            containerView.videoPV.player = player
            containerView.videoPV.useController = false
            val mediaItem = MediaItem.fromUri(Uri.parse(data.videoPath))
            player.setMediaItem(mediaItem)
            player.prepare()
        }
    }
}