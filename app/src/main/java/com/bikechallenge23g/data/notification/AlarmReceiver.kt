package com.bikechallenge23g.data.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.bikechallenge23g.data.model.Bike
import java.util.Calendar


const val REQUEST_CODE = 10
const val BIKE_MODEL = "bike_model"
const val SERVICE_IN = "service_in"

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val bikeModel = intent.getStringExtra(BIKE_MODEL)
        val serviceIn = intent.getStringExtra(SERVICE_IN)
        if (bikeModel != null && serviceIn != null) {
            NotificationHelper.sendNotification(context, bikeModel, serviceIn)
        }
    }
}


class AlarmSetter(context: Context, defaultBike: Bike) {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    private val intent = Intent(context, AlarmReceiver::class.java).apply {
        putExtra(BIKE_MODEL, defaultBike.model)
        putExtra(
            SERVICE_IN,
            "Service in: " +
                    "${(defaultBike.serviceIn ?: 0) - (defaultBike.distance ?: 0.0).toInt()}" +
                    "${defaultBike.distanceUnit?.name}"
        )
    }

    private val pendingIntent: PendingIntent =
        PendingIntent.getBroadcast(context, REQUEST_CODE, intent, PendingIntent.FLAG_IMMUTABLE)

    private val calendar: Calendar = Calendar.getInstance().apply {
        timeInMillis = System.currentTimeMillis()
        set(Calendar.HOUR_OF_DAY, 9)
    }

    fun scheduleAlarm() {
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_FIFTEEN_MINUTES,
            pendingIntent
        )
    }


    fun cancelAlarm() {
        alarmManager.cancel(pendingIntent)
    }
}