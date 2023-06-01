package com.bikechallenge23g.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bikechallenge23g.data.model.enums.BikeTypes
import com.bikechallenge23g.data.model.enums.BikeWheels
import java.io.Serializable

@Entity(tableName = "bikes")
data class Bike(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val type: BikeTypes,
    val isDefault: Boolean = false,
    val model: String,
    val Color: String,
    val wheelSize: BikeWheels,
    val dueService: String,
    val distance: String
) : Serializable
