package xyz.xeonel.cachedvideostreamer.viewmodel

import android.util.Log
import androidx.databinding.BindingAdapter
import com.google.android.exoplayer2.ui.PlayerView
import xyz.xeonel.cachedvideostreamer.model.VideoStreamMeta

@BindingAdapter("stream_meta")
fun PlayerView.stream(videoStreamMeta: VideoStreamMeta) {
    Log.v("VideoStream", "BindingAdapter Called" + videoStreamMeta.url)
}