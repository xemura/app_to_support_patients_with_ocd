package com.xenia.apptosupportpatientswithocd.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xenia.apptosupportpatientswithocd.domain.entity.Action
import com.xenia.apptosupportpatientswithocd.domain.entity.ScriptModel
import com.xenia.apptosupportpatientswithocd.domain.usecases.scripts_usecases.AddScriptUseCase
import com.xenia.apptosupportpatientswithocd.domain.usecases.scripts_usecases.ChangeCheckBoxStateInActionUseCase
import com.xenia.apptosupportpatientswithocd.domain.usecases.scripts_usecases.ChangeDropDownBoxStateUseCase
import com.xenia.apptosupportpatientswithocd.domain.usecases.scripts_usecases.DeleteScriptUseCase
import com.xenia.apptosupportpatientswithocd.domain.usecases.scripts_usecases.GetScriptsUseCase
import com.xenia.apptosupportpatientswithocd.presentation.states.ScriptsScreenState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class ScriptViewModel @Inject constructor(
    private val getScriptsUseCase: GetScriptsUseCase,
    private val changeDropDownBoxStateUseCase: ChangeDropDownBoxStateUseCase,
    private val changeCheckBoxStateInActionUseCase: ChangeCheckBoxStateInActionUseCase,
    private val addScriptUseCase: AddScriptUseCase,
    private val deleteScriptUseCase: DeleteScriptUseCase
) : ViewModel() {
    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    private val scriptsFlow = getScriptsUseCase()

    val screenState = scriptsFlow
        .map { ScriptsScreenState.Scripts(scriptsList = it) as ScriptsScreenState }
        .onStart {
            emit(ScriptsScreenState.Loading)
        }.stateIn(
            scope = coroutineScope,
            started = SharingStarted.Lazily,
            initialValue = ScriptsScreenState.Initial
        )

    fun changeDropDownBoxState(id: String, name: String, state: Boolean) {
        viewModelScope.launch {
            changeDropDownBoxStateUseCase(id, name, state)
        }
    }

    fun addScript(scriptName: String, listActions: List<Action>) {
        viewModelScope.launch {
            addScriptUseCase(scriptName, listActions)
        }
    }

    fun deleteScript(script: ScriptModel) {
        viewModelScope.launch {
            deleteScriptUseCase(script)
        }
    }

    fun changeCheckBoxState(
        idAction: String, actionText: String,
        checkBoxState: Boolean, scriptID: String
    ) {
        viewModelScope.launch {
            changeCheckBoxStateInActionUseCase(
                idAction, actionText, checkBoxState, scriptID
            )
        }
    }
}