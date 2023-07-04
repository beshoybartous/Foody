package com.besho.authentication.login.data.repo

import android.content.Context
import androidx.core.net.toUri
import com.besho.authentication.login.data.source.remote.AuthenticationRemoteDataSource
import com.besho.authentication.login.domain.repo.AuthenticationRepo
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

    override suspend fun signIn(username: String, password: String) {
        authenticationRemoteDataSource.login(username, password)
    }
}