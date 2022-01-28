package com.example.wallpaper.features.search

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wallpaper.components.PhotosGridList
import com.example.wallpaper.components.SearchBarEditField
import com.example.wallpaper.navigation.NavigationManager
import com.example.wallpaper.navigation.navigationManager
import com.example.wallpaper.utils.shortToast
import com.example.wallpaper.viewmodels.search.SearchUiState
import com.example.wallpaper.viewmodels.search.SearchViewModel
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@Composable
fun SearchScreen(
    searchVm: SearchViewModel = viewModel(),
    navManager: NavigationManager,
) {

    val scope = rememberCoroutineScope()
    val uiState = searchVm.uiState
    val searchResults = searchVm.searchResults.observeAsState()
    var lastIndexForPagination by remember {
        mutableStateOf(0)
    }
    val context = LocalContext.current

    if (uiState.value is SearchUiState.Error) {
        context.shortToast((uiState.value as SearchUiState.Error).errorMsg)
    }
    val shouldScrollToTop = searchVm.shouldScrollToTop

    Column {
        Spacer(modifier = Modifier.height(8.dp))
        SearchBarEditField { newQuery ->
            scope.launch {
                searchVm.updateSearchQuery(newQuery)
                lastIndexForPagination = 0
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            contentAlignment = Alignment.TopCenter
        ) {
            PhotosGridList(
                photosList = searchResults.value?.photos ?: listOf(),
                fetchMore = { lastIndex ->
                    if (lastIndex != lastIndexForPagination) {
                        lastIndexForPagination = lastIndex
                        searchVm.updatePageNumber()
                    }
                },
                shouldScrollToTop = shouldScrollToTop.value,
                onImageClick = navManager::navigateToDetailsScreen
            )

            if (uiState.value is SearchUiState.Loading) {
                CircularProgressIndicator()
            }
        }

        if (uiState.value is SearchUiState.PaginatedLoading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
    }

}

@ExperimentalFoundationApi
@Preview(showSystemUi = true)
@Composable
fun SearchScreenPreview() {
    SearchScreen(navManager = navigationManager())
}