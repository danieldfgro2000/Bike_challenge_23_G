package com.bikechallenge23g.data.model.enums

enum class BikeComponents(val category: String) {
    OVER("Over"),
    MIDDLE("Middle"),
    UNDER("Under")
}

enum class BikeWheels(val nick: String, val size: String) {
    BIG("Big", "29\""),
    SMALL("Small", "26\"")
}