package xyz.xeonel.cachedvideostreamer.model

import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.database.DatabaseProvider
import com.google.android.exoplayer2.database.ExoDatabaseProvider
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import xyz.xeonel.cachedvideostreamer.handlers.PlaybackHandler
import xyz.xeonel.cachedvideostreamer.view.ViewCallbacks
import java.io.File

class VideoData(context: Context) {
    // List of URLs that will be streamed or cached for viewing
    public val videoURLs: ArrayList<String> = ArrayList()
    // Map that contains Exoplayer objects with respect to the position.
    private val playerMap =  HashMap<Int,Player>()


    private val renderersFactory = DefaultRenderersFactory(context.applicationContext)
    private val trackSelector = DefaultTrackSelector()
    private val loadControl = DefaultLoadControl()
    private val userAgent : String = "ExoVideoStreamer";
    private val cacheFolder = File(context.filesDir, "media")   // Using filesDir instead of cacheDir because Videos are usually large
    private val cacheEvictor = LeastRecentlyUsedCacheEvictor(100 * 1024 * 1024) // 100MB given for cache
    private val databaseProvider: DatabaseProvider = ExoDatabaseProvider(context)
    private val cache = SimpleCache(cacheFolder, cacheEvictor,databaseProvider)
    private val cacheDataSourceFactory = CacheDataSourceFactory(cache, DefaultHttpDataSourceFactory(userAgent))

    // Load URLs into the list to be prepared for streaming
    private fun setURLs() {
        videoURLs.add("https://cdn.trell.co/h_640,w_640/user-videos/videos/orig/P7sls2VpRAJW8Vbz6rnUlU6WvLTZwEhp.mp4");
        videoURLs.add("https://cdn.trell.co/h_640,w_640/user-videos/videos/orig/8ijoZpmyyWR6Kt1pOESjSZSJ4vES0s73.mp4");
        videoURLs.add("https://cdn.trell.co/h_640,w_640/user-videos/videos/orig/XRa8qdlzCvuMIhcRLcqrNYJlKNKa5OKB.mp4");
        videoURLs.add("https://cdn.trell.co/h_640,w_640/user-videos/videos/orig/j65taHwHTw4mCpbA5moVjVO6frzwkD3u.mp4");
        videoURLs.add("https://cdn.trell.co/h_640,w_640/user-videos/videos/orig/8V7AyVafhbyMH2aWOL4xZdp8POAjskxn.mp4");
        videoURLs.add("https://cdn.trell.co/h_640,w_640/user-videos/videos/orig/W1snOQYcmY2Wv06pF0gZZivFnyWUgnuj.mp4");
        videoURLs.add("https://cdn.trell.co/h_640,w_640/user-videos/videos/orig/8COPuaSXvzyqzM4MG3FCRZNwVGxFmEEd.mp4");
        videoURLs.add("https://cdn.trell.co/h_640,w_640/user-videos/videos/orig/UdTgjehMxzugb7TN4O4Ycg5QVgqlojx8.mp4");
        videoURLs.add("https://cdn.trell.co/h_640,w_640/user-videos/videos/orig/r7EsSAGF6a1q2vXdZXFDUCTJ7wMLBGEO.mp4");
        videoURLs.add("https://cdn.trell.co/h_640,w_640/user-videos/videos/orig/fDkn4hLtkApjqyVq6vEItYKUcr8Kgxlf.mp4");
    }

    public fun getPlayerMap() : HashMap<Int,Player> {
        return playerMap
    }

    public fun initializeModel() {
        Log.v("VideoData","Initializing model");
        setURLs()
    }

    // To be used when the list of URLs are refreshed so new videos will be loaded in the map
    private fun clearPlayerMapping() {
        playerMap.clear()
    }


    //This either creates or returns a stream which is already created for a particular position
    public fun provisionStream(context: Context, position: Int, viewCallbacks: ViewCallbacks) : Player? {
        if (playerMap.containsKey(position)) {
            return playerMap[position]
        }
        if (position > videoURLs.size - 1) {
            return null
        }
        val player = ExoPlayerFactory.newSimpleInstance(context, renderersFactory, trackSelector, loadControl)
        val videoSource = ProgressiveMediaSource
            .Factory(cacheDataSourceFactory)
            .createMediaSource(Uri.parse(videoURLs[position]))
        Log.v("VideoStream","provisionStream: " + videoURLs[position]);
        player.prepare(videoSource)
        player.addListener(PlaybackHandler(viewCallbacks))
        playerMap[position] = player
        return player
    }
}