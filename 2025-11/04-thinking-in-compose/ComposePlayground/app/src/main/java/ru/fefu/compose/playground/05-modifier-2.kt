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

@Preview(showBackground = true, widthDp = 420)
@Composable
private fun Preview_Modifier_Rainbow() {
    MaterialTheme {
        Surface {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("Padding/Background в разном порядке -> совершенно разный результат")

                val rainbow = Modifier
                    .border(1.dp, Color.Black)
                    .background(Color(0xFFFFCDD2))
                    .padding(10.dp)
                    .background(Color(0xFFFFF9C4))
                    .padding(10.dp)
                    .background(Color(0xFFBBDEFB))
                    .padding(10.dp)
                    .background(Color(0xFFC8E6C9))
                    .padding(10.dp)

                Box(rainbow) {
                    Text("UI inside", Modifier.background(Color.White).padding(8.dp))
                }

                val notRainbow = Modifier
                    .border(1.dp, Color.Black)
                    .padding(10.dp)
                    .background(Color(0xFFFFCDD2))
                    .padding(10.dp)
                    .background(Color(0xFFFFF9C4))
                    .padding(10.dp)
                    .background(Color(0xFFBBDEFB))
                    .padding(10.dp)
                    .background(Color(0xFFC8E6C9))

                Box(notRainbow) {
                    Text("Same modifiers,\nother order", Modifier.background(Color.White).padding(8.dp))
                }
            }
        }
    }
}
