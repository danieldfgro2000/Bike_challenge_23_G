package com.bikechallenge23g.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bikechallenge23g.data.model.enums.BikeColors
import com.bikechallenge23g.data.model.enums.BikeTypes
import com.bikechallenge23g.data.model.enums.BikeWheels
import com.bikechallenge23g.data.model.enums.DistanceUnits
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
    val serviceIn: Int = 170,
    val serviceInterval: Int = 500,
    val isServiceReminderActive: Boolean = true,
    val distance: Double = 0.0,
    val distanceUnit: DistanceUnits = DistanceUnits.KM
) : Serializable
