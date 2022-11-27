package com.example.expirationdateapp.home

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.QuerySnapshot

class MainViewModel(): ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    lateinit var listenerBasic: ListenerRegistration
    val LiveItemListData = MutableLiveData<List<DocumentSnapshot>>()

    init {
//        reload()
        getList()
    }

//    fun reload() {
//        db.collection("player").get()   // 작업할 컬렉션에서 문서 가져오기
//            .addOnSuccessListener { result ->
//                //firestore write
//                for (doc in result) {  // 가져온 문서들은 result에 들어감
//                    LiveItemListData.value = ListLayout(doc["name"] as String, doc["useby"] as String)
//                }
//            }
//    }

    fun addItem(itemName: String, itemList: ListLayout) {
        db.collection("player").document(itemName).set(itemList) // 실제 파이어베이스에 입력
            .addOnFailureListener { exception ->
                Log.w("ItemAdd", "Error setting documents: $exception")    // 실패할 경우
            }
    }

    //snapshot read
    fun getList() {
        listenerBasic = db.collection("player")
            .orderBy("name")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.d(TAG, "User VM not listening")
                }
                if (snapshot != null) {
                    for (doc in snapshot) {
                        LiveItemListData.value = snapshot?.documents
                    }
                }
            }
    }
}
