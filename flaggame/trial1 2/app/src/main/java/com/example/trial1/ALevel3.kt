package com.example.trial1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trial1.ui.theme.Trial1Theme

class ALevel3 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ///A3GUI()
            AdvancedLevel()

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun A3GUI() {
    val random1 = randomCountry()
    val random2 = randomCountry()
    val random3 = randomCountry()
    var txt1 by remember{ mutableStateOf("") }
    var txt2 by remember{ mutableStateOf("") }
    var txt3 by remember{ mutableStateOf("") }




    Column(modifier = Modifier
        .background(Color.Yellow)
        .fillMaxSize(1f),
        //horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Row(modifier = Modifier
            .background(Color.Black)
            .fillMaxWidth()
            .height(200.dp)
      ) {
            Image(painterResource(id = random1.imageResource), contentDescription ="flag", modifier = Modifier.fillMaxSize(0.5f))
            TextField(value = txt1, onValueChange ={
                txt1 = it
            } )
        }
        Row(modifier = Modifier
            .background(Color.Red)
            .fillMaxWidth()
            .height(200.dp)
        ) {
            Image(painterResource(id = random2.imageResource), contentDescription ="flags", modifier = Modifier.fillMaxSize(0.5f))
            TextField(value = txt2, onValueChange ={
                txt2 = it
            } )
        }
        Row(modifier = Modifier
            .background(Color.Green)
            .fillMaxWidth()
            .height(200.dp)
        ) {
            Image(painterResource(id = random3.imageResource), contentDescription ="flags", modifier = Modifier.fillMaxSize(0.5f))
            TextField(value = txt3, onValueChange ={
                txt3 = it
            } )
        }
        Row(modifier = Modifier
            .background(Color.Blue)
            .fillMaxWidth()
            .height(200.dp)
        ) {
            Image(painterResource(id = R.drawable.lc), contentDescription ="flags", modifier = Modifier.fillMaxSize(0.5f))
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Submit")
            }
        }

    }
}

@Preview
@Composable
fun AdvancedLevel() {
    val countries = Flag.shuffled() // Shuffling the full list of countries
    var randomFlags by remember { mutableStateOf(countries.take(3)) }
    var answers by remember { mutableStateOf(listOf("", "", "")) }
    var isCorrect by remember { mutableStateOf(listOf(false, false, false)) }
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
                            if (!isCorrect[index]) {
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
                randomFlags = countries.shuffled().take(3)
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