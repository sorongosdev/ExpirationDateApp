package com.example.expirationdateapp.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.expirationdateapp.R
import com.example.expirationdateapp.basket.SearchFragment
import com.example.expirationdateapp.calender.CalenderFragment
import com.example.expirationdateapp.databinding.ActivityNaviBinding
import com.google.firebase.firestore.FirebaseFirestore

class NaviActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNaviBinding
//    val binding by lazy { ActivityNaviBinding.inflate(layoutInflater)}
//    val db = FirebaseFirestore.getInstance()    // Firestore 인스턴스 선언
//    val itemList = arrayListOf<ListLayout>()    // 리스트 아이템 배열
//    val adapter = ListAdapter(itemList)         // 리사이클러 뷰 어댑터

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNaviBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // fragment controller
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.findNavController()

        binding.navigationView.setupWithNavController(navController)
    }
}