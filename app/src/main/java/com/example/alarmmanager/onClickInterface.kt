package com.example.alarmmanager

import com.example.alarmmanager.RoomDB.AlarmTable

interface onClickInterface {
//    fun onNoteClick(position: Int, data : NoteEntity)

    fun onAlarmLongClick(position: Int, data : AlarmTable)
}