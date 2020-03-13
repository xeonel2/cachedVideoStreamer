package xyz.xeonel.cachedvideostreamer.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.video_list_item.view.*

class VideoHoldingFrame (view: View) : RecyclerView.ViewHolder(view) {
    val exoVideoView = view.simpleExoPlayerView;
}