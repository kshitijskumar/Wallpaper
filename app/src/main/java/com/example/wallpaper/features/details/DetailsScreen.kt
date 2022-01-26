package com.example.wallpaper.features.details

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.wallpaper.R
import com.example.wallpaper.components.PhoneWallpaperFrameTemplate
import com.example.wallpaper.domain.models.PhotoResponseModel
import com.example.wallpaper.features.details.wallpapermanager.WallpaperSetter
import com.example.wallpaper.utils.shortToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DetailsScreen(
    photo: PhotoResponseModel = PhotoResponseModel()
) {

    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    var isLoading by remember {
        mutableStateOf(false)
    }

    val wallpaperSetter: WallpaperSetter = remember { WallpaperSetter(context) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Spacer(modifier = Modifier.height(30.dp))
            Box(
                contentAlignment = Alignment.Center
            ) {
                PhoneWallpaperFrameTemplate(photo)
                if (isLoading) {
                    CircularProgressIndicator()
                }
            }
        }

        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp, 0.dp)
            ) {
                val photographerName = stringResource(
                    R.string.amazing_click_by_x,
                    photo.photographer
                )
                Text(
                    text = photographerName,
                    fontSize = 18.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = photo.description,
                    color = Color.Gray,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light
                )

                Spacer(modifier = Modifier.height(10.dp))
            }

            SetWallpaperButton(
                context = context,
                scope = scope,
                wallpaperSetter = wallpaperSetter,
                photo = photo,
            ) {
                isLoading = it
            }

        }

    }

}

@Composable
fun SetWallpaperButton(
    context: Context,
    scope: CoroutineScope,
    wallpaperSetter: WallpaperSetter,
    photo: PhotoResponseModel,
    shouldStartLoading: (Boolean) -> Unit = {}
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(40.dp, 0.dp)
            .padding(0.dp, 10.dp),
        onClick = {
            scope.launch {
                shouldStartLoading.invoke(true)
                val isSuccessful = wallpaperSetter.setWallpaper(photo.photoSrc.original)
                shouldStartLoading.invoke(false)
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

@Preview(showSystemUi = true)
@Composable
fun DetailsScreenPreview() {
    DetailsScreen()
}