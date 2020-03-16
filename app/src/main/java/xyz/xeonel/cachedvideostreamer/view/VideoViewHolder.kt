package xyz.xeonel.cachedvideostreamer.view

import androidx.recyclerview.widget.RecyclerView
import xyz.xeonel.cachedvideostreamer.databinding.VideoListItemBinding

class VideoViewHolder (val binding: VideoListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    val exoVideoView = binding.simpleExoPlayerView;
}