package com.example.expirationdateapp.home

import android.content.ContentValues.TAG
import android.graphics.RectF
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.expirationdateapp.R
import com.google.firebase.firestore.DocumentSnapshot


class ListAdapter (var itemList: List<DocumentSnapshot>, listener: DeleteItemClick):
    RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutInflater.from(parent.context).inflate(R.layout.list_layout, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d(TAG,"position : $position")
        holder.name.text = itemList[position].getString("name")
        holder.useby.text = itemList[position].getString("useby")
        /***/
        holder.register_date.text = itemList[position].getString("register")
        /**디데이*/
        var dDay : Long? = itemList[position].getLong("dday")
        if(dDay!! > 0){
            holder.dDay.text = "D+" + dDay.toString()
        }
        else if(dDay.toString() == "0"){
            holder.dDay.text = "D-DAY"
        }
        else{
            holder.dDay.text = "D" + dDay.toString()
        }
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.search_name)
        val register_date: TextView = view.findViewById(R.id.home_register_date)
        val useby: TextView = view.findViewById(R.id.home_useby)
        val dDay: TextView = view.findViewById(R.id.dDay)
    }

    fun setData(new : List<DocumentSnapshot>){
        itemList = new
        notifyDataSetChanged()
        Log.d(TAG,"setData List : ${new}")
    }
}