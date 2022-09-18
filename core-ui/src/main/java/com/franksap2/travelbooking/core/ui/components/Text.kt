package com.franksap2.travelbooking.core.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.franksap2.travelbooking.core.ui.R

@Composable
fun LocationText(
    text: String,
    secondText: String,
    modifier: Modifier = Modifier,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
    style: TextStyle = LocalTextStyle.current
) {

    Text(
        modifier = modifier,
        text = buildPlaceLocationText(text = text, secondText = secondText),
        style = style,
        inlineContent = buildInlineContent(),
        maxLines = maxLines,
        overflow = overflow
    )
}

@Composable
private fun buildPlaceLocationText(
    text: String,
    secondText: String,
) = buildAnnotatedString {
    appendInlineContent("icon")
    append(text)
    append(", ")
    withStyle(
        style = SpanStyle(
            color = MaterialTheme.colors.onBackground.copy(alpha = 0.7f),
            fontWeight = FontWeight.Light
        )
    ) {
        append(secondText)
    }
}

private fun buildInlineContent() = mapOf(
    "icon" to InlineTextContent(
        placeholder = Placeholder(30.sp, 30.sp, PlaceholderVerticalAlign.TextCenter),
        children = {
            Image(
                painter = painterResource(id = R.drawable.ic_location),
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colors.secondary)
            )
        }
    )
)
