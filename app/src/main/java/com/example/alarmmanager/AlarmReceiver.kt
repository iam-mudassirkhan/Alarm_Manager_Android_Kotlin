package com.example.alarmmanager

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.provider.Settings
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class AlarmReceiver : BroadcastReceiver() {
    lateinit var mp : MediaPlayer
    override fun onReceive(context: Context?, intent: Intent?) {

        val i = Intent(context, MainActivity::class.java)
        intent!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(context!!, "My_Notification_Id")
            .setSmallIcon(R.drawable.baseline_alarm_add_24)
            .setContentTitle("Alarm Triggered")
            .setContentText("This Alarm was created by Mudassir")
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(123,builder.build())

        mp = MediaPlayer.create(context, Settings.System.DEFAULT_ALARM_ALERT_URI)
        mp.start()

        try {
            Thread.sleep(10000)
        }
        catch (e : Exception){
            e.printStackTrace()
        }
        mp.stop()
    }
}