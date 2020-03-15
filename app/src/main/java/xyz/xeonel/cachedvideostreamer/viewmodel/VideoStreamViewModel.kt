package xyz.xeonel.cachedvideostreamer.viewmodel

import androidx.lifecycle.ViewModel
import xyz.xeonel.cachedvideostreamer.model.VideoStreamMeta

class VideoStreamViewModel : ViewModel {
    var url : String = ""

    constructor() : super()
    constructor(videoStreamMeta: VideoStreamMeta) : super() {
        url = videoStreamMeta.url
    }
}