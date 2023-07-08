package com.besho.authentication.di

import android.content.Context
import com.besho.authentication.login.data.repo.AuthenticationRepoImpl
import com.besho.authentication.login.data.source.remote.AuthenticationRemoteDataSource
import com.besho.authentication.login.data.source.remote.AuthenticationRemoteDataSourceImpl
import com.besho.authentication.login.data.source.remote.api.AuthenticationApi
import com.besho.authentication.login.domain.repo.AuthenticationRepo
import com.besho.authentication.login.domain.usecase.LoginUseCase
import com.besho.authentication.login.domain.usecase.LoginValidationsUseCase
import com.besho.authentication.login.domain.usecase.PasswordValidationUseCase
import com.besho.authentication.login.domain.usecase.UserNameValidationUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthenticationModule {

    @Provides
    @Singleton
    fun provideAuthenticationApi(retrofit: Retrofit): AuthenticationApi =
        retrofit.create(AuthenticationApi::class.java)


    @Provides
    @Singleton
    fun provideAuthenticationRemoteDataSource(
        authenticationApi: AuthenticationApi
    ): AuthenticationRemoteDataSource =
        AuthenticationRemoteDataSourceImpl(authenticationApi)

    @Provides
    @Singleton
    fun provideAuthenticationRepo(
        @ApplicationContext context: Context,
        authenticationRemoteDataSource: AuthenticationRemoteDataSource
    ): AuthenticationRepo =
        AuthenticationRepoImpl(context, authenticationRemoteDataSource)

    @Provides
    @Singleton
    fun provideLoginUseCase(authenticationRepo: AuthenticationRepo) =
        LoginUseCase(authenticationRepo)

    @Provides
    @Singleton
    fun provideLoginValidationUseCase(
        userNameValidationUseCase: UserNameValidationUseCase,
        passwordValidationUseCase: PasswordValidationUseCase,
    ) =
        LoginValidationsUseCase(
            userNameValidationUseCase,
            passwordValidationUseCase
        )

    @Provides
    @Singleton
    fun provideUserNameValidationUseCase(
    ) = UserNameValidationUseCase()

    @Provides
    @Singleton
    fun provideUserPasswordValidationUseCase(
    ) = PasswordValidationUseCase()
}