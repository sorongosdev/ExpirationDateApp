package com.example.expirationdateapp.calender

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expirationdateapp.R
import com.example.expirationdateapp.databinding.CalListLayoutBinding

class CalListLayout(val dayText: String)
//    : Fragment(){
////class CalListLayout(val dayText: String) : Fragment(){
//    private lateinit var binding: CalListLayoutBinding
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
//                              savedInstanceState: Bundle?): View? {
//        binding = DataBindingUtil.inflate(inflater, R.layout.cal_list_layout, container, false)
//        binding.lifecycleOwner = viewLifecycleOwner
//
//        Log.d("CalListLayout","onCreateView") // 오류
//
//        /**리사이클러뷰와 어댑터*/
//        binding.calFoodRv.apply{
//            Log.d("calFoodRV","apply") // 오류
//            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//            adapter = FoodListAdapter(listOf(CalFoodBox("ss"),CalFoodBox("mm")))
//        }
//
//        return binding.root
//    }
//}