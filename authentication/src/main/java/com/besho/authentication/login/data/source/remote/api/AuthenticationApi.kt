package com.besho.authentication.login.data.source.remote.api

import com.besho.authentication.login.data.model.dto.UserToken
import com.besho.authentication.login.data.model.request.LoginRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationApi {
    @POST("/signin")
    suspend fun login(@Body loginBody: LoginRequest): UserToken
}