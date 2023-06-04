package com.bikechallenge23g.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bikechallenge23g.data.model.Bike
import com.bikechallenge23g.data.model.enums.DistanceUnit
import kotlinx.coroutines.flow.Flow

@Dao
interface BikeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveBike(bike: Bike)

    @Query("UPDATE bikes SET isDefault = CASE WHEN id != :bikeId THEN 0 ELSE 1 END")
    fun updateDefaultBike(bikeId: Int)

    @Query("UPDATE bikes SET isServiceReminderActive = :isReminderActive WHERE id == :bikeId")
    fun updateServiceReminder(isReminderActive: Boolean, bikeId: Int)

    @Query("UPDATE bikes SET serviceInterval = :newInterval WHERE id == :bikeId")
    fun updateServiceInterval(bikeId: Int, newInterval: Int)

    @Query("UPDATE bikes SET distanceUnit = :newUnit WHERE id == :bikeId")
    fun updateDistanceUnit(bikeId: Int, newUnit: DistanceUnit)

    @Delete
    fun deleteBike(bike: Bike)

    @Query("SELECT * FROM bikes")
    fun getAllBikes(): Flow<List<Bike>>
}