package com.xenia.apptosupportpatientswithocd.presentation.modules_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.xenia.apptosupportpatientswithocd.data.modules_data.modulesList
import com.xenia.apptosupportpatientswithocd.presentation.modules_screen.model.ModuleContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModulesScreen(
    onModuleImageClickListener: (List<ModuleContent>) -> Unit
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
                    Text(text = "Модули")
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    titleContentColor = Color.White,
                    containerColor = Color(0xFF101018)
                )
            )
        }
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(top = contentPadding.calculateTopPadding()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(modulesList) {
                Text(
                    modifier = Modifier.padding(vertical = 5.dp),
                    color = Color.Black,
                    fontSize = 20.sp,
                    text = it.name,
                    textAlign = TextAlign.Center
                )

                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp)
                        .height(200.dp)
                        .clip(shape = RoundedCornerShape(10.dp))
                        .clickable {
                            onModuleImageClickListener(it.contentList)
                        },
                    painter = painterResource(id = it.image),
                    contentDescription = "Картинка на главном экране",
                    contentScale = ContentScale.FillWidth
                )
            }
        }
    }
}