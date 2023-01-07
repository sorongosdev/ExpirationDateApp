package com.example.expirationdateapp.home

import androidx.recyclerview.widget.RecyclerView

interface DeleteItemClick {
//    fun deleteItemCall(ItemName: String)
//    fun takeItemCall(ItemName: String)
    fun onItemMove(from_position: Int, to_position: Int): Boolean
    fun onItemSwipe(position: Int)
    fun onLeftClick(position: Int, viewHolder: RecyclerView.ViewHolder?) // 해당 포지션의 itemname을 출력하게 하면 되겠다
    fun onRightClick(position: Int, viewHolder: RecyclerView.ViewHolder?)
}