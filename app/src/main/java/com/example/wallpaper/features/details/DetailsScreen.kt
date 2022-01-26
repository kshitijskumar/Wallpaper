package com.example.wallpaper.features.details

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wallpaper.R
import com.example.wallpaper.components.PhoneWallpaperFrameTemplate
import com.example.wallpaper.domain.models.PhotoResponseModel
import com.example.wallpaper.features.details.wallpapermanager.WallpaperSetter
import com.example.wallpaper.utils.shortToast
import kotlinx.coroutines.launch

@Composable
fun DetailsScreen(
    photo: PhotoResponseModel = PhotoResponseModel()
) {

    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val wallpaperSetter: WallpaperSetter = remember { WallpaperSetter(context) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        PhoneWallpaperFrameTemplate(photo)

        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.SpaceBetween
        ) {


            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(40.dp, 0.dp)
                    .padding(0.dp, 10.dp),
                onClick = {
                    scope.launch {
                        val isSuccessful = wallpaperSetter.setWallpaper(photo.photoSrc.original)
                        if(isSuccessful) {
                            context.shortToast("Wallpaper successfully set!")
                        } else {
                            context.shortToast("Something went wrong")
                        }

                    }
                },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue)
            ) {
                Text(
                    text = stringResource(id = R.string.set_as_wallpaper),
                    color = Color.White,
                )
            }
        }

    }

}

@Preview(showSystemUi = true)
@Composable
fun DetailsScreenPreview() {
    DetailsScreen()
}