package ru.fefu.compose.playground

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
private fun ModifierOrderTile(title: String, boxModifier: Modifier) {
    Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(title)
        Box(
            boxModifier
                .border(1.dp, Color.Black)
        )
    }
}

@Preview(showBackground = true, widthDp = 420)
@Composable
private fun Preview_ModifierOrder() {
    MaterialTheme {
        Surface {
            Row(Modifier.padding(16.dp)) {
                ModifierOrderTile(
                    title = "background -> padding",
                    boxModifier = Modifier
                        .size(120.dp)
                        .background(Color(0xFFB3E5FC))
                        .padding(16.dp)
                        .background(Color(0xFFFFF9C4))
                )
                Spacer(Modifier.width(16.dp))
                ModifierOrderTile(
                    title = "padding -> background",
                    boxModifier = Modifier
                        .size(120.dp)
                        .padding(16.dp)
                        .background(Color(0xFFB3E5FC))
                )
            }
        }
    }
}
