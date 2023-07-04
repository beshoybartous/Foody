package com.besho.authentication.login.data.source.remote

import com.besho.authentication.login.data.model.dto.UserToken
import com.besho.authentication.login.data.model.request.LoginRequest
import com.besho.authentication.login.data.source.remote.api.AuthenticationApi

class AuthenticationRemoteDataSourceImpl(
    private val authenticationApi:
    AuthenticationApi
) : AuthenticationRemoteDataSource {
    override suspend fun login(username: String, password: String): UserToken =
        authenticationApi.login(LoginRequest(username, password))

}