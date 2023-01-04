package com.example.expirationdateapp.login

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.expirationdateapp.R
import com.example.expirationdateapp.databinding.FragmentLoginBinding
import com.example.expirationdateapp.home.NaviActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
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
//            it.findNavController().navigate(R.id.action_loginFragment_to_homeFragment) // `view->` 를 대채하는 `it`
            signIn(binding.logInputId.text.toString(),binding.logInputPw.text.toString())
        }

//        Handler(Looper.getMainLooper()).postDelayed({
//            findNavController().popBackStack()
//        }, 100)

        return binding.root
    }

    /**로그인*/
    private fun signIn(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth?.signInWithEmailAndPassword(email, password)
                ?.addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        findNavController().popBackStack()
                        Log.d(ContentValues.TAG,"signInWithEmail: success")
                        moveHome(auth?.currentUser)
                    } else {
                        Log.d(ContentValues.TAG,"signInWithEmail: failed")
                    }
                }
        }
    }

    /**로그인 성공시 화면 전환*/
    fun moveHome(user: FirebaseUser?){
        if(user != null){
            val intent= Intent(getActivity(), NaviActivity::class.java)
            startActivity(intent)
        }
    }
}