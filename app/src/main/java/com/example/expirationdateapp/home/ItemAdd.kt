package com.example.expirationdateapp.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expirationdateapp.R
import com.example.expirationdateapp.databinding.FragmentItemAddBinding
import com.google.firebase.firestore.FirebaseFirestore

class ItemAdd : Fragment() {
    private lateinit var binding: FragmentItemAddBinding
//    val db = FirebaseFirestore.getInstance()
//lateinit var model: MainViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_item_add,container,false)
        binding.lifecycleOwner =viewLifecycleOwner

        // fragment2 viewmodel
        val model = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        // Firestore Write, 추가 버튼을 눌러 데이터 추가
        binding.itemRegister.setOnClickListener {
            val aItemName = binding.itemName.text.toString()
            val aItemExpire = binding.itemExpire.text.toString()

            model.addItem(aItemName,ListLayout(aItemName,aItemExpire))
            
            // 등록버튼 눌렀을 때
            val navController = findNavController()
            navController.popBackStack()
        }

        return binding.root
    }
}