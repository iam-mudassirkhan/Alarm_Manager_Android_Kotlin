package com.example.alarmmanager

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.room.Room
import com.example.alarmmanager.RoomDB.AlarmDatabase
import com.example.alarmmanager.RoomDB.AlarmTable
import com.example.alarmmanager.databinding.ActivityAddAlarmBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddAlarm_Activity : AppCompatActivity() {

    private var binding: ActivityAddAlarmBinding? = null
    public lateinit var alarmManager: AlarmManager
 private   lateinit var  pendingIntent: PendingIntent

// private lateinit var calendar: Calendar

//  public val  alaramClass = AlarmManagerClass(this)


// private lateinit var alaramClass: AlarmManagerClass

    private val alarmDB : AlarmDatabase by lazy {
        Room.databaseBuilder(this, AlarmDatabase::class.java, "alarmDB")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
    private lateinit var alarmTable: AlarmTable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAlarmBinding.inflate(layoutInflater)
        setContentView(binding?.root)

//        calendar = Calendar.getInstance()
//        alaramClass = AlarmManagerClass(this)
//     val   calendar = alaramClass.calendar
//        createNotificationChannel()

        val alarmClass = AlarmManagerUtils(this)

        alarmClass.createNotificationChannel()
        val   calendar = alarmClass.calendar

        binding?.setAlarmBtn?.setOnClickListener(View.OnClickListener {

            binding?.alarmTimePicker?.let { it1 ->
                calendar.set(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH),
                    it1.hour,
                    it1.minute,
                    0
                )
                val timeInMiliSeconds = calendar.timeInMillis



                alarmTable = AlarmTable(0,binding?.alarmName?.text.toString(),
                    SimpleDateFormat("hh:mm a", Locale.getDefault()).format(calendar.time))
                alarmDB.dao().inserAlarm(alarmTable)
                alarmClass.setAlarm(timeInMiliSeconds)
                alarmClass.remainingTime(timeInMiliSeconds)

                finish()
//                Toast.makeText(this, "Alarm Save", Toast.LENGTH_SHORT).show()


//                binding?.alarmTimeView?.text = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(calendar.time)
            }
        })
    }
}

