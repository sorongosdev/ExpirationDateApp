package com.example.expirationdateapp.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expirationdateapp.R
import com.example.expirationdateapp.databinding.FragmentItemAddBinding
import com.google.firebase.firestore.FirebaseFirestore

class ItemAdd : Fragment() {
    private lateinit var binding: FragmentItemAddBinding
    val db = FirebaseFirestore.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_item_add,container,false)
        binding.lifecycleOwner =viewLifecycleOwner

        // Firestore Write, 추가 버튼을 눌러 데이터 추가
        binding.itemRegister.setOnClickListener {
            val aItemName=binding.itemName.text
            val aItemExpire=binding.itemExpire.text

            val aItemdata= hashMapOf(
                "name" to aItemName.toString(), // name 필드를 생성, 입력한 aItemName의 String을 입력
                "useby" to aItemExpire.toString()
            )

            db.collection("player").document(aItemName.toString()).set(aItemdata) // 실제 파이어베이스에 입력

                .addOnFailureListener { exception ->
                    Log.w("ItemAdd", "Error setting documents: $exception")    // 실패할 경우
                }
        }

        return binding.root
    }
}