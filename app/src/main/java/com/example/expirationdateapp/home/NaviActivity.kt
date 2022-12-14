package com.example.expirationdateapp.home

import android.content.ContentValues
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.expirationdateapp.MyFirebaseMessagingService
import com.example.expirationdateapp.R
import com.example.expirationdateapp.databinding.ActivityNaviBinding

class NaviActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNaviBinding

    private val model : MainViewModel by viewModels<MainViewModel>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNaviBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // fragment controller
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment2) as NavHostFragment
        val navController = navHostFragment.findNavController()
        binding.navigationView.setupWithNavController(navController)

        /** FCM 설정, Token값 가져오기*/
        MyFirebaseMessagingService().getFirebaseToken()

        /** DynamicLink 수신확인 */
        initDynamicLink()

        model.liveTodayData.observe(this, Observer{
            model.updateItem()
        })
    }
    /** DynamicLink */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun initDynamicLink() {
        val dynamicLinkData = intent.extras //
        if (dynamicLinkData != null) {
            Log.d(ContentValues.TAG, "dynamicLinkData get")
            var dataStr = "DynamicLink 수신받은 값\n"
            for (key in dynamicLinkData.keySet()) {
                dataStr += "key: $key / value: ${dynamicLinkData.getString(key)}\n"
            }
            model.dataStr = dataStr
        }
    }

}