package com.example.trial1

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.trial1.ui.theme.Trial1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            displayGUI("hi")
        }
    }
}
@Composable
fun displayGUI(displayMSG: String){
    Column() {
        val context = LocalContext.current
        Text(displayMSG)
        Button(onClick= {
            val i = Intent(context, GCountry::class.java)
            context.startActivity(i)
        })
        {
            Text(text = "Guess The County")
        }
        Button(onClick= {
            val i = Intent(context, GHints::class.java)
            context.startActivity(i)
        })
        {
            Text(text = "Guess-Hints")
        }
        Button(onClick= {
            val i = Intent(context, GFlag::class.java)
            context.startActivity(i)
        })
        {
            Text(text = "Guess The Flag")
        }
        Button(onClick= {
            val i = Intent(context, ALevel3::class.java)
            context.startActivity(i)
        })
        {
            Text(text = "Advanced Level")
        }
    }
}





@Preview
@Composable
fun displayGUIPreview(){
    displayGUI(displayMSG ="hi" )
}