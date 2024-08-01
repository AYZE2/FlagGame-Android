package com.example.trial1

import android.content.SharedPreferences
import android.media.Image
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ContentInfoCompat.Flags
import com.example.trial1.ui.theme.Trial1Theme
import kotlin.random.Random


var total = 0 //number being presented//
var correct = 0 // noofcorrect guesses//

class GCountry : ComponentActivity() {
    lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //create shared pref object
        prefs = getSharedPreferences("package com.example.trial1", MODE_PRIVATE)
        //restore data
        total = prefs.getInt("total",0)
        correct = prefs.getInt("correct",0)

        setContent {
            GUI()
        }
    }

    override fun onPause() {
        super.onPause()

        // create sharef pref and editor
        var editor = prefs.edit()

        //start saving
        editor.putInt("total", total)
        editor.putInt("correct", correct)

        editor.apply()


    }

}



fun nextRound2(previous_flag2: Int):Int{
    var findex = randomCountry().imageResource
    var flag_chosen = findex

    while (flag_chosen == previous_flag2){
        findex = randomCountry().imageResource
        flag_chosen = findex
    }
    return flag_chosen
}

fun randomCountry(): Country{
    return Flag.random()
}

data class Country(val name: String, val imageResource :Int)

val Flag:List<Country> = listOf(
    Country("Andorra", R.drawable.ad),
    Country("UAE", R.drawable.ae),
    Country("AFG", R.drawable.af),
    Country("Albania", R.drawable.al),
    Country("Armenia", R.drawable.am),
    Country("Angola", R.drawable.ao),
    Country("Argentina", R.drawable.ar),
    Country("Austria", R.drawable.at),
    Country("Australia", R.drawable.au),
    Country("Aruba", R.drawable.aw),
    Country("Bangladesh", R.drawable.bd),
    Country("Belgium", R.drawable.be),
    Country("Bulgria", R.drawable.bg),
    Country("Bahrain", R.drawable.bh),
    Country("Bermuda", R.drawable.bm),
    Country("Brazil", R.drawable.br),
    Country("Bahamas", R.drawable.bs),
    Country("Bhutan", R.drawable.bt),
    Country("Botswana", R.drawable.bw),
    Country("Belarus", R.drawable.by),
    Country("Canada", R.drawable.ca),
    Country("Switzerland", R.drawable.ch),
    Country("Chile", R.drawable.cl),
    Country("Cameroon", R.drawable.cm),
    Country("China", R.drawable.cn),
    Country("Colombia", R.drawable.co),
    Country("Costa Rica", R.drawable.cr),
    Country("Cuba", R.drawable.cu),
    Country("France", R.drawable.fr),
    Country("Spain", R.drawable.es),
    Country("Gabon", R.drawable.ga),
    Country("Egypt", R.drawable.eg),
)

@Preview
@Composable
fun GUI() {
    var randomCountry by remember { mutableStateOf(randomCountry()) }
    var flagChosen by remember { mutableStateOf(randomCountry.imageResource) }
    val countryChosen = randomCountry.name
    val cNames: List<String> = Flag.map { it.name }
    var selected by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var showNext by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Score: $correct / $total",
            fontSize = 32.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 10.dp),
            textAlign = TextAlign.End
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
        Text(text = "Guess The Country")
        Image(
            painter = painterResource(id = flagChosen),
            contentDescription = "Flag image",
            modifier = Modifier.size(100.dp)
        )
        LazyColumn(Modifier.height(400.dp)) {
            items(cNames) { country ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selected = country }
                        .padding(8.dp)
                ) {
                    Text(text = country, fontSize = 24.sp)
                }
            }
        }
        Text(text = "You Clicked: $selected")
        Button(onClick = {
            if (showNext) {
                randomCountry = randomCountry()
                flagChosen = randomCountry.imageResource
                selected = ""
                message = ""
                showNext = false
            } else {
                total++
                if (selected == countryChosen) {
                    correct++
                    message = "CORRECT! Correct country: $countryChosen"
                } else {
                    message = "WRONG! Correct country: $countryChosen"
                }
                showNext = true
            }
        }) {
            Text(text = if (showNext) "Next" else "Submit")
        }
    }
}