package com.bikechallenge23g.domain.usecase

import androidx.compose.ui.graphics.Color
import com.bikechallenge23g.data.model.Bike
import com.bikechallenge23g.data.model.Ride
import com.bikechallenge23g.data.model.enums.BikeType
import com.bikechallenge23g.data.model.enums.DistanceUnit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

class CalcChartUseCase {
    suspend fun execute(
        rides: List<Ride>,
        bikes: List<Bike>
    ): Flow<Pair<Double, List<Triple<BikeType, Float, Color>>>> =
        flow { emit(createPair(rides, bikes)) }
}

fun createPair(
    rides: List<Ride>,
    bikes: List<Bike>
): Pair<Double, List<Triple<BikeType, Float, Color>>> {

    var totalKm = 0.0
    val listOfTriple = mutableListOf<Triple<BikeType, Float, Color>>()

    val ridesByBikeId = rides.groupBy { it.bikeId }

    BikeType.values().forEach { availableType ->
        val tempList = bikes.flatMap { bike ->
            ridesByBikeId[bike.id]?.filter { ride ->
                bike.type == availableType
            }.orEmpty()
        }

        val sumForType = tempList.sumOf {
            if (it.distanceUnit == DistanceUnit.MILES) {
                (it.distance ?: 0.0) * 1.6
            } else {
                it.distance ?: 0.0
            }
        }
        totalKm += sumForType
        val triple = Triple(availableType, sumForType.toFloat(), getRandomColor())
        listOfTriple.add(triple)
    }
    return Pair(totalKm, listOfTriple)
}

fun getRandomColor() = Color(
    red = Random.nextFloat() / 2f + 0.5f,
    green = Random.nextFloat() / 2f + 0.5f,
    blue = Random.nextFloat() / 2f + 0.5f
)
