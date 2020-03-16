package xyz.xeonel.cachedvideostreamer.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import kotlinx.android.synthetic.main.activity_main.*
import xyz.xeonel.cachedvideostreamer.R
import xyz.xeonel.cachedvideostreamer.adapter.VideoStreamAdapter


class VideoViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Snap to video views when scrolling

        val snapHelper: PagerSnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(mainRecyclerView)

        //Vertical layout in layout manager
        mainRecyclerView.layoutManager = LinearLayoutManager(this)
        mainRecyclerView.adapter =
            VideoStreamAdapter(this)
    }
}
