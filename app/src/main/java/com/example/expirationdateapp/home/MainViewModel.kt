package com.example.expirationdateapp.home

import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.expirationdateapp.R
import com.example.expirationdateapp.basket.BasketListLayout
import com.example.expirationdateapp.calender.CalFoodBox
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.Objects.isNull

@RequiresApi(Build.VERSION_CODES.O)
class MainViewModel: ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    lateinit var listenerBasic: ListenerRegistration
    val liveItemListData = MutableLiveData<List<DocumentSnapshot>>()
    private var auth : FirebaseAuth = Firebase.auth

    val liveBasketListData = MutableLiveData<List<BasketListLayout>>()
    lateinit var BasketList : MutableList<BasketListLayout> // can append

    /**로그인 사용자 정보*/
//    val player = db.collection(auth.uid.toString())
    val player = db.collection("player")
    lateinit var dataStr :String

    /**오늘 날짜를 불러오는 부분*/
    var liveTodayData = MutableLiveData<String>()

    init {
        getList("name")
        getTime()
        Log.d(TAG,"사용자 uid : ${auth.uid}")
    }

    /**snapshot read*/
    fun getList(field: String) {
        BasketList = mutableListOf<BasketListLayout>()

        listenerBasic = player.orderBy(field)
            .addSnapshotListener { snapshot, e ->
                BasketList.clear()

                if (e != null) {
                    Log.d(TAG, "User VM not listening")
                }
                if (snapshot != null) { // capture added
                    Log.d(TAG,"snapshot isEmpty : ${snapshot.isEmpty}") // 스냅샷의 변화의 유무
                    if (snapshot.isEmpty) {
                        liveItemListData.value = emptyList()
                        liveBasketListData.value = emptyList()
                    }
                    else {
                        for (doc in snapshot) {
                            Log.d(TAG, "${snapshot.documents}")
                            liveItemListData.value = snapshot.documents
                            /**장바구니 리스트 업데이트*/
                            if (doc["take"] == true) {
                                BasketList.add(BasketListLayout(doc.id))
                            }
                        }
                        liveBasketListData.value = BasketList
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

    /**장바구니에서 삭제*/
    fun takeOutItem(itemName: String) {
        player.document(itemName).update("take", false)
    }

    /**날짜의 일의 자리 숫자 앞에 0 붙여서 반환하는 함수*/
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

    /***/

    /**DB의 useby에서 해당월과 같은 월인 monthFoodList를 가져옴*/
    fun getMonthFood(syyyyMM: String) : MutableList<CalFoodBox> {

        var lst = mutableListOf<CalFoodBox>()
        if (liveItemListData.value != null){
            for (i in liveItemListData.value!!) {
                val useby = i.data?.get("useby").toString()
                if (useby.substring(0 until 6) == syyyyMM) {
                    lst.add(CalFoodBox(i.id, useby))
                }
            }
        }
        else {
            Log.d(TAG, "liveItemListData null")

        }
        return lst
    }

    fun cancelAction(view: View, temp: DocumentSnapshot){
        /**실행 취소 토스트*/
        val snackbar = Snackbar.make(view,"아이템을 삭제합니다.",Snackbar.LENGTH_INDEFINITE)
        snackbar.setAction("실행취소") {
            snackbar.dismiss()
            /**undo*/
            Log.d("cancelAction", "dDay isNull => ${temp["dday"]}")
            Log.d("cancelAction", "take isNull => ${isNull(temp["take"])}")

            addItem(temp["name"].toString(),
                ListLayout(
                    temp["name"].toString(),
                    temp["register"].toString(),
                    temp["useby"].toString(),
                    temp["dday"].toString().toLong(), // null
                    temp["take"].toString().toBoolean()
                )
            )
        }
        snackbar.show()
    }
}
