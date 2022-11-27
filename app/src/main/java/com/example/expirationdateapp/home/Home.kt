package com.example.expirationdateapp.home

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expirationdateapp.R
import com.example.expirationdateapp.databinding.FragmentHomeBinding

class Home : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    lateinit var model: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home,container,false)

        //recycer view and adapter
        binding.rvList.apply{
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter =
                ListAdapter(
                    layoutInflater,
                    emptyList()
                )
        }

        //viewmodel
        model = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        model.LiveItemListData.observe(viewLifecycleOwner){
            (binding.rvList.adapter as ListAdapter).setData(it)
        }

        //아이템 추가하는 화면으로 전환
        binding.btnAdd.setOnClickListener{
            it.findNavController().navigate(R.id.action_home_to_item_add) // `view->` 를 대채하는 `it`
            //화면 종료
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView")
    }
}