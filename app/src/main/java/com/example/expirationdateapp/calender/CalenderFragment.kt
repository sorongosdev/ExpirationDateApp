package com.example.expirationdateapp.calender

import android.graphics.Color
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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expirationdateapp.R
import com.example.expirationdateapp.basket.BasketListAdapter
import com.example.expirationdateapp.databinding.FragmentCalenderBinding
import com.example.expirationdateapp.home.ListAdapter
import com.example.expirationdateapp.home.MainViewModel
import com.google.firebase.database.collection.LLRBNode
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import java.text.SimpleDateFormat
import org.threeten.bp.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class CalenderFragment : Fragment() {
    private lateinit var binding: FragmentCalenderBinding
    private lateinit var selectedDate : LocalDate
    private lateinit var dayList: MutableList<CalListLayout>
    private lateinit var model: MainViewModel
    private lateinit var foodList: MutableList<CalFoodBox>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calender,container,false)
        binding.lifecycleOwner =viewLifecycleOwner

        /**뷰모델 가져오기*/
        model = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        /**오늘 날짜 가져오기*/
        selectedDate = LocalDate.now()

//        /**당일월과 같은 음식 가져오기*/
//        model.getMonthFood( // monthFoodList<CalFoodBox>
//            selectedDate.format(DateTimeFormatter.ofPattern("yyyyMM"))
//        )

        var calendar : MaterialCalendarView = binding.calendarView
        var dates = ArrayList<CalendarDay>()
//        var date = Calendar.getInstance()
//        date.set(2023,1,12)

        // 달력에 표시할 날짜 dayList에 넣기 - 오늘날짜
        dates.add(CalendarDay.today())

        binding.calendarView.addDecorator(EventDecorator(Collections.singleton(CalendarDay.today())))

        //글자 표시를 하루하루


        // 글자표시는 하루하루
//        var date_for_text = ArrayList<CalendarDay>()
//        date_for_text.add(day)
//        calendar.addDecorator(TextDecorator)

        return binding.root
    }

}