package com.example.wallpaper.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
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

@Composable
fun SearchBarEditField(
    onTextChanged: (String) -> Unit = {}
) {

    var localValue by rememberSaveable {
        mutableStateOf("")
    }

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp, 0.dp),
        value = localValue,
        onValueChange = {
            localValue = it
            onTextChanged.invoke(it)
        },
        placeholder = {
            Text(text = stringResource(id = R.string.search_your_wallpaper))
        },
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Black,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            backgroundColor = LightGrey
        ),
        shape = RoundedCornerShape(8.dp)
    )
}

@Preview(showSystemUi = false)
@Composable
fun SearchBarDisplayPreview() {
    SearchBarEditField()
}