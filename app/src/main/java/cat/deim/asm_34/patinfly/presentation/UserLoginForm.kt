package cat.deim.asm.myapplication.presentation

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cat.deim.asm.myapplication.datasource.UserDataSource
import cat.deim.asm.myapplication.repository.UserRepository
import cat.deim.asm_34.patinfly.domain.usecase.LoginUsecase

@Composable
fun UserLoginForm(loginUsecase: LoginUsecase?) {
    Surface {
        Column(modifier = Modifier.width(300.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally){
            var credentials by remember { mutableStateOf(Credentials()) }
            val context = LocalContext.current
            Row(modifier = Modifier.padding(vertical = 8.dp)) {
                TextField(value =credentials.email,
                    label = { Text(text = "Username") },
                    onValueChange = {
                            data -> credentials = credentials.copy(email = data)
                    },)
            }
            Row(modifier = Modifier.padding(vertical = 8.dp)) {
                TextField(value = credentials.password,
                    label = { Text(text = "Password") },
                    onValueChange = {
                            data -> credentials = credentials.copy(password = data)
                    },)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally){
                Row {
                    Button(
                        modifier = Modifier.width(200.dp),
                        content = { Text(text = "Login") },
                        enabled = credentials.isNotEmpty(),
                        onClick = { if(loginUsecase!!.execute(credentials)){
                            val intent: Intent = Intent()
                            intent.setClass(context, MainActivity::class.java)
                            context.startActivity(intent)
                        }
                        })
                }
                Row {
                    Button( modifier = Modifier.width(200.dp),
                        content = { Text(text = "Register") },
                        onClick = { /*TODO*/ })
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun UserLoginFormPreview() {
    UserLoginForm(
        LoginUsecase((UserRepository(UserDataSource.getInstance(LocalContext.current))))
    )
}