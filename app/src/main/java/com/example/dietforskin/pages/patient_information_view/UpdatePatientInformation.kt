package com.example.dietforskin.pages.patient_information_view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.dietforskin.R
import com.example.dietforskin.data.profile.PagesSite
import com.example.dietforskin.elements.CustomElevatedButton
import com.example.dietforskin.pages.CommonElements
import com.example.dietforskin.report.Reports
import com.example.dietforskin.viewmodels.ProfileViewModel
import com.example.dietforskin.viewmodels.UpdatePatientInformationViewModel
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import kotlinx.coroutines.delay

@Composable
fun UpdatePatientInformation(
  profileViewModel: ProfileViewModel,
  navController: NavHostController,
  context: Context,
  updatePatientInformationViewModel: UpdatePatientInformationViewModel,
  uuid: String
) {

  profileViewModel.updateSelectionOfPagesSite(PagesSite.UPDATE_PROFILE)
  DisposableEffect(Unit) {
    onDispose {

    }
  }
  val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
    uri?.let { updatePatientInformationViewModel.onPDFUriChanged(it) }
  }

  Box(modifier = Modifier.fillMaxWidth()) {
    CommonElements().CanvasBackground(modifier = Modifier.align(alignment = Alignment.TopEnd))
    CommonElements().canvasWithName(label = stringResource(id = R.string.update_profile))
  }

  Box(modifier = Modifier.fillMaxSize()) {
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
        updatePatientInformationViewModel.visibilityOfBottomPDFSheet.value = true
      }

      CustomElevatedButton(
        paddingStartAndEnd = 85.dp,
        paddingBottom = 5.dp,
        id = R.string.upload_PDF_File,
      ) {
        launcher.launch("application/pdf")
      }

      PreviewPDFFile(
        updatePatientInformationViewModel.pdfUri.value, context, updatePatientInformationViewModel
      )

      Spacer(modifier = Modifier.height(20.dp))

      if (updatePatientInformationViewModel.pdfUri.value != null) {
        CustomElevatedButton(
          paddingStartAndEnd = 85.dp,
          paddingBottom = 0.dp,
          id = R.string.add_PDF_File_to_database,
        ) {
          updatePatientInformationViewModel.uploadPdfToFirebase(
            pdfUri = updatePatientInformationViewModel.pdfUri.value,
            context = context,
            updatePatientInformationViewModel = updatePatientInformationViewModel,
            uuid = uuid
          )
        }
      }

      if (updatePatientInformationViewModel.visibilityOfBottomPDFSheet.value) {
        BottomPDFSheet(uuid = uuid,
          updatePatientInformationViewModel = updatePatientInformationViewModel,
          visibilityOfBottomPDFSheet = updatePatientInformationViewModel.visibilityOfBottomPDFSheet,
          onDismissSheet = {
            updatePatientInformationViewModel.visibilityOfBottomPDFSheet.value = false
          })
      }

    }
  }
}

@Composable
fun PreviewPDFFile(
  pdfUri: Uri?,
  context: Context,
  updatePatientInformationViewModel: UpdatePatientInformationViewModel
) {
  var showBitmap by remember { mutableStateOf(false) }

  LaunchedEffect(showBitmap) {
    delay(3000)
    showBitmap = true
  }

  Column(
    modifier = Modifier
      .width(200.dp)
      .height(220.dp)
      .fillMaxWidth()
  ) {
    Column(
      modifier = Modifier.fillMaxSize(),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Card(
        modifier = Modifier
          .width(150.dp)
          .height(150.dp),
        elevation = CardDefaults.cardElevation(20.dp)
      ) {

        if (showBitmap) {
          if (pdfUri != null) {
            updatePatientInformationViewModel.PdfRendererViewer(pdfUri)
          }
        }
      }

      if (pdfUri != null) {
        val fileName = updatePatientInformationViewModel.getFileName(context, pdfUri)
        Text(text = "$fileName", modifier = Modifier.padding(vertical = 5.dp))
      }
    }
  }
}
