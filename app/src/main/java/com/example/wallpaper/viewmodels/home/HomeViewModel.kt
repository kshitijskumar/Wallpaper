package com.example.wallpaper.viewmodels.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpaper.domain.models.CuratedListResponseModel
import com.example.wallpaper.domain.models.PhotoResponseModel
import com.example.wallpaper.domain.repositories.home.HomeRepository
import com.example.wallpaper.utils.DataResult
import com.example.wallpaper.utils.DispatcherProvider
import com.example.wallpaper.utils.Injector
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val repo: HomeRepository = Injector.homeRepository,
    private val dispatchers: DispatcherProvider = Injector.dispatchers,
) : ViewModel() {

    private val _homeUiState = MutableLiveData<HomeUiState<CuratedListResponseModel>>()
    val homeUiState: LiveData<HomeUiState<CuratedListResponseModel>> get() = _homeUiState

    private val _curatedPhotos = MutableLiveData<MutableList<PhotoResponseModel>>(mutableListOf())
    val curatedList: LiveData<MutableList<PhotoResponseModel>> get() = _curatedPhotos

    private var currentPageNumberForCuratedPhotos: Int = 1

    init {
        getInitialOrPaginatedCuratedPhotos()
    }

    fun getInitialOrPaginatedCuratedPhotos() = viewModelScope.launch {
        Log.d("HomeScreen", "vm for $currentPageNumberForCuratedPhotos")
        decideBetweenInitialLoadingAndPaginatedLoading()
        withContext(dispatchers.ioDispatcher) {
            val data = repo.fetchCuratedList(currentPageNumberForCuratedPhotos).toHomeUiState()
            currentPageNumberForCuratedPhotos += 1
            updateNewStateBasedOnCurrentState(data)
        }
    }

    private fun decideBetweenInitialLoadingAndPaginatedLoading() {
        if (_curatedPhotos.value.isNullOrEmpty()) {
            _homeUiState.postValue(HomeUiState.Loading)
        } else {
            _homeUiState.postValue(HomeUiState.PaginationLoading)
        }
    }

    private fun updateNewStateBasedOnCurrentState(newState: HomeUiState<CuratedListResponseModel>) {
        val currentList = curatedList.value ?: listOf<PhotoResponseModel>()

        if (newState is HomeUiState.Success) {
            val newUpdatedList = mutableListOf<PhotoResponseModel>().apply {
                addAll(currentList)
                addAll(newState.data.photos)
            }
            val updatedCuratedListModel = newState.data.copy(photos = newUpdatedList)
            _curatedPhotos.postValue(newUpdatedList)
            _homeUiState.postValue(HomeUiState.Success(updatedCuratedListModel))
        } else {
            _homeUiState.postValue(newState)
        }

    }

}

sealed class HomeUiState<out T> {
    data class Success<T>(val data: T) : HomeUiState<T>()
    object Loading : HomeUiState<Nothing>()
    object PaginationLoading : HomeUiState<Nothing>()
    data class Error(val errorMsg: String) : HomeUiState<Nothing>()
}

fun <R> DataResult<R>.toHomeUiState(): HomeUiState<R> {
    return when (this) {
        is DataResult.Success -> HomeUiState.Success(this.data)
        is DataResult.Error -> HomeUiState.Error(this.errorMsg)
        is DataResult.Loading -> HomeUiState.Loading
    }
}