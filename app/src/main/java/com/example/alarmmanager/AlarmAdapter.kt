package com.example.alarmmanager

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.alarmmanager.RoomDB.AlarmTable
import com.example.alarmmanager.databinding.AlarmItemDesignBinding

const val BUNDLE_NOTE_ID="bundle_note_id"

class AlarmAdapter( val interfaceClick: onClickInterface) : RecyclerView.Adapter<AlarmAdapter.ViewHolder>() {
    private lateinit var binding: AlarmItemDesignBinding
    private lateinit var context: Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        binding = AlarmItemDesignBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder()
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])

        holder.itemView.setOnLongClickListener {
            interfaceClick.onAlarmLongClick(holder.adapterPosition,differ.currentList[position])

            true
        }
    }

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {


        @SuppressLint("SetTextI18n")
        fun bind(item: AlarmTable) {
            //InitView
            binding.apply {
                //Set text
                getAlarmName.text = item.alarmName
                alarmTimeTV.text = item.alarmTime

                alarmCard.setOnClickListener {
                    val intent= Intent(context,UpdateAlarm_Activity::class.java)
                    intent.putExtra(BUNDLE_NOTE_ID, item.alarmId)
                    context.startActivity(intent)

                }

            }
        }
    }
    private val differCallback = object : DiffUtil.ItemCallback<AlarmTable>() {
        override fun areItemsTheSame(oldItem: AlarmTable, newItem: AlarmTable): Boolean {
            return oldItem.alarmId == newItem.alarmId
        }

        override fun areContentsTheSame(oldItem: AlarmTable, newItem: AlarmTable): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
}