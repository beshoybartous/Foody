package com.besho.authentication.login.presentation.screen.register.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RegisterFieldsUIState(
    var userName: String = "",
    var email: String = "",
    var password: String = "",
    var confirmPassword: String = "",
    var image: String = ""
)

sealed class RegisterUIEvent {
    data class UpdateEmail(val email: String) : RegisterUIEvent()
    data class UpdateUserName(val userName: String) : RegisterUIEvent()
    data class UpdatePassword(val password: String) : RegisterUIEvent()
    data class UpdateConfirmPassword(
        val confirmPassword: String
    ) : RegisterUIEvent()

    data class UpdateImage(
        val image: String
    ) : RegisterUIEvent()

    object Register : RegisterUIEvent()
}

sealed class RegisterUIState {
    data class Init(
        val registerFieldsUIState: RegisterFieldsUIState
    ) : RegisterUIState()

    object Loading : RegisterUIState()
    data class Error(
        val registerFieldsUIState: RegisterFieldsUIState
    ) : RegisterUIState()

    object Register : RegisterUIState()
}

@HiltViewModel
class RegisterViewModel @Inject constructor() : ViewModel() {

    val registerFieldsUIState by lazy { RegisterFieldsUIState() }

    val registerUIState: MutableStateFlow<RegisterUIState> =
        MutableStateFlow(RegisterUIState.Init(registerFieldsUIState))

    fun onRegisterEvent(registerUIEvent: RegisterUIEvent) {
        when (registerUIEvent) {
            is RegisterUIEvent.Register -> {
                viewModelScope.launch {
                    registerUIState.emit(RegisterUIState.Loading)
                    delay(3000)
                    registerUIState.emit(
                        RegisterUIState.Error(registerFieldsUIState)
                    )
                }
            }

            is RegisterUIEvent.UpdateUserName -> {
                updateUserName(registerUIEvent.userName)
            }

            is RegisterUIEvent.UpdatePassword -> {
                updatePassword(registerUIEvent.password)
            }

            is RegisterUIEvent.UpdateEmail -> {
                updateEmail(registerUIEvent.email)
            }

            is RegisterUIEvent.UpdateConfirmPassword -> {
                updateConfirmPassword(registerUIEvent.confirmPassword)
            }

            is RegisterUIEvent.UpdateImage -> {
                updateImage(registerUIEvent.image)
            }
        }
    }

    private fun updateUserName(userName: String) {
        registerFieldsUIState.userName = userName
    }

    private fun updatePassword(password: String) {
        registerFieldsUIState.password = password
    }

    private fun updateEmail(email: String) {
        registerFieldsUIState.email = email
    }

    private fun updateConfirmPassword(confirmPassword: String) {
        registerFieldsUIState.confirmPassword = confirmPassword
    }

    private fun updateImage(image: String) {
        registerFieldsUIState.image = image
    }

}