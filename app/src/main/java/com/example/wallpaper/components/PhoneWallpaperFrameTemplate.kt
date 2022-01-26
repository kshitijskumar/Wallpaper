package com.example.wallpaper.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberImagePainter
import com.example.wallpaper.R
import com.example.wallpaper.domain.models.PhotoResponseModel

@Composable
fun PhoneWallpaperFrameTemplate(
    photo: PhotoResponseModel = PhotoResponseModel()
) {

    val templateHorizontalMargin = 80.dp
    val templatePaddingValues = PaddingValues(templateHorizontalMargin, 0.dp)

    ConstraintLayout(
        modifier = Modifier
            .wrapContentSize()
    ) {
        val (mobileTemplate, wallpaper, dateTimeTab) = createRefs()
        Image(
            modifier = Modifier
                .constrainAs(wallpaper) {
                    top.linkTo(mobileTemplate.top)
                    bottom.linkTo(mobileTemplate.bottom)
                    start.linkTo(mobileTemplate.start, templateHorizontalMargin)
                    end.linkTo(mobileTemplate.end, templateHorizontalMargin)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
                .padding(start = 7.dp, top = 3.dp, end = 6.dp, bottom = 1.dp)
                .clip(RoundedCornerShape(20.dp)),
            painter = rememberImagePainter(data = photo.photoSrc.portrait),
            contentDescription = photo.description,
            contentScale = ContentScale.Crop
        )

        Image(
            modifier = Modifier
                .layoutId(mobileTemplate.id)
                .fillMaxWidth()
                .padding(templatePaddingValues)
                .wrapContentHeight(),
            painter = painterResource(id = R.drawable.mobile_frame_template),
            contentDescription = "mobile frame"
        )

        val dateTimeTabHorizontalMargin = templateHorizontalMargin + 20.dp

        DateTabComponent(
            modifier = Modifier
                .constrainAs(dateTimeTab) {
                    top.linkTo(mobileTemplate.top, 40.dp)
                    start.linkTo(mobileTemplate.start, dateTimeTabHorizontalMargin)
                    end.linkTo(mobileTemplate.end, dateTimeTabHorizontalMargin)
                    width = Dimension.fillToConstraints
                }
        )

    }

}

@Preview(showSystemUi = true)
@Composable
fun PhoneWallpaperFrameTemplatePreview() {
    PhoneWallpaperFrameTemplate()
}