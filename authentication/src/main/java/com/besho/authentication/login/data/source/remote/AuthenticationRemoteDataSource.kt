package com.besho.authentication.login.data.source.remote

import com.besho.authentication.login.data.model.dto.UserToken

interface AuthenticationRemoteDataSource {
    suspend fun login(username: String, password: String): UserToken
}