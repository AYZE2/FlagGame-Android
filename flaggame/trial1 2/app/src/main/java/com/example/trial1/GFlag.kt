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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import kotlinx.coroutines.delay

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
    // Only the flags' drawable resource ids (Ints) are saved across
    // rotation/process death; Country objects are derived from them so
    // state survives configuration changes.
    var flagOptions by rememberSaveable {
        mutableStateOf(Flag.shuffled().take(3).map { it.imageResource })
    }
    var correctFlag by rememberSaveable { mutableStateOf(flagOptions.random()) }
    val randomFlags = remember(flagOptions) {
        flagOptions.map { id -> Flag.first { it.imageResource == id } }
    }
    val correctCountry = remember(correctFlag) {
        Flag.first { it.imageResource == correctFlag }
    }
    var message by rememberSaveable { mutableStateOf("") }
    var showNext by rememberSaveable { mutableStateOf(false) }
    var timeLeft by rememberSaveable { mutableStateOf(ROUND_TIME_SECONDS) }

    // Countdown timer for the current round. Restarts whenever a new set of
    // flags is shown, and stops as soon as an answer has been submitted.
    LaunchedEffect(flagOptions, showNext) {
        if (!showNext) {
            timeLeft = ROUND_TIME_SECONDS
            while (timeLeft > 0) {
                delay(1000)
                timeLeft--
            }
            message = "WRONG! Time's up - Correct country: ${correctCountry.name}"
            showNext = true
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Time left: $timeLeft s",
            fontSize = 20.sp,
            color = if (timeLeft <= 5 && !showNext) Color.Red else Color.Unspecified
        )
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
        Text(text = correctCountry.name, fontSize = 20.sp)

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
                        .clickable(enabled = !showNext) {
                            message = if (flag == correctCountry) {
                                "CORRECT! Correct country: ${correctCountry.name}"
                            } else {
                                "WRONG! Correct country: ${correctCountry.name}"
                            }
                            showNext = true
                        }
                )
            }
        }

        Button(onClick = {
            if (showNext) {
                flagOptions = Flag.shuffled().take(3).map { it.imageResource }
                correctFlag = flagOptions.random()
                message = ""
                showNext = false
            }
        }) {
            Text(text = "Next")
        }
    }
}
