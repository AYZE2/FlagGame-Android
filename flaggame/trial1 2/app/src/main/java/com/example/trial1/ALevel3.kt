package com.example.trial1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

class ALevel3 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AdvancedLevel()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun AdvancedLevel() {
    // Only the flags' drawable resource ids (Ints) are saved across
    // rotation/process death; Country objects are derived from them so
    // state survives configuration changes.
    var flagOptions by rememberSaveable {
        mutableStateOf(Flag.shuffled().take(3).map { it.imageResource })
    }
    val randomFlags = remember(flagOptions) {
        flagOptions.map { id -> Flag.first { it.imageResource == id } }
    }
    var answers by rememberSaveable { mutableStateOf(listOf("", "", "")) }
    var isCorrect by rememberSaveable { mutableStateOf(listOf(false, false, false)) }
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
            message = "WRONG! Time's up - Correct countries: ${randomFlags.joinToString(", ") { it.name }}"
            isCorrect = randomFlags.map { false }
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
        Text(text = "Guess The Flags")

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            randomFlags.forEachIndexed { index, flag ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = flag.imageResource),
                        contentDescription = "Flag image",
                        modifier = Modifier.size(100.dp)
                    )
                    BasicTextField(
                        value = answers[index],
                        onValueChange = {
                            if (!isCorrect[index] && !showNext) {
                                answers = answers.toMutableList().apply { set(index, it) }
                            }
                        },
                        modifier = Modifier
                            .background(
                                color = if (isCorrect[index]) Color.Green else Color.Transparent,
                                shape = RectangleShape
                            )
                            .padding(8.dp)
                            .fillMaxWidth()
                    ) { innerTextField ->
                        if (answers[index].isEmpty()) {
                            Text(
                                text = "Type here...",
                                color = Color.Gray,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                        innerTextField()
                    }
                }
            }
        }

        Button(onClick = {
            if (showNext) {
                flagOptions = Flag.shuffled().take(3).map { it.imageResource }
                answers = listOf("", "", "")
                isCorrect = listOf(false, false, false)
                message = ""
                showNext = false
            } else {
                var allCorrect = true
                val newIsCorrect = isCorrect.toMutableList()
                randomFlags.forEachIndexed { index, flag ->
                    if (answers[index].equals(flag.name, ignoreCase = true)) {
                        newIsCorrect[index] = true
                    } else {
                        newIsCorrect[index] = false
                        allCorrect = false
                    }
                }
                isCorrect = newIsCorrect
                message = if (allCorrect) {
                    showNext = true
                    "CORRECT!"
                } else {
                    "WRONG! Correct countries: ${randomFlags.joinToString(", ") { it.name }}"
                }
            }
        }) {
            Text(text = if (showNext) "Next" else "Submit")
        }
    }
}
