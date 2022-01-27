package com.example.wallpaper.viewmodels.search

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpaper.domain.models.CuratedListResponseModel
import com.example.wallpaper.domain.models.PhotoResponseModel
import com.example.wallpaper.domain.repositories.search.SearchRepository
import com.example.wallpaper.utils.DataResult
import com.example.wallpaper.utils.DispatcherProvider
import com.example.wallpaper.utils.Injector
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchViewModel(
    private val repo: SearchRepository = Injector.searchRepository,
    private val dispatchers: DispatcherProvider = Injector.dispatchers
) : ViewModel() {

    private val _searchResults = MutableLiveData<CuratedListResponseModel>()
    val searchResults: LiveData<CuratedListResponseModel> get() = _searchResults

    val uiState = mutableStateOf<SearchUiState>(SearchUiState.Idle)

    private var currentSearchQuery: String = ""
    private var currentPageNumber: Int = 1

    private var searchJob: Job? = null

    private fun initiateSearch() {
        searchJob?.cancel()
        searchJob = null
        searchJob = viewModelScope.launch(dispatchers.mainDispatcher) {
            delay(500)
            withContext(dispatchers.ioDispatcher) {
                val result = repo.fetchImagesForSearchQuery(currentSearchQuery, currentPageNumber)
                handleSearchResult(result)
            }

        }
    }

    private suspend fun handleSearchResult(result: DataResult<CuratedListResponseModel>) {
        when(result) {
            is DataResult.Success -> {
                val data = result.data
                val currentResultQuery = searchResults.value?.correspondingQueryForThisResult ?: ""
                if (data.correspondingQueryForThisResult == currentResultQuery) {
                    val updatedList = mutableListOf<PhotoResponseModel>().apply {
                        addAll(searchResults.value?.photos ?: listOf())
                        addAll(data.photos)
                    }
                    val newData = data.copy(photos = updatedList).apply {
                        correspondingQueryForThisResult = data.correspondingQueryForThisResult
                    }
                    _searchResults.postValue(newData)
                } else {
                    _searchResults.postValue(data)
                }
                uiState.value = SearchUiState.Success
            }
            is DataResult.Error -> {
                uiState.value = SearchUiState.Error(result.errorMsg)
            }
        }
    }

    fun updateSearchQuery(newSearchQuery: String) {
        if (newSearchQuery.isEmpty()) {
            searchJob?.cancel()
            searchJob = null
            uiState.value = SearchUiState.Idle
        } else {
            uiState.value = SearchUiState.Loading
            currentPageNumber = 1
            currentSearchQuery = newSearchQuery
            initiateSearch()
        }
    }

    fun updatePageNumber() {
        currentPageNumber += 1
        uiState.value = SearchUiState.PaginatedLoading
        initiateSearch()
    }


}

sealed class SearchUiState {
    object Success : SearchUiState()
    object Loading : SearchUiState()
    object PaginatedLoading : SearchUiState()
    data class Error(val errorMsg: String) : SearchUiState()
    object Idle : SearchUiState()
}