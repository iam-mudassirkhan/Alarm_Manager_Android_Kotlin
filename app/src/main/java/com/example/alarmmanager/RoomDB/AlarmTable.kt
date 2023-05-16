package com.example.alarmmanager.RoomDB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarm_table")
data class AlarmTable(
    @PrimaryKey(autoGenerate = true)
    val alarmId: Int,

    val alarmName: String,

    val alarmTime: String
)
