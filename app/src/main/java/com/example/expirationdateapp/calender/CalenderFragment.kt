package com.example.expirationdateapp.calender

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expirationdateapp.R
import com.example.expirationdateapp.databinding.FragmentCalenderBinding
import com.example.expirationdateapp.home.ListAdapter
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class CalenderFragment : Fragment() {
    private lateinit var binding: FragmentCalenderBinding
    private val dateFormat = SimpleDateFormat("MM월 yyyy")
    private lateinit var selectedDate : LocalDate
    private lateinit var dayList: MutableList<CalListLayout>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calender,container,false)
        binding.lifecycleOwner =viewLifecycleOwner

        /**오늘 날짜 가져오기*/
        selectedDate = LocalDate.now()

        /**오늘 날짜로 포맷에 맞게 텍스트 업데이트*/
        setMonthView()

        /**이전 달*/
        binding.calBtnPre.setOnClickListener{
            selectedDate = selectedDate.minusMonths(1)
            setMonthView()
        }
        /**다음 달*/
        binding.calBtnNext.setOnClickListener{
            selectedDate = selectedDate.plusMonths(1)
            setMonthView()
        }

        /**리사이클러뷰와 어댑터*/
        binding.calRv.apply{
            layoutManager = GridLayoutManager(context, 7, LinearLayoutManager.VERTICAL, false)
        }

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setMonthView(){
        binding.monthYearText.text = selectedDate.format(DateTimeFormatter.ofPattern("MM월 yyyy"))
        dayList = daysInMonthArray(selectedDate)
        binding.calRv.adapter = CalListAdapter(dayList)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun daysInMonthArray(date : LocalDate) : MutableList<CalListLayout>{
        dayList = mutableListOf<CalListLayout>()
        var yearMonth = YearMonth.from(date)

        /**해당월 마지막 날짜*/
        val lastDay = yearMonth.lengthOfMonth()
        /**해당월 첫 날짜*/
        val firstDay = selectedDate.withDayOfMonth(1)
        /**첫째 날 요일 가져오기*/
        val dayOfWeek : Int = firstDay.dayOfWeek.value

        for(i:Int in 1 until 42){
            if(i <= dayOfWeek || i > lastDay + dayOfWeek){
                dayList.add(CalListLayout(""))
            }
            else{
                dayList.add(CalListLayout((i-dayOfWeek).toString()))
            }
        }
        return dayList
    }
}