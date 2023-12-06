package com.example.dietforskin.elements

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.dietforskin.ui.theme.colorTextFieldsAndButton
import kotlinx.coroutines.Job

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: @Composable() (() -> Unit),
    keyboardOptions: KeyboardOptions,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable() (() -> Unit)? = null,
    maxLine: Int = 1,
) {
    val focusManager = LocalFocusManager.current

    val typingJob by remember { mutableStateOf<Job?>(null) }
    BackHandler {

        typingJob?.cancel()
        focusManager.clearFocus()
    }
    TextField(
        modifier = Modifier.shadow(15.dp),
        value = value,
        onValueChange = onValueChange,
        label = label,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = colorTextFieldsAndButton,
            unfocusedContainerColor = colorTextFieldsAndButton,
            disabledContainerColor = colorTextFieldsAndButton,
            cursorColor = Color.Black,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedLabelColor = colorTextFieldsAndButton,
            unfocusedLabelColor = Color.Black,
        ),
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        trailingIcon = trailingIcon,
        maxLines = maxLine,
    )
}
