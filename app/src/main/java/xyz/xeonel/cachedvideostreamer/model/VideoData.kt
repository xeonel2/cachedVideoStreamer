package xyz.xeonel.cachedvideostreamer.model

import android.util.Log

class VideoData {
    // List of URLs that will be streamed or cached for viewing
    public val videoURLs: ArrayList<String> = ArrayList();

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
}