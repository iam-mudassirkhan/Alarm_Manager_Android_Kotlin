package com.example.alarmmanager

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import java.util.Calendar

class AlarmManagerUtils(private val context: Context) {

    private val alarmManager: AlarmManager =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    val calendar = Calendar.getInstance()

    fun setAlarm(timeInMillis: Long) {
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            timeInMillis,
            pendingIntent
        )

//        showToast("Alarm set for ${getTimeString(timeInMillis)}")
    }

    fun updateAlarm(timeInMillis: Long) {
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            timeInMillis,
            pendingIntent
        )

//        showToast("Alarm updated for ${getTimeString(timeInMillis)}")
    }

    fun deleteAlarm() {
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        alarmManager.cancel(pendingIntent)
        showToast("Alarm deleted")
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun getTimeString(timeInMillis: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeInMillis
        return calendar.time.toString()
    }

    fun remainingTime(alarmTimeMillis: Long) {

        // Get the current time
        val currentTimeMillis = System.currentTimeMillis()

// Calculate the time difference between the current time and the alarm time
        val timeDifferenceMillis = alarmTimeMillis - currentTimeMillis

// Convert the time difference from milliseconds to seconds, minutes, and hours
        val seconds = (timeDifferenceMillis / 1000 % 60).toInt()
        val minutes = (timeDifferenceMillis / (1000 * 60) % 60).toInt()
        val hours = (timeDifferenceMillis / (1000 * 60 * 60) % 24).toInt()

// Display the remaining time in a Toast message
//        val toastMessage =
//            "Remaining time: $hours hours, $minutes minutes, /*$seconds seconds*/from now"
        val toastMessage = "Alarm set for $hours hours and $minutes minutes from now"
        Toast.makeText(context, toastMessage, Toast.LENGTH_LONG).show()

    }

    fun createNotificationChannel() {
        if (Build. VERSION. SDK_INT >= Build.VERSION_CODES.O) {
            val name : CharSequence = "alarmByMudassirKhan"
            val description = "Channel For Alarm Manager"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(  "My_Notification_Id" ,name, importance)
            channel.description = description
            val notificationManager=this.context.applicationContext.getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(channel)

        }
    }

}
