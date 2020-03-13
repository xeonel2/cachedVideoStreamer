package xyz.xeonel.cachedvideostreamer.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import xyz.xeonel.cachedvideostreamer.R
import xyz.xeonel.cachedvideostreamer.view.VideoViewHolder
import xyz.xeonel.cachedvideostreamer.model.VideoData


class VideoStreamAdapter ( val context: Context) : RecyclerView.Adapter<VideoViewHolder>() {

    //define model (video data)
    val videoData = VideoData()
    val UserAgent : String = "ExoVideoStreamer";

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
        val player = ExoPlayerFactory.newSimpleInstance(context, DefaultRenderersFactory(context), DefaultTrackSelector(), DefaultLoadControl())
        holder.exoVideoView?.player = player
        val videoSource = ProgressiveMediaSource
            .Factory(DefaultDataSourceFactory(context, UserAgent))
            .createMediaSource(Uri.parse(videoData.videoURLs[position]))
        Log.v("VideoStreamAdapter","Binding" + videoData.videoURLs[position]);
        player.prepare(videoSource)
        player.playWhenReady = true
    }

}