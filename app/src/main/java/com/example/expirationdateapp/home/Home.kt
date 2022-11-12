package com.example.expirationdateapp.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expirationdateapp.R
import com.example.expirationdateapp.databinding.FragmentHomeBinding
import com.google.firebase.firestore.FirebaseFirestore

class Home : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    val db = FirebaseFirestore.getInstance()
    val itemList = arrayListOf<ListLayout>()
    val adapter = ListAdapter(itemList)
    // fragment 안에서 fragment manager 사용하기 위해 parentFragmentManger 사용
//    val navHostFragment = childFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//    val navController = navHostFragment.navController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home,container,false)
        binding.lifecycleOwner =viewLifecycleOwner

        //recycer view and adapter
        binding.rvList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvList.adapter = adapter

        //아이템 추가하는 화면으로 전환
        binding.btnAdd.setOnClickListener{
            it.findNavController().navigate(R.id.action_home_to_item_add) // `view->` 를 대채하는 `it`
            //화면 종료
        }

        //Firestore Read, collection이름으로 field의 name 요소 불러오기
        db.collection("player").get()   // 작업할 컬렉션에서 문서 가져오기
            .addOnSuccessListener { result ->
                // 성공할 경우`
                for (document in result) {  // 가져온 문서들은 result에 들어감
                    val item = ListLayout(document["name"] as String, document["useby"] as String)
                    itemList.add(item) // 있는 것을 매번 다 가져오고 있음
                }
//                adapter.notifyDataSetChanged()  // 전체 리사이클러뷰 갱신
            }
            .addOnFailureListener { exception ->
                Log.w("Home", "Error getting documents: $exception")    // 실패할 경우
            }
        // Firestore snapshot으로 불러오기
//        db.collection("player").get().

        return binding.root
    }
}