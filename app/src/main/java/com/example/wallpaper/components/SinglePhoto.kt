package com.example.wallpaper.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.wallpaper.domain.models.PhotoResponseModel

@Composable
fun SinglePhoto(
    modifier: Modifier = Modifier,
    model: PhotoResponseModel = PhotoResponseModel(),
    onImageClick: (PhotoResponseModel) -> Unit = {}
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(0.75f)
            .clip(RoundedCornerShape(8.dp))
            .shadow(0.dp)
            .clickable (onClick = { onImageClick.invoke(model) })
    ) {
        Image(
            painter = rememberImagePainter(
                data = model.photoSrc.portrait,
                builder = {
                    crossfade(true)
                }
            ),
            contentDescription = model.description,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun SinglePhotoPrev() {
    SinglePhoto()
}