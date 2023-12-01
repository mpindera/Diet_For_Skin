package com.example.dietforskin.pages

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dietforskin.elements.CustomTextField
import com.example.dietforskin.ui.theme.colorPinkMain

class CommonElements {
    @Composable
    fun canvasWithName(label: String) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Canvas(
                modifier = Modifier.align(alignment = Alignment.TopStart),
                onDraw = {
                    val rectangleSize = Size(label.length.dp.toPx()*10, 140.dp.toPx())
                    val circleRadius = 70.dp.toPx()

                    val offsetY = 70.dp.toPx()

                    drawRoundRect(
                        color = colorPinkMain,
                        size = rectangleSize,
                        cornerRadius = CornerRadius.Zero,
                        topLeft = Offset(0f, -offsetY)
                    )

                    drawCircle(
                        color = colorPinkMain,
                        center = Offset(
                            rectangleSize.width,
                            rectangleSize.height - circleRadius - offsetY
                        ),
                        radius = circleRadius
                    )
                }
            )



            Text(
                text = label,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(start = 11.dp, top = 20.dp)
                    .fillMaxWidth()
                    .align(Alignment.TopStart),
                letterSpacing = 1.sp,
                textAlign = TextAlign.Start
            )
        }
    }

    @Composable
    fun CustomTextFieldEmail(email: String, onValueChangeEmail: (String) -> Unit) {
        CustomTextField(
            value = email.lowercase(),
            onValueChange = onValueChangeEmail,
            label = {
                Text(text = "EMAIL", letterSpacing = 1.sp)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        )
    }
}