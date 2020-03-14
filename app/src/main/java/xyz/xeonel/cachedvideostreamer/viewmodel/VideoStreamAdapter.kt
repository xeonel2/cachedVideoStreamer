package xyz.xeonel.cachedvideostreamer.viewmodel

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.Player
import xyz.xeonel.cachedvideostreamer.R
import xyz.xeonel.cachedvideostreamer.model.VideoData
import xyz.xeonel.cachedvideostreamer.view.VideoViewHolder
import xyz.xeonel.cachedvideostreamer.view.ViewCallbacks


class VideoStreamAdapter (val context: Context) : RecyclerView.Adapter<VideoViewHolder>() {

    private lateinit var recyclerView: RecyclerView
    //define model (video data)
    private val videoData = VideoData(context)

    init {
        videoData.initializeModel()
    }

    override fun getItemCount(): Int {
        return videoData.videoURLs.size
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
            this.recyclerView = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        // Initialize Exoplayer view
        return VideoViewHolder(LayoutInflater.from(context).inflate(R.layout.video_list_item, parent, false))
    }

    override fun onViewAttachedToWindow(holder: VideoViewHolder) {
        //Play the video on the screen
        holder.exoVideoView?.player?.playWhenReady = true
    }

    override fun onViewDetachedFromWindow(holder: VideoViewHolder) {
        //Stop the video which is not on screen
        holder.exoVideoView?.player?.playWhenReady = false
    }


    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        // Get the player from model
        val player : Player? = videoData.provisionStream(context, position, ViewCallbacks(recyclerView))
        // Provision(Cache) the next video stream
        if (player != null) {
            videoData.provisionStream(context, position + 1, ViewCallbacks(recyclerView))
            holder.exoVideoView?.player = player
        }
    }

}