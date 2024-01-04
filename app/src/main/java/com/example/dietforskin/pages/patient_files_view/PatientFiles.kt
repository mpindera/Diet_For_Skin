package com.example.dietforskin.pages.patient_files_view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.dietforskin.pages.pdf_view.PDFView
import com.example.dietforskin.ui.theme.colorPinkMain
import com.example.dietforskin.viewmodels.PatientFilesViewModel

@RequiresApi(Build.VERSION_CODES.P)
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun PatientFiles(
  patientFilesViewModel: PatientFilesViewModel,
  context: Context,
  uuid: String,
  navController: NavHostController,

  ) {
  val mapOfPdf = patientFilesViewModel.fetchPDFWithName(uuid)

  when (patientFilesViewModel.visibilityOfPDFFiles.value) {
    true -> {
      ShowPDFFiles(patientFilesViewModel, mapOfPdf, navController, context)
    }

    false -> {
//      PDFView()
    }
  }


}

@Composable
fun ShowPDFFiles(
  patientFilesViewModel: PatientFilesViewModel,
  mapOfPdf: Map<String, String>,
  navController: NavHostController,
  context: Context
) {

  var pathToPDF = ""

  LazyVerticalGrid(modifier = Modifier.fillMaxSize(),
    columns = GridCells.Fixed(2),
    contentPadding = PaddingValues(10.dp),
    content = {
      itemsIndexed(mapOfPdf.entries.toList()) { index, (namePDF, linkPDF) ->
        Card(
          colors = CardDefaults.cardColors(colorPinkMain),
          modifier = Modifier
            .width(100.dp)
            .height(450.dp)
            .clickable {
              pathToPDF = patientFilesViewModel.downloadPDF(context, namePDF, linkPDF)
            }
            .padding(4.dp)
            .fillMaxWidth(),
          elevation = CardDefaults.cardElevation(100.dp),
        ) {
          Text(
            text = namePDF,
            fontWeight = FontWeight.Bold,
            fontSize = 10.sp,
            color = Color(0xFFFFFFFF),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
          )
        }
        Button(onClick = {
          Log.d("FirebaseStorage", "$pathToPDF -- TAKI JEST PATH TERAZ")
        }) {
          Text(namePDF)
        }
      }
    })


}


@Composable
@Preview(showBackground = true)
fun PreviewPatientFiles() {
  Card(
    colors = CardDefaults.cardColors(colorPinkMain),
    modifier = Modifier
      .width(100.dp)
      .height(100.dp)
      .padding(4.dp)
      .fillMaxWidth(),
    elevation = CardDefaults.cardElevation(100.dp),
  ) {
    Column(
      Modifier.fillMaxSize(),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {

      Text(
        text = "file.pdf",
        fontWeight = FontWeight.Bold,
        fontSize = 10.sp,
        color = Color(0xFFFFFFFF),
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(16.dp)
      )
    }

  }
}
