package com.example.dietforskin.elements

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.dietforskin.ui.theme.colorTextFieldsAndButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextFieldLogin(
    value: String,
    onValueChange: (String) -> Unit,
    label: @Composable() (() -> Unit),
    keyboardOptions: KeyboardOptions,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable() (() -> Unit)? = null,
    maxLine: Int = 1,
) {

    TextField(
        modifier = Modifier.shadow(15.dp),
        value = value,
        onValueChange = onValueChange,
        label = label,
        colors = TextFieldDefaults.textFieldColors(
            unfocusedLabelColor = Color.Black,
            focusedLabelColor = colorTextFieldsAndButton,
            cursorColor = Color.Black,
            textColor = Color.Black,
            containerColor = colorTextFieldsAndButton,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        trailingIcon = trailingIcon,
        maxLines = maxLine,
    )
}
