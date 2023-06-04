package com.bikechallenge23g.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "rides")
data class Ride(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String? = "",
    val bikeId: Int,
    val distance: Double,
    val duration: Int,
    val date: Long
) : Serializable
