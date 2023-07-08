package com.besho.authentication.login.presentation.screen.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.besho.authentication.login.domain.model.LoginResult
import com.besho.authentication.login.domain.model.LoginValidationState
import com.besho.authentication.login.domain.usecase.LoginUseCase
import com.besho.authentication.login.domain.usecase.LoginValidationsUseCase
import com.besho.authentication.login.presentation.screen.login.model.event.LoginEvent
import com.besho.authentication.login.presentation.screen.login.model.state.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val loginValidationsUseCase: LoginValidationsUseCase,
) :
    ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    private val _result = MutableSharedFlow<LoginResult>()
    val result: SharedFlow<LoginResult> = _result.asSharedFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.Login -> {
                onLoginEvent()
            }

            is LoginEvent.UpdateUserName -> {
                _state.value = state.value.copy(
                    username = event.userName,
                    isUserNameInvalid = false
                )
            }

            is LoginEvent.UpdatePassword -> {
                _state.value = state.value.copy(
                    password = event.password,
                    isPasswordInvalid = false
                )
            }
        }
    }

    private fun onLoginEvent() {
        viewModelScope.launch {
            val loginValidationState = loginValidationsUseCase(
                state.value.username,
                state.value.password
            )

            if (!loginValidationState.invalidUserName &&
                !loginValidationState.invalidPassword
            )
                handleLogin()
            else
                handleValidationResult(loginValidationState)
        }
    }

    private fun handleValidationResult(loginValidationState: LoginValidationState) {
        if (loginValidationState.invalidUserName &&
            loginValidationState.invalidPassword
        )
            _state.value = state.value.copy(
                isUserNameInvalid = true,
                isPasswordInvalid = true
            )
        else if (loginValidationState.invalidUserName)
            _state.value = state.value.copy(
                isUserNameInvalid = true,
            )
        else
            _state.value = state.value.copy(
                isPasswordInvalid = true
            )
    }

    private suspend fun handleLogin() {
        _state.value = state.value.copy(
            isLoading = true
        )
        val result = loginUseCase.invoke(
            state.value.username,
            state.value.password
        )
        when (result) {
            is LoginResult.OnError -> {
                _result.emit(
                    LoginResult.OnError(
                        result.errorMessage,
                    )
                )
                _state.value = state.value.copy(
                    isLoading = false
                )
            }

            is LoginResult.OnSuccess -> {
                _result.emit(LoginResult.OnSuccess)
            }
        }
    }
}