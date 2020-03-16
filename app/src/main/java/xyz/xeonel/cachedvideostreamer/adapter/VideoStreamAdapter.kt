package xyz.xeonel.cachedvideostreamer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.source.MediaSource
import xyz.xeonel.cachedvideostreamer.R
import xyz.xeonel.cachedvideostreamer.databinding.VideoListItemBinding
import xyz.xeonel.cachedvideostreamer.handler.ExoPlayerManager
import xyz.xeonel.cachedvideostreamer.handler.PlaybackHandler
import xyz.xeonel.cachedvideostreamer.model.VideoRepository
import xyz.xeonel.cachedvideostreamer.model.VideoStreamMeta
import xyz.xeonel.cachedvideostreamer.view.VideoViewHolder
import xyz.xeonel.cachedvideostreamer.view.ViewCallbacks


class VideoStreamAdapter (val context: Context) : RecyclerView.Adapter<VideoViewHolder>() {

    private lateinit var recyclerView: RecyclerView

    //define model (video data)
    private val videoRepository = VideoRepository.getInstance()

    init {
        VideoRepository.getInstance().initializeModel(context)
        ExoPlayerManager.getInstance().initializePlayer(context)
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
        val player = ExoPlayerManager.getInstance().getPlayer()

        player.prepare(VideoRepository.getInstance().provisionStream(holder.binding.videoStreamMeta!!.url))
        player.addListener(holder.binding.videoStreamMeta!!.playbackHandler)
        holder.exoVideoView?.player = player
        holder.exoVideoView?.player?.playWhenReady = true
    }

    override fun onViewDetachedFromWindow(holder: VideoViewHolder) {
        //Stop the video which is not on screen
//        holder.exoVideoView?.player?.playWhenReady = false
    }

    override fun onViewRecycled(holder: VideoViewHolder) {
        // Release the mediaSource when the video holder gets recycled
        VideoRepository.getInstance().clearMediaSourceMapping(holder.binding.videoStreamMeta!!)
    }


    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {

        holder.binding.run {
            videoStreamMeta = VideoStreamMeta(videoRepository.videoURLs[position], PlaybackHandler(
                ViewCallbacks(recyclerView)
            ))
        }
        // Get Media sources current and next
        val currentMediaSource : MediaSource? = videoRepository.provisionStream(videoRepository.videoURLs[position])
        val nextMediaSource : MediaSource? = if (videoRepository.videoURLs.size - 1 > position)
            videoRepository.provisionStream(videoRepository.videoURLs[position + 1])
            else null


        // Prepare current stream or next stream also if next item exists also trying out run xD
//        player.prepare(run{
//            if (nextMediaSource != null) ConcatenatingMediaSource(true, currentMediaSource, nextMediaSource)
//            else currentMediaSource
//        })
    }


}