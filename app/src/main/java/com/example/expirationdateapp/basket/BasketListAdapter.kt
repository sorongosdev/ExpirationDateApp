package com.example.expirationdateapp.basket

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

class BasketListAdapter(var basketList: List<BasketListLayout>):

    RecyclerView.Adapter<BasketListAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding = LayoutInflater.from(parent.context).inflate(R.layout.basket_list_layout, parent, false)
            return ViewHolder(binding)
        }

        override fun getItemCount(): Int {
            Log.d("basket_getItemCount","${basketList.size}")
            return basketList.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.name.text = basketList[position].itemName
        }

        class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            val name: TextView = itemView.findViewById(R.id.basket_item_name)
        }

        fun setData(new : List<BasketListLayout>){
//            basketList = basketList+new
            basketList = new
            notifyDataSetChanged()
            Log.d("basket_getItemCount","$new")
        }
    }