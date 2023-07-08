package com.besho.authentication.login.domain.repo

import com.besho.authentication.login.domain.model.LoginResult


interface AuthenticationRepo {
    suspend fun signUp(imageUri: String)
    suspend fun signIn(username: String, password: String): LoginResult
}