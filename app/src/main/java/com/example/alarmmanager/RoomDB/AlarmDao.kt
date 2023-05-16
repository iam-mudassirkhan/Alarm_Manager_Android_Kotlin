package com.example.alarmmanager.RoomDB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface AlarmDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserAlarm(alarmTable: AlarmTable)

    @Update
    fun updateAlarm(alarmTable: AlarmTable)

    @Delete
    fun deleteAlarm(alarmTable: AlarmTable)

    @Query("SELECT * FROM alarm_table ORDER BY alarmId DESC")
    fun getAllAlarm() : MutableList<AlarmTable>

    @Query("SELECT * FROM alarm_table WHERE alarmId LIKE :id")
    fun getAlarm(id : Int) : AlarmTable

}