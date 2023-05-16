package com.example.alarmmanager.RoomDB

import androidx.room.Database
import androidx.room.RoomDatabase

@Database (entities = [AlarmTable::class], version = 1)
abstract class AlarmDatabase : RoomDatabase()  {
   abstract fun dao(): AlarmDao
}