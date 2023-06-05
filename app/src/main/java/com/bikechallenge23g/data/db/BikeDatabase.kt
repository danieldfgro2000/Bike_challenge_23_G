package com.bikechallenge23g.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bikechallenge23g.data.model.Bike
import com.bikechallenge23g.data.model.Ride

@Database(entities = [Bike::class, Ride::class], version = 1, exportSchema = false)
abstract class BikeDatabase : RoomDatabase() {
    abstract fun getBikeDao(): BikeDao

    abstract fun getRideDao(): RideDao
}