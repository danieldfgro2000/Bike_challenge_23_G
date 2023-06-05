package com.bikechallenge23g.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "rides")
data class Ride(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String? = "",
    val bikeId: Int? = null,
    val distance: Double? = null,
    val duration: Int? = null,
    val date: Long? = null
) : Serializable
