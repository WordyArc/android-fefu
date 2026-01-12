package ru.fefu.compose.playground

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
private fun CounterStateful(modifier: Modifier = Modifier) {
    var count by rememberSaveable { mutableIntStateOf(0) }

    CounterContent(
        count = count,
        onDec = { count-- },
        onInc = { count++ },
        modifier = modifier
    )
}

@Composable
private fun BadCounter(modifier: Modifier = Modifier) {
    var count = 0

    CounterContent(
        count = count,
        onDec = { count-- },
        onInc = { count++ },
        modifier = modifier,
    )
}

@Composable
private fun CounterContent(
    count: Int,
    onDec: () -> Unit,
    onInc: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Count: $count", style = MaterialTheme.typography.headlineSmall)
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = onDec) { Text("-1") }
            Button(onClick = onInc) { Text("+1") }
        }
        Text("Контент stateless")
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview_Counter() {
    MaterialTheme {
        Surface {
            Column {
                CounterStateful()
                Spacer(Modifier.width(1.dp))
                //CounterContent(count = 42, onDec = {}, onInc = {}, modifier = Modifier)
                BadCounter()
            }
        }
    }
}
