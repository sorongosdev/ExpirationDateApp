package com.example.expirationdateapp.basket

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.expirationdateapp.R
import com.example.expirationdateapp.databinding.BasketListLayoutBinding
import com.example.expirationdateapp.home.ListLayout
import com.google.firebase.firestore.DocumentSnapshot

class BasketListAdapter(private val context: Context?, private var basketList: MutableList<BasketListLayout>) : BaseAdapter(){

    override fun getCount(): Int = basketList.size

    override fun getItem(position: Int): BasketListLayout = basketList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding = BasketListLayoutBinding.inflate(LayoutInflater.from(context))

        val show = basketList[position]
//        binding.mlImgTitle.setImageResource(resourceId)
//        binding.mlTxtTitle.text = show.title
//        binding.mlTxtSubtitle.text = show.subTitle
        binding.itemName.text = show.itemName

        return binding.root

    }

    fun setData(new : MutableList<BasketListLayout>){
        basketList = new
        notifyDataSetChanged()
    }
}