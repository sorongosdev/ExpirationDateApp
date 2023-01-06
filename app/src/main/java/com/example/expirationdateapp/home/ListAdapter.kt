package com.example.expirationdateapp.home

import android.content.ContentValues.TAG
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.expirationdateapp.R
import com.google.firebase.firestore.DocumentSnapshot


class ListAdapter (var itemList: List<DocumentSnapshot>, listener: DeleteItemClick):
    RecyclerView.Adapter<ListAdapter.ViewHolder>(), ItemTouchHelperListener {

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
        /**디데이*/
        var dDay : Long? = itemList[position].getLong("dday")
        if(dDay!! > 0){
            holder.dDay.text = "D+" + dDay.toString()
        }
        else if(dDay.toString() == "0"){
            holder.dDay.text = "D-DAY"
        }
        else{
            holder.dDay.text = "D" + dDay.toString()
        }
        /**삭제버튼*/
        holder.btnDelete.setOnClickListener {
            mCallback.deleteItemCall(holder.name.text as String)
        }
        /**장바구니 버튼*/
        holder.btnTake.setOnClickListener {
            mCallback.takeItemCall(holder.name.text as String)
        }
    }
    private val mCallback = listener

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.search_name)
        val useby: TextView = itemView.findViewById(R.id.search_useby)
        val dDay: TextView = itemView.findViewById(R.id.dDay)
        val btnDelete: Button = itemView.findViewById(R.id.btn_delete_item)
        val btnTake: Button = itemView.findViewById(R.id.btn_take_item)
    }

    fun setData(new : List<DocumentSnapshot>){
        itemList = new
        notifyDataSetChanged()
        Log.d(TAG,"setData List : ${new}")
    }

    override fun onItemMove(from_position: Int, to_position: Int): Boolean {

        return true
    }

    override fun onItemSwipe(position: Int) {

    }


    //왼쪽 버튼 누르면 수정할 다이얼로그 띄우기
    override fun onLeftClick(position: Int, viewHolder: RecyclerView.ViewHolder?) {

    }


    //오른쪽 버튼 누르면 아이템 삭제
    override fun onRightClick(position: Int, viewHolder: RecyclerView.ViewHolder?) {

    }
}