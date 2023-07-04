package com.besho.authentication.login.presentation.screen.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.besho.authentication.R
import com.besho.authentication.login.presentation.components.NormalTextField
import com.besho.authentication.login.presentation.components.PasswordTextField
import com.besho.authentication.login.presentation.screen.login.navigation.LoginInternalNavigation
import com.besho.authentication.login.presentation.screen.login.viewmodel.LoginTextFieldUIState
import com.besho.authentication.login.presentation.screen.login.viewmodel.LoginUIEvent
import com.besho.authentication.login.presentation.screen.login.viewmodel.LoginUIState
import com.besho.authentication.login.presentation.screen.login.viewmodel.LoginViewModel
import com.besho.authentication.navigation.AuthenticationExternalNavigation

@Composable
fun LoginScreen(
    externalNavigation: (event: AuthenticationExternalNavigation) -> Unit,
    internalNavigation: (event: LoginInternalNavigation) -> Unit,
    loginViewModel: LoginViewModel
) {
    val state by loginViewModel.loginUIState.collectAsStateWithLifecycle()

    when (state) {
        is LoginUIState.Loading -> {

        }

        is LoginUIState.Error -> {
            LoginScreen(
                (state as LoginUIState.Error).loginTextFieldUIState,
                internalNavigation
            ) {
                loginViewModel.onLoginEvent(it)
            }
        }

        is LoginUIState.Login -> {
            externalNavigation.invoke(
                AuthenticationExternalNavigation.HomeNavigation("")
            )
        }

        else -> {
            LoginScreen(
                loginViewModel.loginTextFieldUIState,
                internalNavigation
            ) {
                loginViewModel.onLoginEvent(it)
            }
        }
    }
}

@Composable
internal fun LoginScreen(
    loginTextFieldUIState: LoginTextFieldUIState,
    internalNavigation: (event: LoginInternalNavigation) -> Unit,
    loginUIEvent: (LoginUIEvent) -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.login),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.45f))
                    .padding(it)
            ) {
                // Create references for the composables to constrain
                val (title, email, password, login, forgetPassword, createAccount) = createRefs()

                LoginScreenTitle(Modifier.constrainAs(title) {
                    linkTo(parent.top, parent.bottom, bias = 0.12F)
                    top.linkTo(parent.top, margin = 16.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                })

                NormalTextField(loginTextFieldUIState.userName,
                    "Email",
                    modifier = Modifier.constrainAs(email) {
                        bottom.linkTo(password.top, margin = 16.dp)
                        start.linkTo(parent.start, margin = 32.dp)
                        end.linkTo(parent.end, margin = 32.dp)
                        width = Dimension.fillToConstraints
                    }) { userName ->
                    loginUIEvent.invoke(LoginUIEvent.UpdateUserName(userName))
                }
                PasswordTextField(
                    loginTextFieldUIState.password,
                    "Password",
                    modifier = Modifier.constrainAs(password) {
                        start.linkTo(parent.start, margin = 32.dp)
                        end.linkTo(parent.end, margin = 32.dp)
                        bottom.linkTo(forgetPassword.top)
                        width = Dimension.fillToConstraints

                    }) { passWord ->
                    loginUIEvent.invoke(LoginUIEvent.UpdatePassword(passWord))
                }

                ForgetPasswordButton(
                    modifier = Modifier.constrainAs(forgetPassword) {
                        bottom.linkTo(login.top, margin = 32.dp)
                        end.linkTo(password.end, margin = 0.dp)
                    },
                    onClick = {
                        internalNavigation.invoke(
                            LoginInternalNavigation.ForgetPassword
                        )
                    })

                Button(
                    onClick = {
                        loginUIEvent.invoke(LoginUIEvent.Login)
                    }, shape = RoundedCornerShape(8.dp),
                    modifier  = Modifier.constrainAs(login) {
                        bottom.linkTo(createAccount.top, margin = 24.dp)
                        start.linkTo(parent.start, margin = 32.dp)
                        end.linkTo(parent.end, margin = 32.dp)
                        width = Dimension.fillToConstraints
                    }
                ) {
                    Text(text = "Login")
                }

                TextButton(onClick = {
                    internalNavigation.invoke(LoginInternalNavigation.CreateAccount)
                }, modifier = Modifier.constrainAs(createAccount) {
                    bottom.linkTo(parent.bottom, margin = 16.dp)
                    start.linkTo(parent.start, margin = 32.dp)
                    end.linkTo(parent.end, margin = 32.dp)
                }) {
                    Text(
                        text = "Create New Account",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Composable
fun LoginButton(modifier: Modifier, onclick: () -> Unit) {


}

@Composable
fun ForgetPasswordButton(modifier: Modifier, onClick: () -> Unit) {
    TextButton(onClick = {
        onClick.invoke()
    }, modifier = modifier) {
        Text(
            text = "Forget password?", color = Color.White, fontSize = 16.sp
        )
    }
}

@Composable
fun LoginScreenTitle(modifier: Modifier) {
    Text(
        modifier = modifier,
        color = Color.White,
        fontWeight = FontWeight.Bold,
        fontSize = 50.sp,
        text = "Foodybite"
    )
}


@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        LoginTextFieldUIState(), internalNavigation = {}
    ) {}
}





