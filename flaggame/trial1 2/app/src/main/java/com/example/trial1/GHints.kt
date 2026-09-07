package com.example.trial1

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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
    // Only the flag's drawable resource id (an Int) and the guessed
    // characters (a String) are saved across rotation/process death; the
    // Country object itself is derived from the id so state survives
    // configuration changes.
    var flagChosen by rememberSaveable { mutableStateOf(randomCountry().imageResource) }
    val countryName = remember(flagChosen) {
        Flag.first { it.imageResource == flagChosen }.name.uppercase()
    }
    var guessedCharacters by rememberSaveable(flagChosen) {
        mutableStateOf("-".repeat(countryName.length))
    }
    var inputCharacter by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
            val newFlag = randomCountry().imageResource
            flagChosen = newFlag
            inputCharacter = ""
        }) {
            Text("Guess-Hints")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painterResource(id = flagChosen),
            contentDescription = "Flag image",
            modifier = Modifier.size(200.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = guessedCharacters,
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
                    guessedCharacters = countryName.indices.joinToString("") { i ->
                        if (countryName[i] == char) char.toString() else guessedCharacters[i].toString()
                    }
                }
                inputCharacter = ""
            }
        ) {
            Text("Submit")
        }
    }
}
