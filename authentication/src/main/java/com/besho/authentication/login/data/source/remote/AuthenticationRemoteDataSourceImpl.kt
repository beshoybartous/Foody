package com.besho.authentication.login.data.source.remote

import com.besho.authentication.login.data.model.dto.UserToken
import com.besho.authentication.login.data.model.request.LoginRequest
import com.besho.authentication.login.data.source.remote.api.AuthenticationApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext


class AuthenticationRemoteDataSourceImpl(
    private val authenticationApi:
    AuthenticationApi
) : AuthenticationRemoteDataSource {
    override suspend fun login(username: String, password: String): UserToken =
        authenticationApi.login(LoginRequest(username, password))
}


fun CoroutineScope.launch(
    context: CoroutineContext = Dispatchers.Default,
    block: suspend CoroutineScope.() -> Unit,
    onError: (Throwable) -> Unit,
) {
    launch(context) {
        try {
            block(this)
        } catch (exception: Exception) {
            onError(exception)
        }
    }
}

/**
 * Executes the [block] inside the coroutine async method with the specified [context]
 * and catches any exception occurs and invokes the [onError].
 */
fun <T> CoroutineScope.async(
    context: CoroutineContext = Dispatchers.Default,
    block: suspend CoroutineScope.() -> T,
    onError: (Throwable) -> T,
): Deferred<T> = async(context) {
    try {
        block(this)
    } catch (exception: Exception) {
        onError(exception)
    }
}

/**
 * Executes the [block] inside the coroutine withContext method with the specified [context]
 * and catches any exception occurs and invokes the [onError].
 */
suspend fun <T> withContext(
    context: CoroutineContext = Dispatchers.Default,
    block: suspend CoroutineScope.() -> T,
    onError: (Throwable) -> T,
): T = withContext(context) {
    try {
        block(this)
    } catch (exception: Exception) {
        onError(exception)
    }
}