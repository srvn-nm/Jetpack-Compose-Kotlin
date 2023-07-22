package com.example.paginglearning.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.paginglearning.utils.UserSource
import kotlinx.coroutines.flow.Flow

class UserViewModel : ViewModel() {
    val user: Flow<PagingData<com.example.paginglearning.model.UserData>> = Pager(PagingConfig(pageSize = 6)){
        UserSource()
    }.flow.cachedIn(viewModelScope)
}