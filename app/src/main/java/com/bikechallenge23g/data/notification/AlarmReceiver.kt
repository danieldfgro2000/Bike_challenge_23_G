package com.bikechallenge23g.data.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.bikechallenge23g.data.model.Bike
import java.util.Calendar


const val REQUEST_CODE = 10
const val BIKE_MODEL = "bike_model"
const val SERVICE_IN = "service_in"

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val bikeModel = intent.getStringExtra(BIKE_MODEL)
        val serviceIn = intent.getStringExtra(SERVICE_IN)
        Log.w("Alarm Receiver", "bike model = $bikeModel")
        Log.w("Alarm Receiver", "service in = $serviceIn")
        if (bikeModel != null && serviceIn != null) {
            sendNotification(context, bikeModel, serviceIn)
        }
    }
}


class AlarmSetter(context: Context, defaultBike: Bike? = null) {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    private val intent = Intent(context, AlarmReceiver::class.java).apply {
        putExtra(BIKE_MODEL, defaultBike?.model)
        putExtra(
            SERVICE_IN,
            "Service in: ${defaultBike?.serviceIn}${defaultBike?.distanceUnit?.name}"
        )
    }

    private val pendingIntent: PendingIntent =
        PendingIntent.getBroadcast(context, REQUEST_CODE, intent, PendingIntent.FLAG_IMMUTABLE)

    private val calendar: Calendar = Calendar.getInstance().apply {
        timeInMillis = System.currentTimeMillis()
        set(Calendar.HOUR_OF_DAY, 9)
    }

    fun scheduleAlarm() {
        Log.d("Alarm setter", "schedule alarm")
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_FIFTEEN_MINUTES,
            pendingIntent
        )
    }


    fun cancelAlarm() {
        Log.d("Alarm setter", "cancel alarm")
        alarmManager.cancel(pendingIntent)
    }
}