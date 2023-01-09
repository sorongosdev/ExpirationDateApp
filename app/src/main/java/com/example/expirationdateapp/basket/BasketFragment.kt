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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.expirationdateapp.R
import com.example.expirationdateapp.calender.FoodListAdapter
import com.example.expirationdateapp.databinding.FragmentBasketBinding
import com.example.expirationdateapp.home.DeleteItemClick
import com.example.expirationdateapp.home.ItemTouchHelperCallback
import com.example.expirationdateapp.home.ListAdapter
import com.example.expirationdateapp.home.MainViewModel

class BasketFragment : Fragment(), BasketCallBack {
    private lateinit var binding: FragmentBasketBinding
    lateinit var model: MainViewModel
    private val listener = this
    lateinit var helper: ItemTouchHelper

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_basket,container,false)
        binding.lifecycleOwner =viewLifecycleOwner

        binding.basketRv.apply{
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = BasketListAdapter(mutableListOf(), listener)

            val itemTouchHelperCallback = ItemTouchHelperCallback(listener)
            helper = ItemTouchHelper(itemTouchHelperCallback)
            helper.attachToRecyclerView(binding.basketRv)
        }

        model = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        /**리스트뷰 업데이트*/
        model.liveBasketListData.observe(viewLifecycleOwner){
            (binding.basketRv.adapter as BasketListAdapter).setData(it)
        }

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun takeOutItemCall(ItemName: String) {
        model.takeOutItem(ItemName)
    }
    override fun onItemMove(from_position: Int, to_position: Int): Boolean{
        return true
    }
    override fun onItemSwipe(position: Int){

    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onRightClick(position: Int, viewHolder: RecyclerView.ViewHolder?){
        val itemName = model.liveItemListData.value?.get(position)?.id
        if (itemName != null) model.takeOutItem(itemName)
    }
}