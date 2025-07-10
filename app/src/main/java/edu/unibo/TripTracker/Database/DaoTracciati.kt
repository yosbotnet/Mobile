package edu.unibo.tracker.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TrackDAO{

    @Insert(onConflict = OnConflictStrategy.IGNORE) //  INSERIMENTO TRACCIATO
    suspend fun insertTrack(tracciati: Track)

    @Query("SELECT * FROM Track")
    suspend fun getTrack() : List<Track>

    @Query("SELECT * FROM Track WHERE km<35")
    suspend fun getShortTrack() : List<Track>

}
