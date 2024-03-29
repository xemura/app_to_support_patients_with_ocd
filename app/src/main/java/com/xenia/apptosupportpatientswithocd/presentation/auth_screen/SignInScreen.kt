package com.xenia.apptosupportpatientswithocd.presentation.auth_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.xenia.apptosupportpatientswithocd.navigation.NavigationItem
import com.xenia.apptosupportpatientswithocd.navigation.rememberNavigationState
import com.xenia.apptosupportpatientswithocd.presentation.composable.LoginField
import com.xenia.apptosupportpatientswithocd.presentation.composable.PasswordField

@Preview
@Composable
fun LoginScreen() {
    var loginText by remember { mutableStateOf("") }
    var passwordText by remember { mutableStateOf("") }

    val navigationState = rememberNavigationState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Добро пожаловать!")

        LoginField(
            value = loginText,
            onValueChange = {
                loginText = it
            }
        )

        PasswordField(
            value = passwordText,
            placeholder = "Введите пароль",
            onValueChange = {
                passwordText = it
            }
        )

        Button(onClick = {
            navigationState.navigateTo(NavigationItem.Main.route)
        }) {
            Text(text = "Войти")
        }

        Text(text = "Нет аккаунта? Зарегистрироваться")
    }
}