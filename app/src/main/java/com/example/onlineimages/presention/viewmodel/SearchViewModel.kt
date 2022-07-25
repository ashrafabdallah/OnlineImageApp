package com.example.onlineimages.presention.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.onlineimages.data.model.UnsplashImage
import com.example.onlineimages.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect
import javax.inject.Inject
@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: Repository):ViewModel() {

    private val _searchQuery  = mutableStateOf("")
    val searchQuery = _searchQuery

    private val _searchedImages = MutableStateFlow<PagingData<UnsplashImage>>(PagingData.empty())
    val searchedImages= _searchedImages

    fun updateSearchQuery(query: String){
      _searchQuery.value =query
    }
    fun executeSearch(query:String) = viewModelScope.launch(Dispatchers.IO) {
        repository.getSearchImage(query = query).cachedIn(viewModelScope).collect {
            _searchedImages.value = it
        }
    }
}