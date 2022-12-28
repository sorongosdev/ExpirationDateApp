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

class FoodListAdapter(var FoodList: List<CalFoodBox>):

    RecyclerView.Adapter<FoodListAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding = LayoutInflater.from(parent.context).inflate(R.layout.cal_food_box, parent, false)
            return ViewHolder(binding)
        }

        override fun getItemCount(): Int {
            Log.d("food_getItemCount","${FoodList.size}")
            return FoodList.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.foodName.text = FoodList[position].FoodName
        }

        class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            val foodName: TextView = itemView.findViewById(R.id.cal_food_name)
        }


    }