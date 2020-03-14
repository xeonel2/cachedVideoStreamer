package xyz.xeonel.cachedvideostreamer.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import kotlinx.android.synthetic.main.activity_main.*
import xyz.xeonel.cachedvideostreamer.R
import xyz.xeonel.cachedvideostreamer.databinding.ActivityMainBinding
import xyz.xeonel.cachedvideostreamer.viewmodel.VideoStreamAdapter
import xyz.xeonel.cachedvideostreamer.viewmodel.VideoStreamMeta


class VideoViewActivity : AppCompatActivity() {

//    lateinit var binding : VideoListItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
//        binding.streamData = VideoStreamMeta("", false)

        Log.v("VideoViewActivity","start");
        //Snap to video views when scrolling
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(mainRecyclerView)
        mainRecyclerView.layoutManager = LinearLayoutManager(this)
        mainRecyclerView.adapter = VideoStreamAdapter(this)
    }
}
