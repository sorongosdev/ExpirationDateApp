package com.example.expirationdateapp.home

import android.annotation.SuppressLint
import android.app.PendingIntent.getActivity
import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.RectF
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.cardview.widget.CardView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import com.example.expirationdateapp.R
import com.google.common.io.Resources.getResource
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.model.Values.isDouble
import java.security.AccessController.getContext
import java.text.SimpleDateFormat
import kotlin.reflect.typeOf


class ListAdapter (var itemList: List<DocumentSnapshot>, listener: DeleteItemClick):
    RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutInflater.from(parent.context).inflate(R.layout.list_layout, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = itemList[position].getString("name")
        holder.useby.text = itemList[position].getString("useby")
        holder.register_date.text = itemList[position].getString("register")
        /**디데이 표시*/
        var dDay : Long? = itemList[position].getLong("dday")
        if(dDay!! > 0) holder.dDay.text = "D+" + dDay.toString()
        else if(dDay.toString() == "0")holder.dDay.text = "D-DAY"
        else holder.dDay.text = "D" + dDay.toString()
        setColor(
            holder.card,
            dDay.toDouble(),
            calGap(itemList[position].getString("useby")!!.toString()            // 만료일
                ,itemList[position].getString("register")!!.toString()
            ).toDouble(),
            holder.context
        )
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.search_name)
        val register_date: TextView = view.findViewById(R.id.home_register_date)
        val useby: TextView = view.findViewById(R.id.home_useby)
        val dDay: TextView = view.findViewById(R.id.dDay)
        val card: CardView = view.findViewById(R.id.home_card)
        val context: Context = view.context
    }

    fun setData(new : List<DocumentSnapshot>){
        itemList = new
        notifyDataSetChanged()
        Log.d(TAG,"setData List : ${new}")
    }

    /**{ (디데이 ) / (만료일-등록일) } 에 따른 카드뷰 배경색 변경*/
    fun setColor(card: CardView, dDay: Double, gap: Double, context: Context){
        val par:Double = dDay/gap
        Log.d(TAG,"dDay => $dDay / gap => $gap / dDay/gap => ${dDay/gap}")
        if(par <= 1.0 && par > 0.5) card.setCardBackgroundColor(context.getColor(R.color.main_sky))

        else if(par <= 0.5 && par > 0.3) card.setCardBackgroundColor(context.getColor(R.color.main_green))

        else if(par <= 0.3 && par > 0) card.setCardBackgroundColor(context.getColor(R.color.main_yellow))

        else if (par <= 0) card.setCardBackgroundColor(context.getColor(R.color.main_red))

    }


    /**Calculate days*/
    fun calGap(eDate: String, sDate: String?) : Long{
        val dateFormat = SimpleDateFormat("yyyyMMdd")
        val sDateTime = dateFormat.parse(sDate).time
        val eDateTime = dateFormat.parse(eDate).time
        return ((sDateTime-eDateTime) / (24 * 60 * 60 * 1000))
    }
}