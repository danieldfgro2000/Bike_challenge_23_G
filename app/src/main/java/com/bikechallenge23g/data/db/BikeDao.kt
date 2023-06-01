package com.bikechallenge23g.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bikechallenge23g.data.model.Bike
import kotlinx.coroutines.flow.Flow

@Dao
interface BikeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(bike: Bike)

    @Query("SELECT * FROM bikes")
    fun getAllBikes() : Flow<List<Bike>>

    @Delete
    suspend fun deleteBike(bike: Bike)
}