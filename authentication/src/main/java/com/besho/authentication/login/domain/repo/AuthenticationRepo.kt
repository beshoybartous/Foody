package com.besho.authentication.login.domain.repo

interface AuthenticationRepo {

    suspend fun signUp(imageUri: String)
    suspend fun signIn(username: String,password:String)
}