package com.xenia.apptosupportpatientswithocd.presentation.modules_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xenia.apptosupportpatientswithocd.domain.entity.ModuleModel
import com.xenia.apptosupportpatientswithocd.domain.usecases.modules_usecases.GetModulesListUseCase
import com.xenia.apptosupportpatientswithocd.presentation.profile_screen.ProfileScreenState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class ModulesViewModel @Inject constructor(
    getModulesListUseCase: GetModulesListUseCase,
) : ViewModel() {

    private val modulesList = getModulesListUseCase()

    fun getModulesList(): List<ModuleModel> {
        return modulesList
    }
}