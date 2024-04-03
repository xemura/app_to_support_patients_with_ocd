package com.xenia.apptosupportpatientswithocd.presentation.main_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xenia.apptosupportpatientswithocd.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    contentPadding: PaddingValues,
    onTherapyButtonPressed: () -> Unit,
    onModulesButtonPressed: () -> Unit,
    onScriptsButtonPressed: () -> Unit,
) {

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier
                    .shadow(
                        elevation = 5.dp,
                        spotColor = Color.DarkGray
                    ),
                title = {
                    Text(text = "Главная")
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    titleContentColor = Color.White,
                    containerColor = Color(0xFF101018)
                ))
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(top = contentPadding.calculateTopPadding()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 30.dp, vertical = 10.dp)
                    .height(300.dp)
                    .clip(shape = RoundedCornerShape(10.dp)),
                painter = painterResource(id = R.drawable.main_screen_1),
                contentDescription = "Картинка на главном экране",
                contentScale = ContentScale.FillWidth
            )

            Text(
                modifier = Modifier.padding(vertical = 5.dp),
                color = Color.Black,
                fontSize = 20.sp,
                text = "Привет, ты на \nправильном пути!",
                textAlign = TextAlign.Center
            )

            Button(
                onClick = { onTherapyButtonPressed() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0575e6)),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
            ) {
                Text(
                    color = Color.White,
                    text = "Терапия",
                )
            }

            Button(
                onClick = { onModulesButtonPressed() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0575e6)),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
            ) {
                Text(
                    color = Color.White,
                    text = "Модули",
                )
            }

            Button(
                onClick = { onScriptsButtonPressed() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0575e6)),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
            ) {
                Text(
                    color = Color.White,
                    text = "Ритуалы",
                )
            }

        }
    }
}