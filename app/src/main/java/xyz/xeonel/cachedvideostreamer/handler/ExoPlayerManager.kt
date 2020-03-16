package xyz.xeonel.cachedvideostreamer.handler

import android.content.Context
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelector

// Manages a single instance of exoplayer
class ExoPlayerManager {

    private lateinit var renderersFactory : RenderersFactory
    private lateinit var trackSelector : TrackSelector
    private lateinit var loadControl : LoadControl
    private lateinit var player : ExoPlayer

    public fun initializePlayer(context: Context) {
        renderersFactory = DefaultRenderersFactory(context.applicationContext)
        trackSelector = DefaultTrackSelector()
        loadControl = DefaultLoadControl()
        player = ExoPlayerFactory.newSimpleInstance(context, renderersFactory, trackSelector, loadControl)
    }

    public fun getPlayer() : ExoPlayer {
        return player
    }

    // Making ExoplayerManager singleton
    companion object{
        @Volatile private var instance: ExoPlayerManager? = null

        fun getInstance() = instance ?: synchronized(this) {
            instance ?: ExoPlayerManager().also { instance = it }
        }

    }
}