package com.example.expirationdateapp.calender

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expirationdateapp.R
import com.example.expirationdateapp.databinding.CalListLayoutBinding

class CalListLayout(val dayText: String)