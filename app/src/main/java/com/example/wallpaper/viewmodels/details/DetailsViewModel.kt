package com.example.wallpaper.viewmodels.details

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.wallpaper.domain.models.PhotoResponseModel
import com.example.wallpaper.domain.repositories.details.DetailsRepository
import com.example.wallpaper.utils.DispatcherProvider
import com.example.wallpaper.utils.Injector
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailsViewModel(
    private val photoId: Long,
    private val repo: DetailsRepository = Injector.detailsRepository,
    private val dispatchers: DispatcherProvider = Injector.dispatchers
) : ViewModel() {

    val isWallpaperSaved = mutableStateOf(false)

    init {
        viewModelScope.launch(dispatchers.ioDispatcher) {
            val isSaved = repo.isWallpaperSaved(photoId)
            withContext(dispatchers.mainDispatcher) {
                isWallpaperSaved.value = isSaved
            }
        }
    }

    fun addOrRemoveWallpaperFromSaved(photo: PhotoResponseModel) = viewModelScope.launch(dispatchers.ioDispatcher) {
        if (isWallpaperSaved.value) {
            repo.removeWallpaperFromSaved(photo = photo)
        } else {
            repo.addWallpaperToSaved(photo)
        }
        isWallpaperSaved.value = !isWallpaperSaved.value
    }
}

class DetailsViewModelFactory(private val photoId: Long) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val vm = DetailsViewModel(photoId)
        return vm as T
    }
}