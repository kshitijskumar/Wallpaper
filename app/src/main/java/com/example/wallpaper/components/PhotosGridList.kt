package com.example.wallpaper.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wallpaper.domain.models.PhotoResponseModel
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@Composable
fun PhotosGridList(
    modifier: Modifier = Modifier,
    photosList: List<PhotoResponseModel> = listOf(),
    shouldScrollToTop: Boolean = false,
    fetchMore: (Int) -> Unit = {},
    onImageClick: (PhotoResponseModel) -> Unit = {},
) {

    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    LazyVerticalGrid(
        modifier = modifier,
        cells = GridCells.Fixed(2),
        state = listState,
        contentPadding = PaddingValues(horizontal = 2.dp, vertical = 4.dp)
    ) {
        itemsIndexed(items = photosList) { index, photo ->
            SinglePhoto(
                model = photo,
                modifier = Modifier.padding(4.dp, 4.dp),
                onImageClick = onImageClick
            )
            if (index == photosList.size - 1) {
                fetchMore.invoke(index)
            }
        }
    }
    if (shouldScrollToTop) {
        LaunchedEffect(key1 = shouldScrollToTop) {
            listState.animateScrollToItem(0)
        }
    }
}

@ExperimentalFoundationApi
@Preview(showSystemUi = true)
@Composable
fun PhotosGridListPrev() {
    PhotosGridList()
}