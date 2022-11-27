package com.example.expirationdateapp.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.expirationdateapp.R
import com.google.firebase.firestore.DocumentSnapshot

//class ListAdapter (val itemList: ArrayList<ListLayout>): RecyclerView.Adapter<ListAdapter.ViewHolder>() {
class ListAdapter (
    val inflater: LayoutInflater,
    var itemList: List<DocumentSnapshot>
    ):
    RecyclerView.Adapter<ListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_layout, parent, false)
        val view = inflater.inflate(R.layout.list_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.name.text = itemList[position].name
        holder.name.text = itemList[position].getString("name")
//        holder.useby.text = itemList[position].useby
        holder.useby.text = itemList[position].getString("useby")
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.search_name)
        val useby: TextView = itemView.findViewById(R.id.search_useby)
    }

    fun setData(new : List<DocumentSnapshot>){
        itemList = new
    }

}