package com.example.dietforskin.pages.patient_files_view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.dietforskin.data.profile.PagesSite
import com.example.dietforskin.pages.pdf_view.PDFView
import com.example.dietforskin.ui.theme.colorPinkMain
import com.example.dietforskin.viewmodels.PatientFilesViewModel
import com.example.dietforskin.viewmodels.ProfileViewModel

@RequiresApi(Build.VERSION_CODES.P)
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun PatientFiles(
  patientFilesViewModel: PatientFilesViewModel,
  context: Context,
  uuid: String,
  navController: NavHostController,
  profileViewModel: ProfileViewModel
) {
  val mapOfPdf = patientFilesViewModel.fetchPDFWithName(uuid)
  val visibleOfPDFView by patientFilesViewModel.visibleOfPDFView.collectAsState()
  val pathPdf by patientFilesViewModel.pathPdf.collectAsState()
  val namePdf by patientFilesViewModel.namePdf.collectAsState()
  profileViewModel.updateSelectionOfPagesSite(PagesSite.DOCUMENT_VIEW_PATIENT)


  if (visibleOfPDFView) {
    Log.d("testa", "Chce wejść")
    PDFView(pathPdf, namePdf, profileViewModel)
  } else {
    ShowPDFFiles(patientFilesViewModel, mapOfPdf, navController, context)
  }


}


@Composable
fun ShowPDFFiles(
  patientFilesViewModel: PatientFilesViewModel,
  mapOfPdf: Map<String, String>,
  navController: NavHostController,
  context: Context
) {
  var selectedCard: Pair<String, String>? by remember { mutableStateOf(null) }
  patientFilesViewModel.onVisibleOfPDFViewChanged(false)

  LazyVerticalGrid(
    modifier = Modifier.fillMaxSize(),
    columns = GridCells.Fixed(2),
    contentPadding = PaddingValues(10.dp),
    content = {
      itemsIndexed(mapOfPdf.entries.toList()) { index, (namePDF, linkPDF) ->
        Box {
          Card(
            colors = CardDefaults.cardColors(colorPinkMain),
            modifier = Modifier
              .fillMaxWidth()
              .height(150.dp)
              .padding(4.dp),
            elevation = CardDefaults.cardElevation(100.dp),
          ) {
            Column(
              modifier = Modifier
                .fillMaxSize(),
              verticalArrangement = Arrangement.Bottom,
              horizontalAlignment = Alignment.CenterHorizontally
            ) {
              Text(
                text = if (namePDF.length <= 20) namePDF else namePDF.substring(0,15).plus("...pdf"),
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp,
                color = Color(0xFFFFFFFF),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
              )
              Button(modifier = Modifier.padding(bottom = 5.dp),onClick = {
                selectedCard = namePDF to linkPDF
              }) {
                Text("Otwórz Plik")
              }
            }
          }
        }
      }
    })
  selectedCard?.let { (selectedName, selectedPath) ->
    LaunchedEffect(selectedCard) {
      patientFilesViewModel.downloadPDF(context, selectedName, selectedPath)
      selectedCard = null
    }
  }
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
