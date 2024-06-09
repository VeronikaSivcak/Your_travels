package com.example.yourtravels

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.yourtravels.add_screens.InfoTravelScreen
import com.example.yourtravels.start.YourTravelsApp
import com.example.yourtravels.ui.theme.YourTravelsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YourTravelsTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                        //HomeScreen()
                    //InputForNewTravel()
                    //NewTravelScreen()
                    YourTravelsApp()
                    //InfoTravelScreen()
                    }
                }
            }
        }
    }


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

        Text(
            text = "Hellou $name!",
            modifier = modifier
        )

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    YourTravelsTheme {
        Greeting("Android")
    }
}