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
import java.io.File

class VideoData(context: Context) {
    // List of URLs that will be streamed or cached for viewing
    public val videoURLs: ArrayList<String> = ArrayList();
    private val renderersFactory = DefaultRenderersFactory(context.applicationContext)
    private val trackSelector = DefaultTrackSelector()
    private val loadControl = DefaultLoadControl()
    private val userAgent : String = "ExoVideoStreamer";
    private val cacheFolder = File(context.filesDir, "media")
    private val cacheEvictor = LeastRecentlyUsedCacheEvictor(30 * 1024 * 1024)
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

    public fun initializeModel() {
        Log.v("VideoData","Initializing model");
        setURLs()
    }

    public fun provisionStream(context: Context, position: Int) : Player {
        val player = ExoPlayerFactory.newSimpleInstance(context, renderersFactory, trackSelector, loadControl)
        val videoSource = ProgressiveMediaSource
            .Factory(cacheDataSourceFactory)
            .createMediaSource(Uri.parse(videoURLs[position]))
        Log.v("VideoStream","provisionStream: " + videoURLs[position]);
        player.prepare(videoSource)
        return player
    }
}