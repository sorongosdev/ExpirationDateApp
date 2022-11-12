package com.example.expirationdateapp.calender

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.expirationdateapp.R
import com.example.expirationdateapp.databinding.FragmentCalenderBinding

class CalenderFragment : Fragment() {
    private lateinit var binding: FragmentCalenderBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calender,container,false)
        binding.lifecycleOwner =viewLifecycleOwner
        return binding.root
    }
}