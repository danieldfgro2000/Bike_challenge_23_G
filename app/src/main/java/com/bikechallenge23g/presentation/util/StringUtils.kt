package com.bikechallenge23g.presentation.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter


fun longEpochToddMMyyyy(epochDateLong: Long): String {
    val dateLong = LocalDate.ofEpochDay(epochDateLong)
    val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    return dateFormatter.format(dateLong)
}

fun longEpochToDayOfWeek(epochDateLong: Long): String {
    val date = LocalDate.ofEpochDay(epochDateLong)
    return "${date.dayOfWeek} ${date.dayOfMonth}"
}

fun intTimeToHHmm(time: Int): String {
    val hours = if (time > 0) time / 60 else 0
    val minutes = if (time > 0) time % 60 else 0
    return String.format("%1sh, %2smin", hours, minutes)
}