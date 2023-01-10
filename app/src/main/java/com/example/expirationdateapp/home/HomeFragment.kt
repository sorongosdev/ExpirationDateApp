package com.example.expirationdateapp.home

import android.content.ContentValues.TAG
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.expirationdateapp.R
import com.example.expirationdateapp.databinding.FragmentHomeBinding
import com.google.firebase.firestore.DocumentSnapshot


//class HomeFragment : Fragment(), DeleteItemClick{
class HomeFragment : Fragment(), DeleteItemClick{
    val TAG = "HomeFragment"
    private lateinit var binding: FragmentHomeBinding
    lateinit var model: MainViewModel
    private val listener = this
    lateinit var helper: ItemTouchHelper

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home,container,false)
        binding.lifecycleOwner =viewLifecycleOwner

        /**recycer view and adapter*/
        binding.rvList.apply{
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = ListAdapter(emptyList(),listener)

            val itemTouchHelperCallback = ItemTouchHelperCallback(listener)
            helper = ItemTouchHelper(itemTouchHelperCallback)
            helper.attachToRecyclerView(binding.rvList)
        }

        /**viewmodel*/
        model = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        /**리스트뷰 업데이트*/
        model.liveItemListData.observe(viewLifecycleOwner){
            Log.d(TAG,"liveItemListData observe : $it")
            (binding.rvList.adapter as ListAdapter).setData(it)
        }

        /**아이템 추가하는 화면으로 전환*/
        binding.btnAdd.setOnClickListener{
            it.findNavController().navigate(R.id.action_home_to_item_add) // `view->` 를 대채하는 `it`
            //화면 종료
        }

        /**오늘 날짜에 따라 상단 텍스트 변화*/
        model.liveTodayData.observe(viewLifecycleOwner) {
            binding.tvToken.text = it
        }

        /**정렬 방식*/
        binding.orderAbc.setOnClickListener{
            Log.d(TAG,"가나다순")
            model.getList("name")

        }
        binding.orderUseby.setOnClickListener{
            Log.d(TAG,"만료일순")
            model.getList("useby")
        }

        return binding.root
    }

    /**swipe*/
    override fun onItemMove(from_position: Int, to_position: Int): Boolean{
        return true
    }
    override fun onItemSwipe(position: Int){

    }
    /**장바구니*/
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onLeftClick(position: Int, viewHolder: RecyclerView.ViewHolder?){
        val itemName = model.liveItemListData.value?.get(position)?.id
        if (itemName != null) model.takeItem(itemName)
    }
    /**삭제*/
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onRightClick(position: Int, viewHolder: RecyclerView.ViewHolder?){

        /**store temp*/
        val temp : DocumentSnapshot = model.liveItemListData.value!![position]

        /**delete temp*/
        val itemName = temp.id
        if (itemName != null) model.deleteItem(itemName)

        model.cancelAction(requireView(), temp)
    }
}