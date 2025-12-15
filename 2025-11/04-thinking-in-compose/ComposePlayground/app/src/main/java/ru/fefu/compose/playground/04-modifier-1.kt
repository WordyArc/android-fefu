package ru.fefu.compose.playground

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape

@Preview(showBackground = true, widthDp = 420)
@Composable
private fun Preview_ClickTarget_Order() {
    MaterialTheme {
        Surface {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("Попробуй кликнуть по ВНЕШНЕМУ отступу (серой зоне)")

                ClickCase(
                    title = "A: clickable BEFORE padding (паддинг кликабельный)",
                    buildModifier = { onClick ->
                        Modifier
                            .fillMaxWidth()
                            .clickable(onClick = onClick)
                            .padding(16.dp)
                    }
                )

                ClickCase(
                    title = "B: padding BEFORE clickable (паддинг НЕ кликабельный)",
                    buildModifier = { onClick ->
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clickable(onClick = onClick)
                    }
                )
            }
        }
    }
}

@Composable
private fun ClickCase(
    title: String,
    buildModifier: (onClick: () -> Unit) -> Modifier
) {
    var clicks by remember { mutableStateOf(0) }
    val shape = RoundedCornerShape(16.dp)

    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(title, style = MaterialTheme.typography.titleSmall)

        // Внешняя рамка
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFFB0BEC5), shape),
            shape = shape,
            tonalElevation = 1.dp,
        ) {
            Box(
                modifier = buildModifier { clicks++ }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color(0xFF90A4AE), shape)
                        .padding(12.dp)
                ) {
                    Text("Clicks: $clicks")
                    Text("Нажми на серую область вокруг контента и сравни A vs B")
                }
            }
        }
    }
}
