package com.besho.authentication.login.presentation.screen.register

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowUpward
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.besho.authentication.R
import com.besho.authentication.login.presentation.components.NormalTextField
import com.besho.authentication.login.presentation.components.PasswordTextField
import com.besho.authentication.login.presentation.screen.register.viewmodel.RegisterFieldsUIState
import com.besho.authentication.login.presentation.screen.register.viewmodel.RegisterUIEvent
import com.besho.authentication.login.presentation.screen.register.viewmodel.RegisterUIState
import com.besho.authentication.login.presentation.screen.register.viewmodel.RegisterViewModel


@Composable
fun RegisterScreen(
    registerViewModel: RegisterViewModel
) {
    val registerUIState by registerViewModel.registerUIState.collectAsStateWithLifecycle()
    when (registerUIState) {
        is RegisterUIState.Loading -> {

        }

        is RegisterUIState.Error -> {

        }

        is RegisterUIState.Register -> {
        }

        is RegisterUIState.Init -> {
            RegisterScreen(
                registerFieldsUIState =
                (registerUIState as RegisterUIState.Init).registerFieldsUIState
            ) {
                registerViewModel.onRegisterEvent(it)
            }
        }

    }

}

@Composable
fun RegisterScreen(
    registerFieldsUIState: RegisterFieldsUIState,
    registerUIEvent: (RegisterUIEvent) -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.register),
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
                    .background(Color.Black.copy(alpha = 0.4f))
                    .padding(it),
            ) {
                val (profileImage, downloadButton, loginButton,
                    emailTextField, usernameTextField, passwordTextField,
                    confirmPasswordTextField, registerButton) =
                    createRefs()
                val profileImageTopGuideLine = createGuidelineFromTop(0.1f)
                val profileImageBottomGuideLine = createGuidelineFromTop(0.2f)
                val textFieldsTopGuideLine = createGuidelineFromTop(0.35f)
                val profileImageStartGuideLine = createGuidelineFromStart(0.32f)
                val profileImageEndGuideLine = createGuidelineFromEnd(0.32f)
                val downloadButtonEndGuideLine = createGuidelineFromEnd(0.45f)


                ImageHolder(
                    buttonModifier = Modifier
                        .constrainAs(downloadButton) {
                            end.linkTo(profileImage.end)
                            start.linkTo(downloadButtonEndGuideLine)
                            bottom.linkTo(profileImage.bottom)
                            top.linkTo(profileImageBottomGuideLine)
                            width = Dimension.fillToConstraints
                            height = Dimension.fillToConstraints
                        },
                    modifier = Modifier
                        .constrainAs(profileImage) {
                            start.linkTo(profileImageStartGuideLine)
                            end.linkTo(profileImageEndGuideLine)
                            top.linkTo(profileImageTopGuideLine)
                            bottom.linkTo(profileImageBottomGuideLine)
                            width = Dimension.fillToConstraints
                            height = Dimension.fillToConstraints
                        },
                    updateImage = {img->
                        registerUIEvent.invoke(RegisterUIEvent.UpdateImage(img))
                    }
                )

                NormalTextField(registerFieldsUIState.userName,
                    "Username",
                    modifier = Modifier.constrainAs(usernameTextField) {
                        top.linkTo(textFieldsTopGuideLine)
                        bottom.linkTo(emailTextField.top, margin = 16.dp)
                        start.linkTo(parent.start, margin = 32.dp)
                        end.linkTo(parent.end, margin = 32.dp)
                        width = Dimension.fillToConstraints
                    }) { userName ->
                    registerUIEvent.invoke(
                        RegisterUIEvent.UpdateUserName(
                            userName
                        )
                    )
                }
                NormalTextField(registerFieldsUIState.email,
                    "Email",
                    modifier = Modifier.constrainAs(emailTextField) {
                        top.linkTo(usernameTextField.bottom, margin = 16.dp)
                        start.linkTo(parent.start, margin = 32.dp)
                        end.linkTo(parent.end, margin = 32.dp)
                        width = Dimension.fillToConstraints
                    }) { email ->
                    registerUIEvent.invoke(RegisterUIEvent.UpdateEmail(email))
                }

                PasswordTextField(registerFieldsUIState.password,
                    "Password",
                    modifier = Modifier.constrainAs(passwordTextField) {
                        start.linkTo(parent.start, margin = 32.dp)
                        end.linkTo(parent.end, margin = 32.dp)
                        top.linkTo(emailTextField.bottom, margin = 16.dp)
                        width = Dimension.fillToConstraints

                    }) { passWord ->
                    registerUIEvent.invoke(
                        RegisterUIEvent.UpdatePassword(
                            passWord
                        )
                    )
                }
                PasswordTextField(registerFieldsUIState.confirmPassword,
                    "Confirm Password",
                    modifier = Modifier.constrainAs(confirmPasswordTextField) {
                        start.linkTo(parent.start, margin = 32.dp)
                        end.linkTo(parent.end, margin = 32.dp)
                        top.linkTo(passwordTextField.bottom, margin = 16.dp)
                        width = Dimension.fillToConstraints

                    }) { passWord ->
                    registerUIEvent.invoke(
                        RegisterUIEvent.UpdateConfirmPassword(
                            passWord
                        )
                    )
                }

                Button(
                    onClick = {
                        registerUIEvent.invoke(RegisterUIEvent.Register)
                    }, shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.constrainAs(registerButton) {
                        bottom.linkTo(loginButton.top)
                        top.linkTo(confirmPasswordTextField.top)
                        start.linkTo(parent.start, margin = 32.dp)
                        end.linkTo(parent.end, margin = 32.dp)
                        width = Dimension.fillToConstraints
                    }
                ) {
                    Text(text = "Register")
                }

                val login = "Login"
                val annotatedString = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    ) {
                        append("Already have an account?")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 16.sp
                        ),
                    ) {
                        pushStringAnnotation(tag = login, annotation = login)
                        append(login)
                    }
                }
                ClickableText(text = annotatedString, onClick = { offset ->
                    annotatedString.getStringAnnotations(offset, offset)
                        .firstOrNull()?.let { span ->
                            println("Clicked on ${span.item}")
                        }
                },
                    modifier = Modifier.constrainAs(loginButton) {
                        bottom.linkTo(parent.bottom, margin = 32.dp)
                        start.linkTo(parent.start, margin = 32.dp)
                        end.linkTo(parent.end, margin = 32.dp)
                    })
            }
        }
    }
}

@Composable
fun ImageHolder(
    buttonModifier: Modifier,
    modifier: Modifier,
    updateImage: (String) -> Unit
) {
    var imgUri: Uri? by rememberSaveable { mutableStateOf(null) }
    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            imgUri = uri
            updateImage.invoke(uri.toString())
        }

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .background(
                Color.DarkGray.copy(0.8f),
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        if (imgUri == null)
            Icon(
                modifier = Modifier.size(32.dp),
                imageVector = Icons.Outlined.Person,
                contentDescription = "Localized description",
                tint = Color.White,
            )
        else {
            Image(
                painter = rememberAsyncImagePainter(imgUri),
                contentScale = ContentScale.FillBounds,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
            )
        }
    }
    Box(
        modifier = buttonModifier
            .border(
                width = 1.dp,
                color = Color.White,
                shape = CircleShape
            )
            .aspectRatio(1f)
            .background(
                MaterialTheme.colorScheme.primary,
                shape = CircleShape
            )
            .clickable {
                galleryLauncher.launch("image/*")
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            Icons.Outlined.ArrowUpward,
            contentDescription = "Localized description",
            tint = Color.White,
        )
    }
}
