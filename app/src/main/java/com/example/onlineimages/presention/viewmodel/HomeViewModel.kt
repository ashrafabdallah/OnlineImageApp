package com.example.onlineimages.presention.viewmodel

import androidx.lifecycle.ViewModel
import com.example.onlineimages.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository
):ViewModel() {
    val allImages= repository.getAllImages()
}