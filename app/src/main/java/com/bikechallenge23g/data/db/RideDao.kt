package com.bikechallenge23g.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bikechallenge23g.data.model.Ride
import kotlinx.coroutines.flow.Flow

@Dao
interface RideDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveRide(ride: Ride)

    @Delete
    fun deleteRide(ride: Ride)

    @Query("SELECT * from rides ORDER BY date DESC")
    fun getRides(): Flow<List<Ride>>
}