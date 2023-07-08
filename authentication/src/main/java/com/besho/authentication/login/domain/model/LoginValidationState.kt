package com.besho.authentication.login.domain.model

data class LoginValidationState(
    var invalidUserName:Boolean=false,
    var invalidPassword:Boolean=false,
)