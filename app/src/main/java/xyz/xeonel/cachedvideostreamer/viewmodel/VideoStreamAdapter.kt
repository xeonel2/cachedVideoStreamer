package xyz.xeonel.cachedvideostreamer.viewmodel

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.Player
import xyz.xeonel.cachedvideostreamer.R
import xyz.xeonel.cachedvideostreamer.view.VideoViewHolder
import xyz.xeonel.cachedvideostreamer.model.VideoData


class VideoStreamAdapter ( val context: Context) : RecyclerView.Adapter<VideoViewHolder>() {

    //define model (video data)
    private val videoData = VideoData(context)

    init {
        videoData.initializeModel()
    }

    override fun getItemCount(): Int {
        return videoData.videoURLs.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        // Initialize Exoplayer
        Log.v("VideoStreamAdapter","onCreateViewHolder");
        return VideoViewHolder(LayoutInflater.from(context).inflate(R.layout.video_list_item, parent, false))

    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val player : Player? = videoData.provisionStream(context, position)
        if (player != null) {
            videoData.provisionStream(context, position + 1)
            holder.exoVideoView?.player = player
            player.playWhenReady = true
        }
    }

}