package com.besho.authentication.login.domain.usecase

class UserNameValidationUseCase {
    operator fun invoke(
        userName: String,
    ): Boolean {
        val emailRegex = Regex("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        val phoneNumber = Regex("^01\\d{9}$")
        val isEmailValid = emailRegex.matches(userName)
        val isPhoneNumberValid = phoneNumber.matches(userName)
        return !(!isEmailValid && !isPhoneNumberValid)
    }
}