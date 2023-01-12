package com.example.expirationdateapp.calender

import android.text.style.LineBackgroundSpan
import com.example.expirationdateapp.R
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

//class EventDecorator(dates: Collection<CalendarDay>): DayViewDecorator {
class EventDecorator(
    private val colors: List<Int>,
    private var dates: List<CalendarDay> = emptyList()
): DayViewDecorator {

//    var dates: HashSet<CalendarDay> = HashSet(dates)

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return dates.contains(day)
    }

    override fun decorate(view: DayViewFacade?) {
//        view?.addSpan(DotSpan(5F, Color.parseColor("#1D872A")))
        view?.addSpan(MultiDotSpan(8f, colors))
    }
}