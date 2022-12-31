package com.example.expirationdateapp.home

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.expirationdateapp.R
import com.example.expirationdateapp.databinding.FragmentItemAddBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class ItemAdd : Fragment() {
    private lateinit var binding: FragmentItemAddBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_item_add,container,false)
        binding.lifecycleOwner =viewLifecycleOwner

        val model = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        var dayText = binding.itemExpire
        var datePicker = binding.datePicker
        val pYear = datePicker.year
        val pMonth = datePicker.month+1
        val pDay = datePicker.dayOfMonth

        val dateFormat = SimpleDateFormat("yyyyMMdd")
        val sDate = "${pYear}${model.Date2String(pMonth)}${model.Date2String(pDay)}"
        val sDateTime = dateFormat.parse(sDate).time
        var eDateTime = sDateTime

        // 소비기한 날짜 표시, 디데이 업데이트
        dayText.text = sDate

        datePicker.setOnDateChangedListener{ datePicker, year, month, dayOfMonth ->
            var eDate = "${year}${model.Date2String(month+1)}${model.Date2String(dayOfMonth)}"
            eDateTime = dateFormat.parse(eDate).time
            dayText.text = eDate
        }

        // 등록 버튼
        binding.itemRegister.setOnClickListener {
            val aItemName = binding.itemName.text.toString()
            val aItemExpire = dayText.text.toString()
            val aItemDday = (sDateTime-eDateTime) / (24 * 60 * 60 * 1000)

            model.addItem(aItemName,ListLayout(aItemName,aItemExpire,aItemDday))
            
            // 등록버튼 눌렀을 때
            val navController = findNavController()
            navController.popBackStack()
        }

        return binding.root
    }

    // 10보다 작은 수는 앞에 0을 붙여주는 함수
//    fun Date2String(date: Int): String{
//        if (date < 10) return "0" + date.toString()
//
//        else return date.toString()
//    }
}