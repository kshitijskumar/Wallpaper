package com.example.wallpaper.features.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wallpaper.R
import com.example.wallpaper.components.PhoneWallpaperFrameTemplate
import com.example.wallpaper.domain.models.PhotoResponseModel

@Composable
fun DetailsScreen(
    photo: PhotoResponseModel = PhotoResponseModel()
) {

    val scrollState = rememberScrollState()

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