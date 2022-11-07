package com.example.expirationdateapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.adapters.AdapterViewBindingAdapter.setOnItemSelectedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.expirationdateapp.databinding.ActivityNaviBinding

class NaviActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNaviBinding
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