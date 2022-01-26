package com.example.wallpaper.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wallpaper.R
import com.example.wallpaper.ui.theme.DarkerGrey
import com.example.wallpaper.ui.theme.LightGrey

@Composable
fun SearchBarDisplay(
    onSearchClicked: () -> Unit = {}
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp, 0.dp)
            .clickable { onSearchClicked.invoke() }
            .background(LightGrey, RoundedCornerShape(8.dp))
            .padding(10.dp, 10.dp),
        text = stringResource(id = R.string.search_your_wallpaper),
        textAlign = TextAlign.Start,
        color = DarkerGrey
    )
}

@Preview(showSystemUi = false)
@Composable
fun SearchBarDisplayPreview() {
    SearchBarDisplay()
}