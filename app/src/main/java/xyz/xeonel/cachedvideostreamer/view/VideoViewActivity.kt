package xyz.xeonel.cachedvideostreamer.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import kotlinx.android.synthetic.main.activity_main.*
import xyz.xeonel.cachedvideostreamer.R
import xyz.xeonel.cachedvideostreamer.viewmodel.VideoStreamAdapter

class VideoViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.v("VideoViewActivity","start");
        mainRecyclerView.layoutManager = LinearLayoutManager(this)
        mainRecyclerView.adapter = VideoStreamAdapter(this)
    }
}
