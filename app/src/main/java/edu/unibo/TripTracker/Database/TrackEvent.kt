package edu.unibo.tracker.Database

sealed class TrackEvent{
    data class AddTrack(val track: Track) : TrackEvent()
    object GetAllTrack : TrackEvent()
}
