package com.example.expirationdateapp.calender

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.expirationdateapp.R
import com.example.expirationdateapp.databinding.BasketListLayoutBinding
import com.example.expirationdateapp.home.ListLayout
import com.google.firebase.firestore.DocumentSnapshot

class CalListAdapter(var dayList: List<CalListLayout>):

    RecyclerView.Adapter<CalListAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding = LayoutInflater.from(parent.context).inflate(R.layout.cal_list_layout, parent, false)
            return ViewHolder(binding)
        }

        override fun getItemCount(): Int {
            Log.d("cal_getItemCount","${dayList.size}")
            return dayList.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.dayText.text = dayList[position].dayText
        }

        class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            val dayText: TextView = itemView.findViewById(R.id.dayText)
        }


    }