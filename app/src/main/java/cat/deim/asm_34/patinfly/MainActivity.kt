package cat.deim.asm_34.patinfly

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cat.deim.asm_34.patinfly.data.datasource.database.AppDatabase
import cat.deim.asm_34.patinfly.data.datasource.local.*
import cat.deim.asm_34.patinfly.data.repository.*
import cat.deim.asm_34.patinfly.domain.models.*
import cat.deim.asm_34.patinfly.domain.repository.*
import cat.deim.asm_34.patinfly.ui.theme.PatinflyTheme
import kotlinx.coroutines.runBlocking
import java.util.*

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PatinflyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
                }
            }
        }
    }
}


@Composable
fun Greeting() {
    Text(text = "¡Hola, Patinfly!")
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PatinflyTheme { Greeting() }
}