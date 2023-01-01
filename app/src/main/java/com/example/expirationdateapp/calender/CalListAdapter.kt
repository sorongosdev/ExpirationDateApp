package com.example.expirationdateapp.calender

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.expirationdateapp.R
import com.example.expirationdateapp.databinding.BasketListLayoutBinding
import com.example.expirationdateapp.home.ListLayout
import com.google.firebase.firestore.DocumentSnapshot

class CalListAdapter(var dayList: List<CalListLayout>,var monthFoodList: List<CalFoodBox>):

    RecyclerView.Adapter<CalListAdapter.ViewHolder>() {

        lateinit var dayFoodList: MutableList<CalFoodBox>

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding = LayoutInflater.from(parent.context).inflate(R.layout.cal_list_layout, parent, false)
            return ViewHolder(binding)
        }

        override fun getItemCount(): Int {
            return dayList.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.dayText.text = dayList[position].dayText
            if (holder.dayText.text.length != 0) {
                holder.foodRv.apply {
                    Log.d("CalListAdapter_onBindViewHolder","holder.foodRv.apply")
                    layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    getDayFood(Date2String((holder.dayText.text as String).toInt()))
                    adapter = FoodListAdapter(dayFoodList)
                }
            }
        }

        class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            val dayText: TextView = itemView.findViewById(R.id.dayText)
            val foodRv : RecyclerView = itemView.findViewById(R.id.cal_food_rv)
        }

        /**monthFoodList에서 dayFoodList를 생성*/
        fun getDayFood(dayText: String){
            dayFoodList = mutableListOf<CalFoodBox>()
            for(i in monthFoodList){
                if(i.FoodDate.substring(6,8) == dayText){
                    dayFoodList.add(CalFoodBox(i.FoodName,i.FoodName))
                }
            }
        }
        fun Date2String(date: Int): String{
            if (date < 10) return "0" + date.toString()

            else return date.toString()
        }
    }