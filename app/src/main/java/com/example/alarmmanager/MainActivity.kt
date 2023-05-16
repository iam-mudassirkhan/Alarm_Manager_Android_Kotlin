package com.example.alarmmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.alarmmanager.RoomDB.AlarmDatabase
import com.example.alarmmanager.RoomDB.AlarmTable
import com.example.alarmmanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), onClickInterface {
    lateinit var newArrayList: MutableList<AlarmTable>

    private val alarmDB : AlarmDatabase by lazy {
        Room.databaseBuilder(this, AlarmDatabase::class.java, "alarmDB")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    private val noteAdapter by lazy { AlarmAdapter( this) }

    private var binding : ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)



        binding?.btnAddAlarm?.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, AddAlarm_Activity::class.java))
        })
    }

    override fun onResume() {
        super.onResume()
        checkItem()
    }


    private fun checkItem(){
        binding?.apply {
            if(alarmDB.dao().getAllAlarm().isNotEmpty()){
                recyclerView.visibility= View.VISIBLE
                tvEmptyText.visibility=View.GONE
//                Toast.makeText(this@MainActivity, "Alarm Fetched", Toast.LENGTH_SHORT).show()
                noteAdapter.differ.submitList(alarmDB.dao().getAllAlarm())
                setupRecyclerView()
            }else{

                recyclerView.visibility=View.GONE
                tvEmptyText.visibility=View.VISIBLE
                Toast.makeText(this@MainActivity, "No Alarm Found", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupRecyclerView(){
        binding?.recyclerView?.apply {
            layoutManager= LinearLayoutManager(this@MainActivity)
            adapter=noteAdapter
        }

    }

    override fun onAlarmLongClick(position: Int, data: AlarmTable) {

        val mAlarmManagerUtils = AlarmManagerUtils(this)

        newArrayList = alarmDB.dao().getAllAlarm()

        val builder  = AlertDialog.Builder(this)
        with(builder){
            setTitle("Delete")
            setMessage("are you sure?")
            setPositiveButton("yes"){dialog, which ->

                alarmDB.dao().deleteAlarm(noteAdapter.differ.currentList[position])
//                alarmManagerClass.cancelAlarm()
                mAlarmManagerUtils.deleteAlarm()

                // I used the below line in the If Condition otherwise it throw an Exception that ArrayIndexOutOfBound
                if (newArrayList.size-1 >= 0){
                    newArrayList.removeAt(position)
                    noteAdapter.differ.submitList(newArrayList)
                    Toast.makeText(this@MainActivity, "${data.alarmName} Deleted", Toast.LENGTH_SHORT).show()
//                    checkItem()
                }
                else {
                    Toast.makeText(this@MainActivity, "index out of bound", Toast.LENGTH_SHORT).show()
                }
            }
            setNegativeButton("Edit"){dialog, which  ->
                val intent=Intent(this@MainActivity,UpdateAlarm_Activity::class.java)
                intent.putExtra(BUNDLE_NOTE_ID, data.alarmId)
                intent.putExtra("alarmName", data.alarmName)
                startActivity(intent)
////                Toast.makeText(this@MainActivity, "Not Deleted", Toast.LENGTH_SHORT).show()
            }
            setNeutralButton("Cancel"){dialog, which ->
                Toast.makeText(this@MainActivity, "dialog Disappear", Toast.LENGTH_SHORT).show()
            }
            show()
        }

    }
}