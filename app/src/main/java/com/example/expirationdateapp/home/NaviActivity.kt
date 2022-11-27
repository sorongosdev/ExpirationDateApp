package com.example.expirationdateapp.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.expirationdateapp.R
import com.example.expirationdateapp.databinding.ActivityNaviBinding

class NaviActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNaviBinding

//    private val viewModel : MainViewModel by viewModels<MainViewModel>()
////    private val TAG = NaviActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNaviBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // fragment controller
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.findNavController()
        binding.navigationView.setupWithNavController(navController)

//        // viewmodel
//        viewModel.getList()
    }
}