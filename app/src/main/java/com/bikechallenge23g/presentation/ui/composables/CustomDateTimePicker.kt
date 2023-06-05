package com.bikechallenge23g.presentation.ui.composables


import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import com.marosseleng.compose.material3.datetimepickers.date.ui.dialog.DatePickerDialog
import com.marosseleng.compose.material3.datetimepickers.time.ui.dialog.TimePickerDialog
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun CustomDateTimePicker(
    modifier: Modifier = Modifier,
    selectedTime: Int? = 0,
    selectedDate: Long? = 0,
    isDatePicker: Boolean = false,
    onTimeSelected: (Int) -> Unit = {},
    onDateSelected: (Long) -> Unit = {}
) {
    var showTimePickerDialog by remember { mutableStateOf(false) }
    var showDatePickerDialog by remember { mutableStateOf(false) }
    var date: LocalDate? by remember { mutableStateOf(LocalDate.now()) }

    var hours = 0
    var minutes = 0

    selectedTime?.let {
        hours = if (selectedTime > 0) selectedTime / 60 else 0
        minutes = if (selectedTime > 0) selectedTime % 60 else 0
    }

    val currentRideDate = LocalDate.ofEpochDay(selectedDate ?: LocalDate.now().toEpochDay())
    val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    val formattedDate = currentRideDate.format(dateFormatter)

    val displayedDateTime =
        if (isDatePicker) {
            formattedDate
        } else String.format("%1sh, %2smin", hours, minutes)

    var calculatedTime: Int


    Surface(modifier = modifier) {
        CustomCardWithDropDown(text = displayedDateTime) {
            if (isDatePicker) showDatePickerDialog = true
            else showTimePickerDialog = true
        }
        if (showTimePickerDialog) {
            TimePickerDialog(
                initialTime = LocalTime.MIDNIGHT,
                is24HourFormat = true,
                onDismissRequest = { showTimePickerDialog = false },
                onTimeChange = {
                    showTimePickerDialog = false
                    calculatedTime = it.hour * 60 + it.minute
                    onTimeSelected(calculatedTime)
                }
            )
        }
        if (showDatePickerDialog) {
            DatePickerDialog(
                initialDate = date,
                onDismissRequest = { showDatePickerDialog = false },
                onDateChange = { localDate: LocalDate ->
                    showDatePickerDialog = false
                    date = localDate
                    Log.e("Date  = ", "${localDate.toEpochDay()}")
                    onDateSelected(localDate.toEpochDay())
                }
            )
        }
    }
}

