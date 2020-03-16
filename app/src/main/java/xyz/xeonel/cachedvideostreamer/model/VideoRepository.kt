package xyz.xeonel.cachedvideostreamer.model

import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.android.exoplayer2.database.DatabaseProvider
import com.google.android.exoplayer2.database.ExoDatabaseProvider
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.upstream.cache.*
import xyz.xeonel.cachedvideostreamer.config.ApplicationConfig
import java.io.File

class VideoRepository() {

    // List of URLs that will be streamed or cached for viewing
    public val videoURLs: MutableList<String> = mutableListOf<String>()
    // Map that contains MediaSource objects with respect to the URL.
    private val mediaSourceMap =  HashMap<String,MediaSource>()

    private val userAgent : String = "ExoVideoStreamer";
    private lateinit var cacheFolder : File
    private lateinit var cacheEvictor : CacheEvictor
    private lateinit var databaseProvider: DatabaseProvider
    private lateinit var cache : Cache
    private lateinit var cacheDataSourceFactory : CacheDataSourceFactory

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

    public fun getMediaSourceMap() : HashMap<String,MediaSource> {
        return mediaSourceMap
    }

    public fun initializeModel(context: Context) {
        Log.v("VideoData","Initializing model");
        setURLs()
        cacheFolder = File(context.filesDir, "media") // Using filesDir instead of cacheDir because Videos are usually large
        cacheEvictor = LeastRecentlyUsedCacheEvictor(ApplicationConfig.cacheSizeMB * 1024 * 1024) // 100MB given for cache
        databaseProvider = ExoDatabaseProvider(context)
        cache = SimpleCache(cacheFolder, cacheEvictor,databaseProvider)
        cacheDataSourceFactory = CacheDataSourceFactory(cache, DefaultHttpDataSourceFactory(userAgent))
    }

    // To be used when the list of URLs are refreshed so new videos will be loaded in the map
    public fun clearMediaSourceMapping(videoStreamMeta: VideoStreamMeta) {
        for (k in mediaSourceMap.keys) {
            mediaSourceMap[k]?.releaseSource(null)
        }
        mediaSourceMap.clear()

        if (mediaSourceMap.containsKey(videoStreamMeta.url)) {
            mediaSourceMap[videoStreamMeta.url]?.releaseSource(null)
        }

    }


    // This either creates or returns a stream which is already created for a particular position
    public fun provisionStream(url: String) : MediaSource? {
        if (mediaSourceMap.containsKey(url)) {
            return mediaSourceMap[url]
        }

        val videoSource = ProgressiveMediaSource
            .Factory(cacheDataSourceFactory)
            .createMediaSource(Uri.parse(url))

        mediaSourceMap[url] = videoSource
        return videoSource
    }

    // Making repository a singleton class
    companion object{
        @Volatile private var instance: VideoRepository? = null

        fun getInstance() = instance ?: synchronized(this) {
            instance ?: VideoRepository().also { instance = it }
        }
    }
}