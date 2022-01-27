package com.example.wallpaper.features.home

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wallpaper.components.PhotosGridList
import com.example.wallpaper.components.SearchBarDisplay
import com.example.wallpaper.navigation.NavigationManager
import com.example.wallpaper.navigation.navigationManager
import com.example.wallpaper.viewmodels.home.HomeUiState
import com.example.wallpaper.viewmodels.home.HomeViewModel
import kotlinx.coroutines.CoroutineScope

@ExperimentalFoundationApi
@Composable
fun HomeScreen(
    scope: CoroutineScope = rememberCoroutineScope(),
    homeVM: HomeViewModel = viewModel(modelClass = HomeViewModel::class.java),
    navManager: NavigationManager,
) {
    val context = LocalContext.current

    val uiState = homeVM.homeUiState.observeAsState()
    val curatedImagesList = homeVM.curatedList.observeAsState(listOf())
    var lastIndexUsedForPagination by remember {
        mutableStateOf(1)
    }

    // launched effects
    LaunchedEffect(key1 = lastIndexUsedForPagination) {
        homeVM.getInitialOrPaginatedCuratedPhotos()
    }
    if (uiState.value is HomeUiState.Error) {
        LaunchedEffect(key1 = uiState.value) {
            Toast.makeText(context,
                (uiState.value as HomeUiState.Error).errorMsg,
                Toast.LENGTH_SHORT).show()
        }
    }

    // ui
    Column {

        Spacer(modifier = Modifier.height(8.dp))
        SearchBarDisplay(onSearchClicked = navManager::navigateToSearchScreen)
        Spacer(modifier = Modifier.height(8.dp))
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val (circularLoader, paginatedLoader, imagesGridList) = createRefs()
            PhotosGridList(
                modifier = Modifier.constrainAs(imagesGridList) {
                    top.linkTo(parent.top)
                },
                photosList = curatedImagesList.value,
                onImageClick = navManager::navigateToDetailsScreen,
                fetchMore = { lastIndexUsedForPagination = it }
            )
            if (uiState.value is HomeUiState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.constrainAs(circularLoader) {
                        top.linkTo(parent.top, 18.dp)
                        centerHorizontallyTo(parent)
                    }
                )
            }
            if (uiState.value is HomeUiState.PaginationLoading) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .constrainAs(paginatedLoader) {
                            bottom.linkTo(parent.bottom)
                        }
                        .fillMaxWidth()
                )
            }
        }
    }
}


@ExperimentalFoundationApi
@Preview(showSystemUi = true)
@Composable
fun HomeScreenPrev() {
    HomeScreen(navManager = navigationManager())
}