package com.example.expirationdateapp.login

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.expirationdateapp.R
import com.example.expirationdateapp.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlin.reflect.typeOf

class RegisterFragment : Fragment() {
    lateinit var binding: FragmentRegisterBinding
    private var auth : FirebaseAuth = Firebase.auth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        Log.d(TAG,"onCreateView")

        /**가입 구현*/
        binding.btnRegisterConfirm.setOnClickListener {
            Log.d(TAG,"btn clicked")
            createAccount(binding.regInputId.text.toString(), binding.regInputPw.text.toString())
        }
        return binding.root
    }
    private fun createAccount(email: String, password: String) {
        Log.d(TAG,"createAccount")
        Log.d(TAG,"email => $email")
        Log.d(TAG,"pw => $password")

        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth?.createUserWithEmailAndPassword(email, password)
                ?.addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG,"createUserWithEmail:success")
                    } else {
                        Log.d(TAG,"createUserWithEmail:failed")
                    }
                }
        }
    }
}