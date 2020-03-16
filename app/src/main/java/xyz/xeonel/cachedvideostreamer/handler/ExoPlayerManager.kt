package xyz.xeonel.cachedvideostreamer.handler

import android.content.Context
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelector
import xyz.xeonel.cachedvideostreamer.model.VideoRepository

// Manages exoplayer instances
class ExoPlayerManager {

    private lateinit var renderersFactory : RenderersFactory
    private lateinit var trackSelector : TrackSelector
    private lateinit var loadControl : LoadControl
    val availablePlayers: MutableSet<ExoPlayer> = mutableSetOf()
    var preparedURL : String? = null
    var nextPlayer : ExoPlayer? = null

    public fun initializePlayerManager(context: Context, numberOFPlayers: Int) {
        renderersFactory = DefaultRenderersFactory(context.applicationContext)
        trackSelector = DefaultTrackSelector()
        loadControl = DefaultLoadControl()

        val playerCount = if (numberOFPlayers < 2) 2 else numberOFPlayers
        // Create players and add to available players
        for (x in 1..playerCount) {
            availablePlayers.add(
                ExoPlayerFactory.newSimpleInstance(
                    context,
                    renderersFactory,
                    trackSelector,
                    loadControl
                )
            )
        }
    }

    // Return an available Exoplayer from the available pool
    private fun getFreePlayer() : ExoPlayer? {
        val player = availablePlayers.lastOrNull()
        if (player != null){
            availablePlayers.remove(player)
        }
        return player
    }

    // Return a prepared player for the URL
    public fun getPlayerForURL(url: String) : ExoPlayer? {
        if (url == preparedURL) {
            // If the URL is already prepared for playing
            return nextPlayer
        }

        val player = getFreePlayer()
        player?.prepare(VideoRepository.getInstance().provisionStream(url))
        return player
    }

    // Prepare the URL for playing next
    public fun preparePlayerForURL(url: String?) {
        if (url !=null && preparedURL != url) {
            val player = getFreePlayer() ?: return
            player.prepare(VideoRepository.getInstance().provisionStream(url!!))
            player.playWhenReady = false
            nextPlayer = player
            preparedURL = url
        }
        return
    }

    // Player not attached to any ViewHolder. Added back to available pool
    fun playerAvailable(exoPlayer: ExoPlayer) {
        availablePlayers.add(exoPlayer)
    }

    // Making ExoplayerManager singleton
    companion object{
        @Volatile private var instance: ExoPlayerManager? = null

        fun getInstance() = instance ?: synchronized(this) {
            instance ?: ExoPlayerManager().also { instance = it }
        }

    }
}