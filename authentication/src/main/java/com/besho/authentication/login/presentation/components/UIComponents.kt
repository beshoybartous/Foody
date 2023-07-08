package com.besho.authentication.login.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp


@Composable
fun textFieldDesign(): TextFieldColors {
    return OutlinedTextFieldDefaults.colors(
        focusedTextColor = Color.Black,
        focusedContainerColor = Color.LightGray.copy(alpha = 0.4f),
        unfocusedContainerColor = Color.LightGray.copy(alpha = 0.4f),
        errorContainerColor = Color.LightGray.copy(alpha = 0.4f),
        unfocusedTextColor = Color.Black,
        focusedSupportingTextColor = Color.Transparent,
        unfocusedSupportingTextColor = Color.Transparent,
    )
}

@Composable
fun NormalTextField(
    value: String = "",
    errorMessage: Int,
    isError: Boolean = false,
    placeHolder: String,
    modifier: Modifier,
    onValueChange: (String) -> Unit
) {

    OutlinedTextField(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        placeholder = { Text(color = Color.White, text = placeHolder) },
        value = value,
        singleLine = true,
        isError = isError,
        colors = textFieldDesign(),
        supportingText = {
            if (isError) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = stringResource(errorMessage),
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        textStyle = LocalTextStyle.current.copy(color = Color.White),
        leadingIcon = {
            Icon(
                Icons.Outlined.Email,
                contentDescription = "Localized description",
                tint = Color.White
            )
        },
        onValueChange = {
            onValueChange.invoke(it)
        })
}

@Composable
fun PasswordTextField(
    value: String,
    errorMessage: Int,
    isError: Boolean = false,
    placeHolder: String,
    modifier: Modifier,
    onPasswordChange: (String) -> Unit
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    OutlinedTextField(modifier = modifier,
        colors = textFieldDesign(),
        shape = RoundedCornerShape(8.dp),
        placeholder = { Text(color = Color.White, text = placeHolder) },
        value = value,
        singleLine = true,
        isError = isError,
        supportingText = {
            if (isError) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = stringResource(errorMessage),
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        textStyle = LocalTextStyle.current.copy(color = Color.White),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val image = if (passwordVisible)
                Icons.Outlined.Visibility
            else Icons.Outlined.VisibilityOff
            val description =
                if (passwordVisible) "Hide password" else "Show password"
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(
                    imageVector = image, description, tint = Color.White
                )
            }
        },
        leadingIcon = {
            Icon(
                Icons.Outlined.Lock,
                contentDescription = "Localized description",
                tint = Color.White
            )
        },
        onValueChange = {
            onPasswordChange.invoke(it)
        })
}