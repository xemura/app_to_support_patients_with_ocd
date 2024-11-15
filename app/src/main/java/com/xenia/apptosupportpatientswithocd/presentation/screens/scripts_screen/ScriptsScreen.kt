package com.xenia.apptosupportpatientswithocd.presentation.screens.scripts_screen

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.xenia.apptosupportpatientswithocd.R
import com.xenia.apptosupportpatientswithocd.domain.entity.ScriptModel
import com.xenia.apptosupportpatientswithocd.presentation.composable.CardScript
import com.xenia.apptosupportpatientswithocd.presentation.composable.topbar.TopBarWithoutArrowBack
import com.xenia.apptosupportpatientswithocd.presentation.getApplicationComponent
import com.xenia.apptosupportpatientswithocd.presentation.states.ScriptsScreenState
import com.xenia.apptosupportpatientswithocd.presentation.viewmodels.ScriptViewModel

@Composable
fun ScriptsScreenStateContent(
    onFloatingActionButtonClick: () -> Unit
) {
    val component = getApplicationComponent()
    val scriptViewModel: ScriptViewModel = viewModel(factory = component.getViewModelFactory())
    val screenState = scriptViewModel.screenState.collectAsState(ScriptsScreenState.Initial)

    when (val currentState = screenState.value) {
        is ScriptsScreenState.Scripts -> {
            ScriptsScreen(
                currentState.scriptsList,
                { onFloatingActionButtonClick() },
                onCardClicked = { id, name, state ->
                    scriptViewModel.changeDropDownBoxState(id, name, state)
                },
                onCheckBoxClicked = { idAction, actionText, checkBoxState, scriptID ->
                    scriptViewModel.changeCheckBoxState(
                        idAction,
                        actionText,
                        checkBoxState,
                        scriptID
                    )
                },
                onDeleteItem = { script ->
                    scriptViewModel.deleteScript(script)
                }
            )
        }

        ScriptsScreenState.Initial -> {

        }

        ScriptsScreenState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.Black)
            }
        }

        else -> {}
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScriptsScreen(
    scriptsList: List<ScriptModel>?,
    onFloatingActionButtonClick: () -> Unit,
    onCardClicked: (String, String, Boolean) -> Unit,
    onCheckBoxClicked: (String, String, Boolean, String) -> Unit,
    onDeleteItem: (ScriptModel) -> Unit,
) {

    var currentItem by remember {
        mutableStateOf(ScriptModel("", "", false, emptyList()))
    }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopBarWithoutArrowBack(topBarName = stringResource(R.string.rituals))
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(bottom = 80.dp),
                onClick = {
                    onFloatingActionButtonClick()
                },
                shape = CircleShape,
                containerColor = Color(0xFF0575e6),
                contentColor = Color.White
            ) {
                Icon(Icons.Filled.Add, stringResource(R.string.floating_action_button))
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(
                    top = contentPadding.calculateTopPadding(),
                    bottom = contentPadding.calculateTopPadding() + 30.dp
                ),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!scriptsList.isNullOrEmpty()) {
                items(scriptsList) { script ->
                    val dismissState = rememberSwipeToDismissBoxState(
                        confirmValueChange = {
                            if (it == SwipeToDismissBoxValue.EndToStart) {
                                currentItem = script
                                true
                            } else false
                        },
                        positionalThreshold = { 150.dp.value }
                    )

                    SwipeToDismissBox(
                        modifier = Modifier
                            .animateContentSize()
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 5.dp),
                        state = dismissState,
                        enableDismissFromStartToEnd = false,
                        backgroundContent = {
                            val color = Color(0xFFFF1744)
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.End
                            ) {
                                Icon(
                                    Icons.Default.Delete,
                                    modifier = Modifier
                                        .minimumInteractiveComponentSize(),
                                    contentDescription = stringResource(R.string.delete),
                                    tint = Color.White
                                )
                            }
                        }
                    ) {
                        CardScript(
                            onCardClicked = { id, name, state ->
                                onCardClicked(id, name, state)
                            },
                            scriptModel = script,
                            onCheckBoxClicked = { idAction, actionText, checkBoxState, scriptID ->
                                onCheckBoxClicked(idAction, actionText, checkBoxState, scriptID)
                            }
                        )
                    }

                    if (dismissState.currentValue == SwipeToDismissBoxValue.EndToStart) {
                        LaunchedEffect(dismissState) {
                            dismissState.snapTo(SwipeToDismissBoxValue.Settled)
                            onDeleteItem(currentItem)
                            Toast.makeText(context,
                                context.getString(R.string.script_delete), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                item {
                    Text(
                        modifier = Modifier.padding(horizontal = 30.dp, vertical = 5.dp),
                        text = stringResource(R.string.empty_list_scripts),
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}