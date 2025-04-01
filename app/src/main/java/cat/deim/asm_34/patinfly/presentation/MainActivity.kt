package cat.deim.asm.myapplication.presentation

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
import cat.deim.asm.myapplication.datasource.UserDataSource
import cat.deim.asm.myapplication.presentation.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

    private val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
        Log.d(TAG, "onCreate ejecutado")
    }




    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart ejecutado")

        // Cargar el usuario desde JSON
        val userDataSource = UserDataSource.getInstance(this)
        val user = userDataSource.getByEmail("tomas.gonzalez@urv.cat")

        if (user != null) {
            Log.d(TAG, "Usuario cargado desde JSON: ${user.username}")
        } else {
            Log.d(TAG, "No se ha podido cargar el usuario desde JSON")
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume ejecutado")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause ejecutado")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop ejecutado")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy ejecutado")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}
