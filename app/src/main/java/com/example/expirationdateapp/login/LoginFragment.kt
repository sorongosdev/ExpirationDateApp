package com.example.expirationdateapp.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.expirationdateapp.R
import com.example.expirationdateapp.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {
    lateinit var binding: FragmentLoginBinding
    private var auth : FirebaseAuth = Firebase.auth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        /**가입하기 버튼*/
        binding.btnRegister.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginFragment_to_registerFragment) // `view->` 를 대채하는 `it`
        }

        /**로그인 버튼*/
        binding.btnLogin.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginFragment_to_homeFragment) // `view->` 를 대채하는 `it`
        }

        /**로그인 구현*/



        return binding.root
    }

//    override fun onStart() {
//        super.onStart()
//        // Check if user is signed in (non-null) and update UI accordingly.
//        val currentUser = auth.currentUser
//        if(currentUser != null){
////            reload()
//        }
//    }
}