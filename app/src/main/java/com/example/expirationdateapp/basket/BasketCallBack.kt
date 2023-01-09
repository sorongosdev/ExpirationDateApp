package com.example.expirationdateapp.basket

import androidx.recyclerview.widget.RecyclerView

interface BasketCallBack {
    fun takeOutItemCall(ItemName: String)
    fun onItemMove(from_position: Int, to_position: Int): Boolean
    fun onItemSwipe(position: Int)
    fun onRightClick(position: Int, viewHolder: RecyclerView.ViewHolder?)
}