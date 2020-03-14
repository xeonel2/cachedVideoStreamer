package xyz.xeonel.cachedvideostreamer.viewmodel

import android.util.Log
import com.google.android.exoplayer2.Player
import xyz.xeonel.cachedvideostreamer.model.VideoData

class PlaybackHandler : Player.EventListener{

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        //When video finished
        if (playbackState == Player.STATE_ENDED ) {
            Log.v("VideoStream", "Video Ended")
        }
    }

    companion object {
        fun stopOtherPlayback(videoData: VideoData, position: Int) {
            Log.v("VideoStream", "PlaybackHandler stopping stream other than $position")
            for (key in videoData.getPlayerMap().keys) {
                if (key != position) {
                    videoData.getPlayerMap()[key]?.playWhenReady = false
                }
            }
        }
    }


}