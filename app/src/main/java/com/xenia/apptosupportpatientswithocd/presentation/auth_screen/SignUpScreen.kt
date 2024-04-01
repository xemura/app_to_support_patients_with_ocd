package com.xenia.apptosupportpatientswithocd.presentation.auth_screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xenia.apptosupportpatientswithocd.navigation.rememberNavigationState
import com.xenia.apptosupportpatientswithocd.presentation.composable.LoginField
import com.xenia.apptosupportpatientswithocd.presentation.composable.PasswordField
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    viewModel: AuthViewModel
) {
    var loginText by remember { mutableStateOf("") }
    var passwordText by remember { mutableStateOf("") }
    var repeatPasswordText by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val navigationState = rememberNavigationState()

    val state = viewModel.signUpState.collectAsState(initial = null)

    var screenLogin by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (screenLogin) {
            SignInScreen(viewModel = viewModel)
        } else {
            Text(
                text = "Добро пожаловать!",
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.padding(vertical = 5.dp))

            LoginField(
                value = loginText,
                onValueChange = {
                    loginText = it
                })

            Spacer(modifier = Modifier.padding(vertical = 5.dp))

            PasswordField(value = passwordText,
                "Введите пароль",
                onValueChange = {
                    passwordText = it
                })

            Spacer(modifier = Modifier.padding(vertical = 5.dp))

            PasswordField(value = repeatPasswordText,
                "Повторите пароль",
                onValueChange = {
                    repeatPasswordText = it
                })

            Spacer(modifier = Modifier.padding(vertical = 5.dp))

            Button(onClick = {
                viewModel.registerUser(loginText, passwordText)
            }) {
                Text(text = "Зарегистрироваться")
            }

            Spacer(modifier = Modifier.padding(vertical = 5.dp))

            Text(
                modifier = Modifier.clickable {
                    screenLogin = true
                }
                    .alpha(0.7f),
                color = Color.Red,
                text = "Есть аккаунта? Войти"
            )
        }
    }

    LaunchedEffect(key1 = state.value?.isSuccess) {
        scope.launch {
            if (state.value?.isSuccess?.isNotEmpty() == true) {
                val success = state.value?.isSuccess
                Toast.makeText(context, "$success", Toast.LENGTH_SHORT).show()
            }
        }
    }

    LaunchedEffect(key1 = state.value?.isError) {
        scope.launch {
            if (state.value?.isError?.isNotEmpty() == true) {
                val error = state.value?.isError
                Toast.makeText(context, "$error", Toast.LENGTH_SHORT).show()
            }
        }

    }
}