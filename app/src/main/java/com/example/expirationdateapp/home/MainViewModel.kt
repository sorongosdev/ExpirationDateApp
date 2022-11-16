package com.example.expirationdateapp.home

import androidx.lifecycle.ViewModel

class MainViewModel(): ViewModel() {
    var test_list = mutableListOf<ListLayout>()
    // firestore read한 리스트를 여기에 저장
}