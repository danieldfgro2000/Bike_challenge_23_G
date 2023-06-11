package com.bikechallenge23g.presentation.ui.composables


import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.bikechallenge23g.R
import com.bikechallenge23g.presentation.util.intTimeToHHmm
import com.bikechallenge23g.presentation.util.longEpochToddMMyyyy
import com.marosseleng.compose.material3.datetimepickers.date.ui.dialog.DatePickerDialog
import com.marosseleng.compose.material3.datetimepickers.time.ui.dialog.TimePickerDialog
import java.time.LocalDate
import java.time.LocalTime


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun customDateTimePicker(
    modifier: Modifier = Modifier,
    selectedTime: Int? = null,
    selectedDate: Long? = null,
    isDatePicker: Boolean = false,
    onTimeSelected: (Int) -> Unit = {},
    onDateSelected: (Long) -> Unit = {}
) {

    var showTimePickerDialog by remember { mutableStateOf(false) }
    var showDatePickerDialog by remember { mutableStateOf(false) }
    var date: LocalDate by remember { mutableStateOf(LocalDate.now()) }
    var calculatedTime by remember { mutableIntStateOf(selectedTime ?: 0) }

    val displayedDateTime = if (isDatePicker) {
        longEpochToddMMyyyy(selectedDate ?: LocalDate.now().toEpochDay())
    } else intTimeToHHmm(selectedTime ?: 0)

    val selectedTimeToLocalTime = if (selectedTime != null) {
        LocalTime.of(selectedTime / 60, selectedTime % 60)
    } else {
        LocalTime.of(0, 0)
    }

    val error: String? = if (!isDatePicker && calculatedTime == 0) {
        stringResource(id = R.string.required_field)
    } else null

    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.background
    ) {
        CustomCardWithDropDown(
            text = displayedDateTime,
            error = error
        ) {
            if (isDatePicker) showDatePickerDialog = true
            else showTimePickerDialog = true
        }
        if (showTimePickerDialog) {
            TimePickerDialog(
                initialTime = selectedTimeToLocalTime,
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
                    onDateSelected(date.toEpochDay())
                }
            )
        }
    }
}

