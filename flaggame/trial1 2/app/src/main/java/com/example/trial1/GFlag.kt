package com.example.trial1

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trial1.ui.theme.Trial1Theme

class GFlag : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ///displayGUI()
            FlagGame()
        }
    }
}


@Preview
@Composable
fun FlagGame() {
    val countries = Flag.shuffled().take(256) // Assuming Flag is a list of all countries
    var randomFlags by remember { mutableStateOf(countries.shuffled().take(3)) }
    var correctFlag by remember { mutableStateOf(randomFlags.random()) }
    var selectedFlag by remember { mutableStateOf<Country?>(null) }
    var message by remember { mutableStateOf("") }
    var showNext by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            fontSize = 24.sp,
            color = when {
                message.startsWith("CORRECT") -> Color.Green
                message.startsWith("WRONG") -> Color.Red
                else -> Color.Unspecified
            }
        )
        Text(text = "Guess The Flag")
        Text(text = correctFlag.name, fontSize = 20.sp)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            randomFlags.forEach { flag ->
                Image(
                    painter = painterResource(id = flag.imageResource),
                    contentDescription = "Flag image",
                    modifier = Modifier
                        .size(100.dp)
                        .clickable {
                            if (!showNext) {
                                selectedFlag = flag
                                message = if (flag == correctFlag) {
                                    "CORRECT! Correct country: ${correctFlag.name}"
                                } else {
                                    "WRONG! Correct country: ${correctFlag.name}"
                                }
                                showNext = true
                            }
                        }
                )
            }
        }

        Button(onClick = {
            if (showNext) {
                randomFlags = countries.shuffled().take(3)
                correctFlag = randomFlags.random()
                selectedFlag = null
                message = ""
                showNext = false
            }
        }) {
            Text(text = "Next")
        }
    }
}