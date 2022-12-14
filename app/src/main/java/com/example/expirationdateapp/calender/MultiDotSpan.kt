package com.example.expirationdateapp.calender

import android.R.attr
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF.length
import android.opengl.Matrix.length
import android.text.style.LineBackgroundSpan
import com.prolificinteractive.materialcalendarview.spans.DotSpan.DEFAULT_RADIUS


//class CustmMultipleDotSpan(color: Int, radius: Float) : LineBackgroundSpan {
class MultiDotSpan(private val radius: Float = 3f, private val colors: List<Int>) : LineBackgroundSpan {
    override fun drawBackground(
        canvas: Canvas, paint: Paint,
        left: Int, right: Int, top: Int, baseline: Int, bottom: Int,
        charSequence: CharSequence,
        start: Int, end: Int, lineNum: Int
    ) {

        var position = (left + right) / 2 - (colors.size - 1) * radius

        if (colors.size > 1) {
            position - 2
        }

        colors.forEach { color ->
            val oldColor = paint.color
            if (color != 0) {
                paint.color = color
            }
            canvas.drawCircle(position, bottom + radius, radius, paint)
            paint.color = oldColor

            position += radius * 2 + 2
        }
    }
}
