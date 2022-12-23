package com.example.expirationdateapp.home
import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.datatransport.runtime.util.PriorityMapping.toInt
import com.google.android.gms.tasks.OnCompleteListener
import com.google.common.primitives.UnsignedBytes.toInt
import com.google.firebase.firestore.*
import java.lang.reflect.Field
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class MainViewModel: ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    lateinit var listenerBasic: ListenerRegistration
    val liveItemListData = MutableLiveData<List<DocumentSnapshot>>()
    val liveBasketListData = MutableLiveData<List<DocumentSnapshot>>()
    val player = db.collection("player")
    lateinit var dataStr :String

    /**오늘 날짜를 불러오는 부분*/
    var liveTodayData = MutableLiveData<String>()

    init {
        getList()
        getTime()
    }

    /**snapshot read*/
    fun getList() {
        listenerBasic = player.orderBy("name")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.d(TAG, "User VM not listening")
                }
                if (snapshot != null) {
                    for (doc in snapshot) {                        
                        liveItemListData.value = snapshot.documents
                        /**장바구니 리스트 업데이트*/
                        if (doc.data["take"]==true) liveBasketListData.value = snapshot.documents
                    }
                }
            }
    }

    fun addItem(itemName: String, itemList: ListLayout) {
        player.document(itemName).set(itemList) // 실제 파이어베이스에 입력
            .addOnFailureListener { exception ->
                Log.d("MainViewModel", "Error set documents: $exception")    // 실패할 경우
            }
    }

    fun deleteItem(itemName: String){
        player.document(itemName).delete() // 실제 파이어베이스에 입력
            .addOnFailureListener { exception ->
                Log.d("MainViewModel", "Error delete documents: $exception")    // 실패할 경우
            }
    }

    /**디데이를 업데이트 해주는 함수*/
    fun updateItem(){
        player.get()
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        Log.d(TAG, document.id + " => " + document.data)
                        document.reference.update("dday",calDday(document.data["useby"].toString(),liveTodayData.value))
                        Log.d(TAG,"dday updated")
                    }
                } else {
                    Log.w(TAG, "Error getting documents.", task.exception)
                }
            }
    }

    /**장바구니에 들어가는 아이템 take필드를 true로 셋*/
    fun takeItem(itemName: String){
        player.document(itemName).update("take",true)
    }

    fun Date2String(date: Int): String{
        if (date < 10) return "0" + date.toString()

        else return date.toString()
    }

    fun getTime(){
        liveTodayData.value = SimpleDateFormat("yyyyMMdd").format(Date(System.currentTimeMillis()))
    }

    fun calDday(eDate: String, sDate: String?) : Long{
        val dateFormat = SimpleDateFormat("yyyyMMdd")
        val sDateTime = dateFormat.parse(sDate).time
        val eDateTime = dateFormat.parse(eDate).time
        return ((eDateTime-sDateTime) / (24 * 60 * 60 * 1000))
    }
}
