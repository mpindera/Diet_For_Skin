package com.example.dietforskin.elements

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.dietforskin.R
import com.example.dietforskin.ui.theme.colorPinkMain
import com.example.dietforskin.ui.theme.colorTextFieldsAndButton
import kotlinx.coroutines.Job

@Composable
fun CustomElevatedButton(
    paddingStartAndEnd: Dp,
    paddingBottom: Dp,
    id: Int,
    onClick: () -> Unit
) {
    ElevatedButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = paddingStartAndEnd, end = paddingStartAndEnd, bottom = paddingBottom),
        onClick = onClick,
        elevation = ButtonDefaults.buttonElevation(10.dp),
        colors = ButtonDefaults.buttonColors(
            colorPinkMain
        )
    ) {
        Text(stringResource(id = id), color = Color.Black)
    }
}
