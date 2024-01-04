package com.example.dietforskin.viewmodels

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.dietforskin.data.profile.person.Patient
import com.example.dietforskin.pages.CommonElements
import com.google.android.gms.tasks.Tasks
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class PatientFilesViewModel : ViewModel() {


  fun downloadPDF(context: Context, namePDF: String, linkPDF: String): String {
    val pdfStorageRef = Firebase.storage.getReferenceFromUrl(linkPDF)
    val localPdfFile = File.createTempFile("pdf", "pdf")

    pdfStorageRef.getFile(localPdfFile).addOnSuccessListener {
      Toast.makeText(context, "Downloaded $namePDF", Toast.LENGTH_SHORT).show()
      Log.d("FirebaseStorage", "Downloaded PDF file path: ${localPdfFile.absolutePath}")

      val request =
        DownloadManager.Request(Uri.parse(linkPDF)).setTitle(namePDF).setDescription("Downloading")
          .setMimeType("application/pdf").setDestinationInExternalPublicDir(
            Environment.DIRECTORY_DOWNLOADS, namePDF
          ).setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)

      val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
      downloadManager.enqueue(request)

    }.addOnFailureListener { exception ->
      Toast.makeText(
        context, "Error downloading PDF: ${exception.message}", Toast.LENGTH_SHORT
      ).show()
    }
    return localPdfFile.absolutePath
  }

  @Composable
  fun fetchPDFWithName(uuid: String): Map<String, String> {
    var pdfMap by remember { mutableStateOf(emptyMap<String, String>()) }

    val storageRef = CommonElements().storage
    val pathRef = storageRef.child("$uuid/")

    DisposableEffect(Unit) {
      val job = CoroutineScope(Dispatchers.IO).launch {
        try {
          val listResult = Tasks.await(pathRef.listAll())
          val mapOfRes = mutableMapOf<String, String>()

          for (i in listResult.items) {
            val downloadUrl = Tasks.await(i.downloadUrl)
            mapOfRes[i.name] = downloadUrl.toString()
          }

          withContext(Dispatchers.Main) {
            pdfMap = mapOfRes
          }

        } catch (e: Exception) {
          println("$e")
        }
      }

      onDispose {
        job.cancel()
      }
    }
    return pdfMap
  }

  private val _visibilityOfPDFFiles = MutableStateFlow(true)
  val visibilityOfPDFFiles: StateFlow<Boolean> = _visibilityOfPDFFiles

  fun onVisibilityOfPDFFilesChanged(visibilityPDFFiles: Boolean) {
    _visibilityOfPDFFiles.value = visibilityPDFFiles
  }

  private val _visibilityOfAnimation = MutableStateFlow(false)
  val visibilityOfAnimation: StateFlow<Boolean> = _visibilityOfAnimation

  fun onVisibilityChanged(visibility: Boolean) {
    _visibilityOfAnimation.value = visibility
  }


}