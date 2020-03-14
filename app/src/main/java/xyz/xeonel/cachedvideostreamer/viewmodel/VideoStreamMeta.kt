package xyz.xeonel.cachedvideostreamer.viewmodel

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

data class VideoStreamMeta (var _videoURL: String, var _finishedPlaying: Boolean) : BaseObservable(){
//    var videoURL: String
//        @Bindable get() = _videoURL
//        set(value) {
//            _videoURL = value
////            notifyPropertyChanged(BR.videoURL)
//
//        }
//
//    var finishedPlaying: Boolean
//        @Bindable get() = _finishedPlaying
//        set(value) {
//            _finishedPlaying = value
////            notifyPropertyChanged(BR.finishedPlaying)
//        }

}