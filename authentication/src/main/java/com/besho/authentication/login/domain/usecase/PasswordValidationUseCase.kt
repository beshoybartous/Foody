package com.besho.authentication.login.domain.usecase

class PasswordValidationUseCase {
    operator fun invoke(password: String): Boolean {
        return password.length >= 8
    }
}