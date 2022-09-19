package com.franksap2.travelbooking.core.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

private val defaultButtonPadding = PaddingValues(horizontal = 30.dp, vertical = 10.dp)

@Composable
fun TravelBookingButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.button
) {

    Button(
        modifier = modifier,
        onClick = onClick,
        shape = MaterialTheme.shapes.large,
        contentPadding = defaultButtonPadding
    ) {
        Text(
            text = text,
            style =style,
        )
    }

}