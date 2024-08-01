package com.example.trial1

import android.media.Image
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trial1.ui.theme.Trial1Theme

class GHints : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DisGUI()
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun DisGUI() {
    var randomCountry by remember { mutableStateOf(randomCountry()) }
    var flag_chosen by remember { mutableStateOf(randomCountry.imageResource) }
    var countryName = randomCountry.name.toUpperCase()
    var guessedCharacters by remember { mutableStateOf(StringBuilder("-".repeat(countryName.length))) }
    var inputCharacter by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
            randomCountry = randomCountry()
            flag_chosen = randomCountry.imageResource
            countryName = randomCountry.name.toUpperCase()
            guessedCharacters = StringBuilder("-".repeat(countryName.length))
        }) {
            Text("Guess-Hints")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painterResource(id = flag_chosen),
            contentDescription = "Flag image",
            modifier = Modifier.size(200.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = guessedCharacters.toString(),
            fontSize = 50.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = inputCharacter,
            onValueChange = { inputCharacter = it },
            singleLine = true,
            label = { Text("Enter a character") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val char = inputCharacter.uppercase().firstOrNull()
                if (char != null && char in 'A'..'Z') {
                    val indices = countryName.indices.filter { countryName[it] == char }
                    if (indices.isNotEmpty()) {
                        indices.forEach { guessedCharacters[it] = char }
                    }
                }
                inputCharacter = ""
            }
        ) {
            Text("Submit")
        }
    }
}
