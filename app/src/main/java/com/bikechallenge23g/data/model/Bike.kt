package com.bikechallenge23g.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bikechallenge23g.data.model.enums.BikeColor
import com.bikechallenge23g.data.model.enums.BikeType
import com.bikechallenge23g.data.model.enums.BikeWheel
import com.bikechallenge23g.data.model.enums.DistanceUnit
import java.io.Serializable

@Entity(tableName = "bikes")
data class Bike(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val type: BikeType? = BikeType.ELECTRIC,
    val isDefault: Boolean? = false,
    val model: String? = "",
    val bikeColor: BikeColor? = BikeColor.BLUE,
    val wheelSize: BikeWheel? = BikeWheel.BIG,
    val serviceIn: Int? = 100,
    val serviceInterval: Int? = 100,
    val isServiceReminderActive: Boolean? = true,
    val distance: Double? = 0.0,
    val distanceUnit: DistanceUnit? = DistanceUnit.KM
) : Serializable
