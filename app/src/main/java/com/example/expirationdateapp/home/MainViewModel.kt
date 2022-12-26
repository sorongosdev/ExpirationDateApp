package com.example.expirationdateapp.home
import android.content.ContentValues.TAG
import android.util.Log

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.expirationdateapp.basket.BasketListAdapter
import com.example.expirationdateapp.basket.BasketListLayout
import com.google.firebase.firestore.*
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel: ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    lateinit var listenerBasic: ListenerRegistration
    val liveItemListData = MutableLiveData<List<DocumentSnapshot>>()

//    val liveBasketListData = MutableLiveData<List<DocumentSnapshot>>()
    val liveBasketListData = MutableLiveData<List<BasketListLayout>>()
    lateinit var BasketList : MutableList<BasketListLayout> // can append
//    val liveBasketListData = MutableLiveData<BasketListLayout>()
//    var liveBasketItemData = MutableLiveData<BasketListLayout>()
//    var liveBasketItemData = MutableLiveData<String>()

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

                        /**장바구니 리스트 업데이트*/
                        if (doc["take"] == true) {
                            Log.d("liveBasketListData","${doc.id} => ${doc["take"]}")
                            BasketList.add(BasketListLayout(doc.id))
//                            BasketList = BasketList + BasketListLayout(doc.id)
//                            liveBasketItemData.value = BasketListLayout(doc.id)
//                            val liveBasketListData = MutableLiveData<List<BasketListLayout>>()
//                            liveBasketListData.value = mutableListOf(BasketListLayout(doc.id))
//                            liveBasketListData.value.addAll(mutableListOf(BasketListLayout(doc.id)))
//                            liveBasketItemData.value = BasketListLayout(doc.id)
//                            Log.d("liveBasketItemData","updated, ${liveBasketItemData.value}")
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
//        liveBasketItemData.value = BasketListLayout(player.document(itemName).toString())
//        Log.d("takeItem","${player.document(itemName).get()}")
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
