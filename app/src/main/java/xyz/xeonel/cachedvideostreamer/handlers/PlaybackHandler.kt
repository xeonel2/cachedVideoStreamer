package xyz.xeonel.cachedvideostreamer.handlers

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.Player
import xyz.xeonel.cachedvideostreamer.view.ViewCallbacks

// Handles Events and playback of the Video Playes
class PlaybackHandler(val viewCallbacks: ViewCallbacks) : Player.EventListener{

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        //When video finished
        if (playbackState == Player.STATE_ENDED ) {
            Log.v("VideoStream", "Video Ended")
            //Go to next video
            viewCallbacks.scrollNext()
        }
    }

}