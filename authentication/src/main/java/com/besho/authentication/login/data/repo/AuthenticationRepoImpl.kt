package com.besho.authentication.login.data.repo

import android.content.Context
import androidx.core.net.toUri
import com.besho.authentication.login.data.source.remote.AuthenticationRemoteDataSource
import com.besho.authentication.login.data.source.remote.withContext
import com.besho.authentication.login.domain.model.LoginResult
import com.besho.authentication.login.domain.repo.AuthenticationRepo
import kotlinx.coroutines.Dispatchers
import java.io.File
import java.io.InputStream

class AuthenticationRepoImpl(
    private val context: Context,
    private val authenticationRemoteDataSource: AuthenticationRemoteDataSource
) :
    AuthenticationRepo {
    override suspend fun signUp(imageUri: String) {
        val imageFile = File(context.cacheDir, "image.jpg")

        imageFile.outputStream().use {
            val inputStream: InputStream? =
                context.contentResolver.openInputStream(imageUri.toUri())
            inputStream?.copyTo(it)
            inputStream?.close()
        }

    }

    override suspend fun signIn(
        username: String,
        password: String
    ): LoginResult = withContext(Dispatchers.IO,
        block = {
            authenticationRemoteDataSource.login(username, password)
            LoginResult.OnSuccess
        }, onError = {
            LoginResult.OnError(errorMessage = -1)
        })

}