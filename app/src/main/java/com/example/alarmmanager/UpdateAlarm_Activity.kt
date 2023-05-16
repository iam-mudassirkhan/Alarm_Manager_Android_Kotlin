package com.example.alarmmanager

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.alarmmanager.RoomDB.AlarmDatabase
import com.example.alarmmanager.RoomDB.AlarmTable
import com.example.alarmmanager.databinding.ActivityUpdateAlarmBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

private var binding: ActivityUpdateAlarmBinding? = null

class UpdateAlarm_Activity : AppCompatActivity() {

    private val alarmDB: AlarmDatabase by lazy {
        Room.databaseBuilder(this, AlarmDatabase::class.java, "alarmDB")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
    private lateinit var alarmTable: AlarmTable
    private var alarmId = 0
    private var defaultAlarmName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateAlarmBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // Below is The Class in Which I have set the Different Alarm Functions Function
        val alarmClass = AlarmManagerUtils(this)
        alarmClass.createNotificationChannel()

        intent.extras?.let {
            alarmId = it.getInt(BUNDLE_NOTE_ID)
        }
//        val calendar = Calendar.getInstance()
        val calendar = alarmClass.calendar


        binding?.apply {
            defaultAlarmName = alarmDB.dao().getAlarm(alarmId).alarmName

            alarmName.setText(defaultAlarmName)

            UpdateAlarmBtn.setOnClickListener(View.OnClickListener {

                binding?.updateAlarmTimePicker?.let { it2 ->

                    calendar.set(
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH),
                        it2.hour,
                        it2.minute,
                        0
                    )

                    // I Use this to get Time in Meili Seconds
                    val timeInMiliSeconds = calendar.timeInMillis

                    val updatedAlarmName = alarmName.text.toString()

                    alarmTable = AlarmTable(
                        alarmId, updatedAlarmName,
                        // The Below Line Just Saving Time in Readable Format
                        SimpleDateFormat("hh:mm a", Locale.getDefault()).format(calendar.time)
                    )
                    alarmDB.dao().updateAlarm(alarmTable)
//                    alaramClass.setAlarm()
                    alarmClass.updateAlarm(timeInMiliSeconds)
                    alarmClass.remainingTime(timeInMiliSeconds)

                    finish()
//                    Toast.makeText(this@UpdateAlarm_Activity, "Alarm Updated", Toast.LENGTH_SHORT).show()


                    //                binding?.alarmTimeView?.text = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(calendar.time)
                }
            })
        }


    }
}