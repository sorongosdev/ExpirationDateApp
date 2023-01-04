package com.example.expirationdateapp.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.expirationdateapp.databinding.ActivitySplashBinding
import com.example.expirationdateapp.home.NaviActivity
import com.google.firebase.auth.FirebaseUser

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        Handler(Looper.getMainLooper()).postDelayed({
//
////            val intent= Intent( this, NaviActivity::class.java)
////            startActivity(intent)
//        }, 500)
//
    }
//    fun moveHome(user: FirebaseUser?){
//        if(user != null){
////            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
//            val intent= Intent( this, NaviActivity::class.java)
//            startActivity(intent)
//        }
//    }
}