package com.example.trial1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.trial1.ui.theme.Trial1Theme

class ALevels : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            aLevelGUI()
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun aLevelGUI(){
    var ttf by remember{mutableStateOf("")}

    Column() {
        Row() {
            Column() {

            Button(onClick = {
            },
                shape = RectangleShape
            )
            {
                Image(painterResource(id = R.drawable.la), contentDescription ="flag" )
            }
            TextField(value = ttf, onValueChange ={
                ttf = it
            } )
            Text(text = "hi")
            }
            Column() {
            Button(onClick = {
            },
                shape = RectangleShape
            )
            {
                Image(painterResource(id = R.drawable.pa), contentDescription ="flag" )
            }
            Text(text = "jigle")}
            Column() {

            Button(onClick = {
            },
                shape = RectangleShape
            )
            {
                Image(painterResource(id = R.drawable.na), contentDescription ="flag" )
            }
            Text(text = "ki")}
        }

        Button(onClick = { /*TODO*/ }) {
            Text(text = "Next")
        }
    }


}