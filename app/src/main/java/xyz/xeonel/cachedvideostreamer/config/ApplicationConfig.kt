package xyz.xeonel.cachedvideostreamer.config

object ApplicationConfig {
    // Number of Exoplayers in the pool to allocate when in need
    // Give a number >= 2
    // Suggested number 3(for precaching next video) .
    // 2 Shows smooth playback but no precaching
    // If normal SnapHelper used instead of PagerSnapHelper,
    // then it is suggested to give more numbers as more viewholders are in use when scrolling fast
    val numberOFExoplayers = 3

    // Cache size for local cache storage in MB
    val cacheSizeMB : Long = 100
}