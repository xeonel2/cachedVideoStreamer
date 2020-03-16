package xyz.xeonel.cachedvideostreamer.view

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// Callback functions to be used by Handlers
class ViewCallbacks(val recyclerView: RecyclerView, val nextURL: String?) {

    //Scroll to the next item in the RecyclerView
    public fun scrollNext() {
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        recyclerView.scrollToPosition(layoutManager.findFirstVisibleItemPosition() + 1)
    }
}