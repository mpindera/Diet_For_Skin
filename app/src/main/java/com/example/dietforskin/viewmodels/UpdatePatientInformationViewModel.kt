package com.example.dietforskin.viewmodels

import android.content.Context
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.ViewModel
import com.example.dietforskin.report.Reports
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UpdatePatientInformationViewModel : ViewModel() {

    private var _pdfUri = mutableStateOf<Uri?>(null)
    val pdfUri: MutableState<Uri?> = _pdfUri

    fun onPDFUriChanged(pdfUri: Uri?) {
        _pdfUri.value = pdfUri
    }

    private var _fileName = mutableStateOf<String?>(null)
    val fileName: MutableState<String?> = _fileName

    fun onFileNameChanged(fileName: String?) {
        _fileName.value = fileName
    }

    fun getFileName(context: Context, uri: Uri): String? {
        val documentFile = DocumentFile.fromSingleUri(context, uri)
        fileName.value = documentFile?.name
        return fileName.value
    }

    private val _visibilityOfBottomPDFSheet = mutableStateOf(false)
    val visibilityOfBottomPDFSheet: MutableState<Boolean> = _visibilityOfBottomPDFSheet

  @Composable
  fun PdfRendererViewer(pdfUri: Uri) {
    val context = LocalContext.current
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }

    DisposableEffect(pdfUri) {
      val parcelFileDescriptor = context.contentResolver.openFileDescriptor(pdfUri, "r")
      val pdfRenderer = PdfRenderer(parcelFileDescriptor!!)
      val page = pdfRenderer.openPage(0)

      try {
        val bitmapResult = Bitmap.createBitmap(page.width, page.height, Bitmap.Config.ARGB_8888)
        page.render(bitmapResult, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
        bitmap.value = bitmapResult
      } finally {
        page.close()
        pdfRenderer.close()
      }

      onDispose {
        parcelFileDescriptor.close()
      }
    }

    bitmap.value?.let {
      Image(
        bitmap = it.asImageBitmap(),
        contentDescription = "PDF Page",
        contentScale = ContentScale.Fit,
        modifier = Modifier.fillMaxSize()
      )
    }

    bitmap.value?.asImageBitmap()?.let { Image(bitmap = it, contentDescription = null) }
  }

  fun uploadPdfToFirebase(
    pdfUri: Uri?,
    context: Context,
    updatePatientInformationViewModel: UpdatePatientInformationViewModel,
    uuid: String
  ) {

    pdfUri?.let { uri ->
      val storageRef =
        Firebase.storage.reference.child("pdfs/$uuid/${updatePatientInformationViewModel.fileName.value}")
      val uploadTask = storageRef.putFile(uri)

      uploadTask.addOnSuccessListener {
        Reports(context).pdfUploadedSuccessfully()
        updatePatientInformationViewModel.onPDFUriChanged(null)
      }.addOnFailureListener {
        Reports(context).pdfUploadFailed()
      }
    }
  }


}