package com.example.expirationdateapp.basket

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.expirationdateapp.R
import com.google.firebase.firestore.DocumentSnapshot
import org.w3c.dom.Text

class ListAdapter(var basketList: List<DocumentSnapshot>) :
    RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutInflater.from(parent.context).inflate(R.layout.basket_list_layout, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return basketList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemName.text = basketList[position].getString("name")
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val itemName: TextView = itemView.findViewById(R.id.item_name)
    }
    fun setData(new : List<DocumentSnapshot>){
        basketList = new
        notifyDataSetChanged()
    }
}