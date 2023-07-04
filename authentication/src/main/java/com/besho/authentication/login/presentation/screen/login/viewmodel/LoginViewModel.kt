package com.besho.authentication.login.presentation.screen.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.besho.authentication.R
import com.besho.authentication.login.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginTextFieldUIState(
    var userName: String = "",
    var password: String = ""
)

sealed class LoginUIEvent {
    data class UpdateUserName(val userName: String) : LoginUIEvent()
    data class UpdatePassword(val password: String) : LoginUIEvent()
    object Login : LoginUIEvent()
}

sealed class LoginUIState {
    object Init : LoginUIState()
    object Loading : LoginUIState()
    data class Error(val loginTextFieldUIState: LoginTextFieldUIState) :
        LoginUIState()

    object Login : LoginUIState()
}

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) :
    ViewModel() {

    val loginTextFieldUIState by lazy { LoginTextFieldUIState() }

    val loginUIState: MutableStateFlow<LoginUIState> =
        MutableStateFlow(LoginUIState.Init)

    fun onLoginEvent(loginUIEvent: LoginUIEvent) {
        when (loginUIEvent) {
            is LoginUIEvent.Login -> {
                val loginValidation = validateCredentials(
                    loginTextFieldUIState.userName,
                    loginTextFieldUIState.password
                )
                if (loginValidation == LoginValidation.ValidCredentials)
                    viewModelScope.launch {
                        loginUseCase.invoke(
                            loginTextFieldUIState.userName,
                            loginTextFieldUIState.password
                        )
                    }
            }

            is LoginUIEvent.UpdateUserName -> {
                updateUserName(loginUIEvent.userName)
            }

            is LoginUIEvent.UpdatePassword -> {
                updatePassword(loginUIEvent.password)
            }
        }
    }

    private fun updateUserName(userName: String) {
        loginTextFieldUIState.userName = userName
    }

    private fun updatePassword(password: String) {
        loginTextFieldUIState.password = password
    }

    private fun validateCredentials(
        userName: String,
        password: String
    ): LoginValidation? {
        val emailRegex = Regex("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        val phoneNumber = Regex("^01\\d{9}$")
        val isEmailValid = emailRegex.matches(userName)
        val isPhoneNumberValid = phoneNumber.matches(userName)
        if (!isEmailValid && !isPhoneNumberValid) {
            return LoginValidation.InvalidUserNameOrPhoneNumber
        }

        if (password.length < 8) {
            return LoginValidation.InvalidUserNameOrPhoneNumber
        }
        return null
    }
}

sealed class LoginValidation(val errorMsg: Int) {
    object InvalidUserNameOrPhoneNumber :
        LoginValidation(errorMsg = R.string.login_invalid_username)

    object InvalidPassword :
        LoginValidation(errorMsg = R.string.login_invalid_username)

    object ValidCredentials : LoginValidation(-1)

}