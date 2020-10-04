package com.yeletskyiv.nitrixvideotask.ui

import android.app.PendingIntent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.yeletskyiv.nitrixvideotask.R
import com.yeletskyiv.nitrixvideotask.viemodel.VideoViewModel
import kotlinx.android.synthetic.main.activity_video.*
import org.koin.android.viewmodel.ext.android.viewModel

class VideoActivity : AppCompatActivity() {

    private lateinit var player: SimpleExoPlayer
    private lateinit var playerNotificationManager: PlayerNotificationManager

    private val videoViewModel: VideoViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        val intent = intent
        val position = intent.getIntExtra("position", 0)
        val videoNumbers = intent.getIntegerArrayListExtra("video_number")

        playerNotificationManager = PlayerNotificationManager.createWithNotificationChannel(
            this,
            getString(R.string.channel_name),
            R.string.channel_name,
            R.string.channel_description,
            1234,
            object : PlayerNotificationManager.MediaDescriptionAdapter {

                override fun getCurrentContentTitle(player: Player): CharSequence {
                    return getString(R.string.player)
                }

                override fun createCurrentContentIntent(player: Player): PendingIntent? {
                    return null
                }

                override fun getCurrentContentText(player: Player): CharSequence? {
                    return getString(R.string.video) + videoNumbers?.get(player.currentWindowIndex)
                }

                override fun getCurrentLargeIcon(
                    player: Player,
                    callback: PlayerNotificationManager.BitmapCallback
                ): Bitmap? {
                    return null
                }
            }
        )

        player = SimpleExoPlayer.Builder(this).build()
        videoViewModel.videoData.observe(this) {
            player.setMediaItems(it)
        }
        player.seekTo(position, 0)

        playerNotificationManager.setPlayer(player)
        playerView.player = player
        player.prepare()
        player.play()
    }

    override fun onDestroy() {
        playerNotificationManager.setPlayer(null)
        player.release()
        super.onDestroy()
    }
}