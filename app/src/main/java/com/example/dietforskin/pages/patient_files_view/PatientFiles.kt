package com.example.dietforskin.pages.patient_files_view

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.dietforskin.pages.CommonElements
import com.example.dietforskin.pages.patient_information_view.PdfRendererViewer
import com.example.dietforskin.ui.theme.colorPinkMain
import com.example.dietforskin.viewmodels.PatientFilesViewModel
import com.google.android.gms.tasks.Tasks
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun PatientFiles(patientFilesViewModel: PatientFilesViewModel, context: Context, uuid: String) {
  val listOfPdf = fetchPDF(uuid)

  LazyVerticalGrid(modifier = Modifier.fillMaxSize(),
    columns = GridCells.Fixed(2),
    contentPadding = PaddingValues(10.dp),
    content = {
      items(listOfPdf) { pdfFileName ->
        Card(
          colors = CardDefaults.cardColors(colorPinkMain),
          modifier = Modifier
            .width(100.dp)
            .height(100.dp)
            .padding(4.dp)
            .fillMaxWidth(),
          elevation = CardDefaults.cardElevation(100.dp),
        ) {
          Text(
            text = pdfFileName,
            fontWeight = FontWeight.Bold,
            fontSize = 10.sp,
            color = Color(0xFFFFFFFF),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
          )
        }
      }
    })

}

@Composable
fun fetchPDF(uuid: String): List<String> {
  var pdfList by remember { mutableStateOf(emptyList<String>()) }

  val storageRef = CommonElements().storage
  val pathRef = storageRef.child("$uuid/")


  DisposableEffect(Unit) {
    val job = CoroutineScope(Dispatchers.IO).launch {
      try {
        val listResult = Tasks.await(pathRef.listAll())
        val items = listResult.items.map { it.name }

        withContext(Dispatchers.Main) {
          pdfList = items
        }
      } catch (e: Exception) {
        println("$e")
      }
    }

    onDispose {
      job.cancel()
    }
  }
  return pdfList
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
