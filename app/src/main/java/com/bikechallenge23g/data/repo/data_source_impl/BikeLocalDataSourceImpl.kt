package com.bikechallenge23g.data.repo.data_source_impl

import com.bikechallenge23g.data.db.BikeDao
import com.bikechallenge23g.data.model.Bike
import com.bikechallenge23g.data.repo.data_source.BikeLocalDataSource
import kotlinx.coroutines.flow.Flow

class BikeLocalDataSourceImpl(
    private val bikeDao: BikeDao
) : BikeLocalDataSource{
    override fun getSavedBikes(): Flow<List<Bike>> = bikeDao.getAllBikes()
}