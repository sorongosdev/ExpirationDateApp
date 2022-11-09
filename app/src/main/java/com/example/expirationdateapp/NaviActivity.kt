package com.example.expirationdateapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.adapters.AdapterViewBindingAdapter.setOnItemSelectedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.expirationdateapp.databinding.ActivityNaviBinding
import com.google.firebase.firestore.FirebaseFirestore

class NaviActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNaviBinding
    val db = FirebaseFirestore.getInstance()    // Firestore 인스턴스 선언
    val itemList = arrayListOf<ListLayout>()    // 리스트 아이템 배열
    val adapter = ListAdapter(itemList)         // 리사이클러 뷰 어댑터

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNaviBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigationItemSelect()
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFrameLayout, fragment)
            .commit()
    }

    private fun navigationItemSelect() {
        binding.navigationView.run {
            setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.homeFragment -> replaceFragment(HomeFragment())
                    R.id.searchFragment -> replaceFragment(SearchFragment())
                    R.id.calenderFragment -> replaceFragment(CalenderFragment())
                }
                true
            }
            selectedItemId = R.id.homeFragment
        }
    }


}