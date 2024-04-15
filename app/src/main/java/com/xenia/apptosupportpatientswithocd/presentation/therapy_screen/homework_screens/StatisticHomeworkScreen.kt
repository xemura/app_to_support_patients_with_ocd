package com.xenia.apptosupportpatientswithocd.presentation.therapy_screen.homework_screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.xenia.apptosupportpatientswithocd.domain.entity.HomeworkModel
import com.xenia.apptosupportpatientswithocd.presentation.composable.BarGraph
import com.xenia.apptosupportpatientswithocd.presentation.composable.BarType
import com.xenia.apptosupportpatientswithocd.presentation.getApplicationComponent
import com.xenia.apptosupportpatientswithocd.presentation.therapy_screen.practice_screens.StatisticHomeworkViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticHomeworkScreen(
    homework: HomeworkModel,
    onBackPressed: () -> Unit
) {

    val component = getApplicationComponent()
    val statisticHomeworkViewModel: StatisticHomeworkViewModel =
        viewModel(factory = component.getViewModelFactory())

    val statisticList = statisticHomeworkViewModel.statistics.collectAsState()

    statisticHomeworkViewModel.getStatisticByID(homework.id)

    Log.d("TAG", "StatisticHomeworkScreen ${statisticList.toString()}")

    val context = LocalContext.current

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier
                    .shadow(
                        elevation = 5.dp,
                        spotColor = Color.DarkGray
                    ),
                title = {
                    Text(text = "Статистика")
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    titleContentColor = Color.White,
                    containerColor = Color(0xFF101018)
                ),
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                },
            )
        }
    ) { contentPadding ->
        Box(
            modifier = Modifier.padding(top = contentPadding.calculateTopPadding() + 5.dp)
        ) {
            Column {
                Text(
                    modifier = Modifier.padding(horizontal = 30.dp, vertical = 5.dp),
                    text = "Статистика по домашней работе: ${homework.obsessionInfo}"
                )

                val dataList = mutableListOf<Int>()
                val valueNumberList = mutableListOf<String>()

                if (!statisticList.value.isNullOrEmpty()) {
                    statisticList.value!!.forEach {
                        dataList.add(it.statisticAfterPractice)
                        valueNumberList.add(it.statisticAfterPractice.toString())
                    }

                    val floatValue = mutableListOf<Float>()

                    dataList.forEachIndexed { index, value ->
                        floatValue.add(
                            index = index,
                            element = value.toFloat() / dataList.max().toFloat()
                        )
                    }

                    Log.d("TAG", floatValue.toString())

                    BarGraph(
                        graphBarData = floatValue,
                        xAxisScaleData = valueNumberList,
                        barData_ = dataList,
                        height = 300.dp,
                        roundType = BarType.TOP_CURVED,
                        barWidth = 20.dp,
                        barBrush = Brush.verticalGradient(
                            listOf(
                                Color(0xFF0575e6),
                                Color(0xFFb5e2fa)
                            )
                        ),
                        barArrangement = Arrangement.SpaceEvenly
                    )

                    Text(
                        modifier = Modifier.padding(horizontal = 30.dp, vertical = 5.dp),
                        text = "График показывает оценку неприятного ощущения на обсессию после прохождения данного домашнего задания",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )

                } else {
                    Text(
                        modifier = Modifier.padding(horizontal = 30.dp, vertical = 5.dp),
                        text = "Выполните хотя бы одну домашнюю работу для отображения графика!",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }


            }
        }
    }
}