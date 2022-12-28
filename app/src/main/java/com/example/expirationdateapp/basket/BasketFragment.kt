package com.example.expirationdateapp.basket

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expirationdateapp.R
import com.example.expirationdateapp.calender.FoodListAdapter
import com.example.expirationdateapp.databinding.FragmentBasketBinding
import com.example.expirationdateapp.home.ListAdapter
import com.example.expirationdateapp.home.MainViewModel

class BasketFragment : Fragment() {
    private lateinit var binding: FragmentBasketBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_basket,container,false)
        binding.lifecycleOwner =viewLifecycleOwner

        binding.basketRv.apply{
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = BasketListAdapter(mutableListOf())
        }

        val model = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        /**리스트뷰 업데이트*/
        model.liveBasketListData.observe(viewLifecycleOwner){
            (binding.basketRv.adapter as BasketListAdapter).setData(it)
            Log.d("liveBasketListData","set data")
        }

        return binding.root
    }
}