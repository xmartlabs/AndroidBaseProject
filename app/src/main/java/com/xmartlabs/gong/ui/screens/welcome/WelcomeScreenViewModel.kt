package com.xmartlabs.gong.ui.screens.welcome

import androidx.lifecycle.viewModelScope
import com.xmartlabs.gong.data.model.Project
import com.xmartlabs.gong.data.model.User
import com.xmartlabs.gong.device.common.ProcessState
import com.xmartlabs.gong.device.common.getDataOrNull
import com.xmartlabs.gong.device.extensions.mapToProcessResult
import com.xmartlabs.gong.domain.usecase.GetProjectsUseCase
import com.xmartlabs.gong.domain.usecase.LoadUserUseCase
import com.xmartlabs.gong.ui.common.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Created by mirland on 25/04/20.
 */
class WelcomeScreenViewModel(
    getProjectsUseCase: GetProjectsUseCase,
    loadUserUseCase: LoadUserUseCase,
) : BaseViewModel<WelcomeUiAction, WelcomeViewModelEvent, WelcomeViewState>(WelcomeViewState()) {
    init {
        viewModelScope.launch {
            getProjectsUseCase(Unit)
                .mapToProcessResult()
                .collect { updateLocation(it) }
        }
        viewModelScope.launch {
            loadUserUseCase.invokeAsFlow(Unit)
                .collect { updateUserInfo(it) }
        }
    }

    private suspend fun updateUserInfo(userProcessState: ProcessState<User?>) {
//        userProcessState.getDataOrNull()?.let { user ->
//            setState { copy(userName = user.name) }
//        }
    }

    private suspend fun updateLocation(projectProcessState: ProcessState<List<Project>>) {
        projectProcessState.getDataOrNull()?.let { projects->

            Timber.e(projects.toString())
            setState { copy(projects = projects) }
        }
    }

    override suspend fun processAction(action: WelcomeUiAction) {}
}
