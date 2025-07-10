package edu.unibo.tracker.Database

import javax.inject.Inject

class TrackRepository @Inject constructor(private val trackDAO: TrackDAO){

    suspend fun addTrack(newTrack: Track){
        trackDAO.insertTrack(newTrack)
    }
    suspend fun getAllTrack(): List<Track> {
        return trackDAO.getTrack()
    }
}