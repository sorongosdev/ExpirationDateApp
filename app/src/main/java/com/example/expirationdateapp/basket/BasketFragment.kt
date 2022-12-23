package com.example.expirationdateapp.basket

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expirationdateapp.R
import com.example.expirationdateapp.databinding.FragmentBasketBinding
import com.example.expirationdateapp.basket.ListAdapter
import com.example.expirationdateapp.home.MainViewModel

class BasketFragment : Fragment() {
    private lateinit var binding: FragmentBasketBinding
    val model = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_basket,container,false)
        binding.lifecycleOwner =viewLifecycleOwner

        // take=true이면 갖고 온다
        /**recycer view and adapter*/
        binding.basketRv.apply{
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = ListAdapter(emptyList())
        }

        /**리스트뷰 업데이트*/
        model.liveBasketListData.observe(viewLifecycleOwner){
            (binding.basketRv.adapter as ListAdapter).setData(it)
        }

        return binding.root
    }
}