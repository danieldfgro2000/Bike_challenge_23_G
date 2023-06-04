package com.bikechallenge23g.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bikechallenge23g.data.model.enums.BikeColors
import com.bikechallenge23g.data.model.enums.BikeTypes
import com.bikechallenge23g.data.model.enums.BikeWheels
import java.io.Serializable

@Entity(tableName = "bikes")
data class Bike(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val type: BikeTypes = BikeTypes.ELECTRIC,
    val isDefault: Boolean = false,
    val model: String = "",
    val bikeColor: BikeColors = BikeColors.BLUE,
    val wheelSize: BikeWheels = BikeWheels.BIG,
    val dueService: String = "100",
    val isServiceReminderActive: Boolean = true,
    val distance: Double = 0.0
) : Serializable
