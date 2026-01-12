package ru.fefu.compose.playground

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

private data class PersonUi(val name: String, val role: String)

@Composable
private fun PersonCard(person: PersonUi, modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Text(person.name, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(6.dp))
            Text(person.role, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Preview(showBackground = true, name = "Light")
@Composable
private fun Preview_PersonCard_Light() {
    MaterialTheme {
        Surface {
            PersonCard(PersonUi("Levi Ackerman", "Captain"), Modifier.padding(16.dp))
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark")
@Composable
private fun Preview_PersonCard_Dark() {
    MaterialTheme {
        Surface {
            PersonCard(PersonUi("Frieren", "Mage"), Modifier.padding(16.dp))
        }
    }
}

@Preview(showBackground = true, fontScale = 1.4f, name = "Large font")
@Composable
private fun Preview_PersonCard_LargeFont() {
    MaterialTheme {
        Surface {
            PersonCard(PersonUi("Spike Spiegel", "Bounty hunter"), Modifier.padding(16.dp))
        }
    }
}