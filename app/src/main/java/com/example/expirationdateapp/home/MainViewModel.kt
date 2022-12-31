package com.example.expirationdateapp.home
import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.expirationdateapp.basket.BasketListAdapter
import com.example.expirationdateapp.basket.BasketListLayout
import com.example.expirationdateapp.calender.CalFoodBox
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
class MainViewModel: ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    lateinit var listenerBasic: ListenerRegistration
    val liveItemListData = MutableLiveData<List<DocumentSnapshot>>()
    private var auth : FirebaseAuth = Firebase.auth

    val liveBasketListData = MutableLiveData<List<BasketListLayout>>()
    lateinit var BasketList : MutableList<BasketListLayout> // can append

//    val player = db.collection(auth.uid.toString())
    val player = db.collection("player")
    lateinit var dataStr :String

    /**오늘 날짜를 불러오는 부분*/
    var liveTodayData = MutableLiveData<String>()

    init {
        Log.d("mainViewModel","init")
        Log.d("mainViewModel","getList")
        getList()
        Log.d("mainViewModel","getTime")
        getTime()
        Log.d(TAG,"사용자 uid : ${auth.uid}")
    }

    /**snapshot read*/
    fun getList() {
        BasketList = mutableListOf<BasketListLayout>()
        Log.d("liveBasketListData","init, ${BasketList.size}")

        listenerBasic = player.orderBy("name")
            .addSnapshotListener { snapshot, e ->
                BasketList.clear()
                Log.d("liveBasketListData","clear, ${BasketList.size}")

                if (e != null) {
                    Log.d(TAG, "User VM not listening")
                }
                if (snapshot != null) {
                    for (doc in snapshot) {
                        liveItemListData.value = snapshot.documents
                        Log.d("liveBasketListData","${doc.id} => ${doc["useby"]}")

                        /**장바구니 리스트 업데이트*/
                        if (doc["take"] == true) {
                            BasketList.add(BasketListLayout(doc.id))
                            Log.d("liveBasketListData","updated, ${BasketList.size}")
                        }
                    }
                     // 리스트 개수 하나를 변화로 인식?
                    liveBasketListData.value = BasketList
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
                        document.reference.update("dday",calDday(document.data["useby"].toString(),liveTodayData.value))
                    }
                } else {
                    Log.w(TAG, "Error getting documents.", task.exception)
                }
            }
    }

    /**장바구니에 들어가는 아이템 take필드를 true로 셋*/
    fun takeItem(itemName: String) {
        player.document(itemName).update("take", true)
    }

    fun Date2String(date: Int): String{
        if (date < 10) return "0" + date.toString()

        else return date.toString()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getTime(){
        var selectedDate = LocalDate.now()
        liveTodayData.value = selectedDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
    }

    /**Calculate Dday*/
    fun calDday(eDate: String, sDate: String?) : Long{
        val dateFormat = SimpleDateFormat("yyyyMMdd")
        val sDateTime = dateFormat.parse(sDate).time
        val eDateTime = dateFormat.parse(eDate).time
        return ((sDateTime-eDateTime) / (24 * 60 * 60 * 1000))
    }

    /**DB의 useby에서 해당월과 같은 월인 monthFoodList를 가져옴*/
    fun getMonthFood(syyyyMM: String) : MutableList<CalFoodBox> {

        var lst = mutableListOf<CalFoodBox>()
        for (i in liveItemListData.value!!){
            val useby = i.data?.get("useby").toString()
            if(useby.substring(0 until 6) == syyyyMM){
                lst.add(CalFoodBox(i.id, useby))
            }
        }
        return lst
    }
}
