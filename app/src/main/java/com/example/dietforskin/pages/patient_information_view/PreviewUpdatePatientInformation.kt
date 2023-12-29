package com.example.dietforskin.pages.patient_information_view

import android.net.Uri
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dietforskin.R
import com.example.dietforskin.elements.CustomElevatedButton
import com.example.dietforskin.ui.theme.colorCircle
import com.example.dietforskin.ui.theme.colorPinkMain
import com.example.dietforskin.viewmodels.UpdatePatientInformationViewModel


@Preview(showBackground = true)
@Composable
fun PreviewUpdatePatientInformation() {
    val context = LocalContext.current
    val pdfUri by remember { mutableStateOf<Uri?>(null) }
    Row(Modifier.fillMaxWidth()) {
        Box(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.border(1.dp, Color.Black))

            Canvas(modifier = Modifier.align(Alignment.TopEnd), onDraw = {
                drawCircle(
                    color = colorCircle, radius = 450.dp.toPx()
                )
            })
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                CustomElevatedButton(
                    paddingStartAndEnd = 85.dp,
                    paddingBottom = 5.dp,
                    id = R.string.show_list_of_PDF_File,
                ) {

                }

                CustomElevatedButton(
                    paddingStartAndEnd = 85.dp,
                    paddingBottom = 5.dp,
                    id = R.string.upload_PDF_File,
                ) {

                }

                if (pdfUri == null) {
                    CustomElevatedButton(
                        paddingStartAndEnd = 85.dp,
                        paddingBottom = 0.dp,
                        id = R.string.add_PDF_File_to_database,
                    ) {

                    }
                }

                PreviewPDFFile(pdfUri, context, UpdatePatientInformationViewModel())

                Spacer(modifier = Modifier.height(20.dp))
            }
        }

    }
}
