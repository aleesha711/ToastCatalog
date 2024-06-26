package com.sumup.challenge.toastcatalog.designsystem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CircleWithText(text: String, color: Color, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(40.dp) // Size of the circle
            .clip(CircleShape) // Clip to circle shape
            .background(color), // Background color of the circle
        contentAlignment = Alignment.Center // Center the text within the circle
    ) {
        Text(text = text, style = MaterialTheme.typography.labelLarge, color = Color.White)
    }
}

@Preview
@Composable
fun PreviewCircleWithText() {
    CircleWithText(text = "1", color = Color.Black)
}
