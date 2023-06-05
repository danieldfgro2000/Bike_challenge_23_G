package com.bikechallenge23g.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bikechallenge23g.data.model.enums.DistanceUnit
import java.io.Serializable

@Entity(tableName = "rides")
data class Ride(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String? = "",
    val bikeId: Int? = null,
    val distance: Double? = 0.0,
    val distanceUnit: DistanceUnit? = DistanceUnit.KM,
    val duration: Int? = null,
    val date: Long? = null
) : Serializable
