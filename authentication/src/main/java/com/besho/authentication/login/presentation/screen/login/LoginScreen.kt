package com.besho.authentication.login.presentation.screen.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.besho.authentication.R
import com.besho.authentication.login.domain.model.LoginResult
import com.besho.authentication.login.presentation.components.NormalTextField
import com.besho.authentication.login.presentation.components.PasswordTextField
import com.besho.authentication.login.presentation.screen.login.model.event.LoginEvent
import com.besho.authentication.login.presentation.screen.login.model.state.LoginState
import com.besho.authentication.login.presentation.screen.login.navigation.LoginInternalNavigation
import com.besho.authentication.login.presentation.screen.login.viewmodel.LoginViewModel
import com.besho.authentication.navigation.AuthenticationExternalNavigation

@Composable
fun LoginScreen(
    externalNavigation: (event: AuthenticationExternalNavigation) -> Unit,
    internalNavigation: (event: LoginInternalNavigation) -> Unit,
    loginViewModel: LoginViewModel
) {
    val context = LocalContext.current
    val resultState by loginViewModel.result.collectAsStateWithLifecycle(null)
    val state by loginViewModel.state.collectAsStateWithLifecycle()

    when (resultState) {
        is LoginResult.OnSuccess -> {
            externalNavigation.invoke(
                AuthenticationExternalNavigation
                    .HomeNavigation("")
            )
        }

        is LoginResult.OnError -> {
            LaunchedEffect(Unit) {
                Toast.makeText(
                    context,
                    (resultState as LoginResult.OnError).errorMessage.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        else -> {}
    }
    LoginScreen(
        state,
        internalNavigation,
        loginEvent = loginViewModel::onEvent
    )
}

@Composable
internal fun LoginScreen(
    loginState: LoginState,
    internalNavigation: (event: LoginInternalNavigation) -> Unit,
    loginEvent: (LoginEvent) -> Unit,
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
                val (title, emailTextField, passwordTextField, loginButton,
                    forgetPasswordButton, createAccountButton) = createRefs()

                LoginScreenTitle(Modifier.constrainAs(title) {
                    linkTo(parent.top, parent.bottom, bias = 0.12F)
                    top.linkTo(parent.top, margin = 16.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                })

                NormalTextField(loginState.username,
                    loginState.userNameError,
                    loginState.isUserNameInvalid,
                    "Email",
                    modifier = Modifier.constrainAs(emailTextField) {
                        bottom.linkTo(passwordTextField.top, margin = 16.dp)
                        start.linkTo(parent.start, margin = 32.dp)
                        end.linkTo(parent.end, margin = 32.dp)
                        width = Dimension.fillToConstraints
                    },
                    onValueChange = { currentUsername ->
                        loginEvent(LoginEvent.UpdateUserName(currentUsername))
                    }
                )
                PasswordTextField(
                    loginState.password,
                    loginState.passwordError,
                    loginState.isPasswordInvalid,
                    "Password",
                    modifier = Modifier.constrainAs(passwordTextField) {
                        start.linkTo(parent.start, margin = 32.dp)
                        end.linkTo(parent.end, margin = 32.dp)
                        bottom.linkTo(forgetPasswordButton.top)
                        width = Dimension.fillToConstraints

                    },
                    onPasswordChange = { currentPassword ->
                        loginEvent(LoginEvent.UpdatePassword(currentPassword))
                    }
                )

                ForgetPasswordButton(
                    modifier = Modifier.constrainAs(forgetPasswordButton) {
                        bottom.linkTo(loginButton.top, margin = 32.dp)
                        end.linkTo(passwordTextField.end, margin = 0.dp)
                    },
                    onClick = {
                        internalNavigation.invoke(
                            LoginInternalNavigation.ForgetPassword
                        )
                    })

                LoadingButton(
                    isLoading = loginState.isLoading,
                    onClick = {
                        loginEvent(LoginEvent.Login)
                    },
                    text="Login",
                    modifier = Modifier.constrainAs(loginButton) {
                        bottom.linkTo(createAccountButton.top, margin = 24.dp)
                        start.linkTo(parent.start, margin = 32.dp)
                        end.linkTo(parent.end, margin = 32.dp)
                        width = Dimension.fillToConstraints
                    }
                )

                TextButton(onClick = {
                    internalNavigation.invoke(LoginInternalNavigation.CreateAccount)
                }, modifier = Modifier.constrainAs(createAccountButton) {
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
        LoginState(), internalNavigation = {}
    ) {}
}


@Composable
fun LoadingButton(
    isLoading: Boolean,
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = !isLoading,
        shape = RoundedCornerShape(8.dp)
    ) {
        if (isLoading) {
            Box(Modifier.size(24.dp)) {
                CircularProgressIndicator(
                    Modifier.align(Alignment.Center),
                )
            }
        } else {
            Text(
                text,
            )
        }
    }
}




