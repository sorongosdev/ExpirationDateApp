package com.example.expirationdateapp.home

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expirationdateapp.MyFirebaseMessagingService
import com.example.expirationdateapp.R
import com.example.expirationdateapp.databinding.FragmentHomeBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class HomeFragment : Fragment(), DeleteItemClick{
//class Home : Fragment(){
    private lateinit var binding: FragmentHomeBinding
    lateinit var model: MainViewModel
    private val listener = this

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home,container,false)
        binding.lifecycleOwner =viewLifecycleOwner

        /**recycer view and adapter*/
        binding.rvList.apply{
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = ListAdapter(emptyList(),listener)
        }

        /**viewmodel*/
        model = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        /**리스트뷰 업데이트*/
        model.liveItemListData.observe(viewLifecycleOwner){
            (binding.rvList.adapter as ListAdapter).setData(it)
        }

        /**아이템 추가하는 화면으로 전환*/
        binding.btnAdd.setOnClickListener{
            it.findNavController().navigate(R.id.action_home_to_item_add) // `view->` 를 대채하는 `it`
            //화면 종료
        }

        /**오늘 날짜에 따라 상단 텍스트 변화*/
        model.liveTodayData.observe(viewLifecycleOwner) {
            binding.tvToken.text = it
        }

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun deleteItemCall(ItemName: String){
        model.deleteItem(ItemName) // name만 넘겨줘야함
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun takeItemCall(ItemName: String){
        model.takeItem(ItemName) // name만 넘겨줘야함
    }
}