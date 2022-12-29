package com.example.expirationdateapp.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        holder.name.text = itemList[position].getString("name")
        holder.useby.text = itemList[position].getString("useby")
        holder.dDay.text = itemList[position].getLong("dday").toString()
        holder.btnDelete.setOnClickListener {
            mCallback.deleteItemCall(holder.name.text as String)
        }
        holder.btnTake.setOnClickListener {
            mCallback.takeItemCall(holder.name.text as String)
        }
    }
    private val mCallback = listener

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.search_name)
        val useby: TextView = itemView.findViewById(R.id.search_useby)
        val dDay: TextView = itemView.findViewById(R.id.dDay)
        val btnDelete: Button = itemView.findViewById(R.id.btn_delete_item)
        val btnTake: Button = itemView.findViewById(R.id.btn_take_item)
    }

    fun setData(new : List<DocumentSnapshot>){
        itemList = new
        notifyDataSetChanged()
    }
}