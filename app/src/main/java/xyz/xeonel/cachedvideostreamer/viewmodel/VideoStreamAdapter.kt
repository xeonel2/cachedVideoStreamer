package xyz.xeonel.cachedvideostreamer.viewmodel

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import xyz.xeonel.cachedvideostreamer.R
import xyz.xeonel.cachedvideostreamer.databinding.VideoListItemBinding
import xyz.xeonel.cachedvideostreamer.handler.PlaybackHandler
import xyz.xeonel.cachedvideostreamer.model.VideoRepository
import xyz.xeonel.cachedvideostreamer.model.VideoStreamMeta
import xyz.xeonel.cachedvideostreamer.view.VideoViewHolder
import xyz.xeonel.cachedvideostreamer.view.ViewCallbacks


class VideoStreamAdapter (val context: Context) : RecyclerView.Adapter<VideoViewHolder>() {

    private lateinit var recyclerView: RecyclerView
    private val renderersFactory = DefaultRenderersFactory(context.applicationContext)
    private val trackSelector = DefaultTrackSelector()
    private val loadControl = DefaultLoadControl()

    //define model (video data)
    private val videoData = VideoRepository.getInstance()

    init {
        VideoRepository.getInstance().initializeModel(context)
    }

    override fun getItemCount(): Int {
        return VideoRepository.getInstance().videoURLs.size
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
            this.recyclerView = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {

        val binding = DataBindingUtil.inflate<VideoListItemBinding>(LayoutInflater.from(parent.context), R.layout.video_list_item, parent, false)

        return VideoViewHolder(binding)
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

        holder.binding.run {
            videoStreamMeta = VideoStreamMeta(VideoRepository.getInstance().videoURLs[position], PlaybackHandler(ViewCallbacks(recyclerView)))
        }
        // Get Media sources current and next
        val currentMediaSource : MediaSource? = VideoRepository.getInstance().provisionStream(position)
        val nextMediaSource : MediaSource? = VideoRepository.getInstance().provisionStream(position + 1)

        // Create ExoPlayer
        val player = ExoPlayerFactory.newSimpleInstance(context, renderersFactory, trackSelector, loadControl)
        holder.exoVideoView?.player = player

        // Prepare current stream or next stream also if next item exists also trying out run xD
        player.prepare(run{
            if (nextMediaSource != null) ConcatenatingMediaSource(currentMediaSource, nextMediaSource)
            else currentMediaSource
        })
    }


}