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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.dietforskin.R
import com.example.dietforskin.pages.CommonElements
import com.google.android.gms.tasks.Tasks
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.File

class PatientFilesViewModel : ViewModel() {


  fun downloadPDF(context: Context, namePDF: String, linkPDF: String) {

    val pdfStorageRef = Firebase.storage.getReferenceFromUrl(linkPDF)
    val localPdfFile = File.createTempFile("pdf", "pdf")

    val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
    val fullPath = File(directory, namePDF).absolutePath
    try {
      pdfStorageRef.getFile(localPdfFile).addOnSuccessListener {
        runBlocking {
          checkPDF(fullPath, namePDF) { (isSuccess, value) ->
            if (isSuccess) {
              downloadPDFToPhone(linkPDF, namePDF, context)
            } else {
              if (isFileExists(value.toString())) {
                onVisibleOfPDFViewChanged(true)
                onPathPdfChanged(value.toString())
                onNamePdfChanged(namePDF)
              } else {
                downloadPDFToPhone(linkPDF, namePDF, context)
              }
            }
          }
        }
      }.addOnFailureListener { exception ->
        Toast.makeText(
          context, context.getString(R.string.downloaded_message, namePDF), Toast.LENGTH_SHORT
        ).show()
      }
    } catch (e: Exception) {
      Log.d("ERROR", e.toString())
    }
  }

  private fun isFileExists(filePath: String): Boolean {
    val file = File(filePath)
    return file.exists()
  }

  private suspend fun checkPDF(
    absolutePath: String, namePDF: String, callback: (Pair<Boolean, String?>) -> Unit
  ) {
    val mAuthCurrentAdminEmail = CommonElements().mAuth?.email
    val changedNameFilePDF = namePDF.replace(".", "_")

    for (document in CommonElements().dbGet.await()) {
      val userData = document.data
      val userEmail = userData["email"].toString()
      val role = userData["role"].toString()

      if (userEmail == mAuthCurrentAdminEmail && role == CommonElements().patient) {
        val userDocumentRef = Firebase.firestore.collection("users").document(document.id)

        if (userData.containsKey(changedNameFilePDF)) {
          for (i in userData.entries) {
            if (i.key == changedNameFilePDF) {
              callback(Pair(false, i.value.toString()))
            }
          }
        } else {
          userDocumentRef.update(changedNameFilePDF, absolutePath).addOnSuccessListener {
            callback(Pair(true, null))
          }.addOnFailureListener { e ->
            Log.e("testa", "Error adding field", e)
            callback(Pair(false, null))
          }
        }
        return
      }
    }
    callback(Pair(false, null))
  }


  private fun downloadPDFToPhone(linkPDF: String, namePDF: String, context: Context) {
    Toast.makeText(
      context,
      context.getString(R.string.downloaded_message, namePDF),
      Toast.LENGTH_SHORT
    ).show()
    val request =
      DownloadManager.Request(Uri.parse(linkPDF)).setTitle(namePDF).setDescription("Downloading")
        .setMimeType("application/pdf").setDestinationInExternalPublicDir(
          Environment.DIRECTORY_DOWNLOADS, namePDF
        ).setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)

    val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    downloadManager.enqueue(request)
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

  private val _visibleOfPDFView = MutableStateFlow(false)
  val visibleOfPDFView: StateFlow<Boolean> = _visibleOfPDFView

  fun onVisibleOfPDFViewChanged(visibleOfPDFView: Boolean) {
    _visibleOfPDFView.value = visibleOfPDFView
    Log.d("testa", "_visibleOfPDFView updated: ${_visibleOfPDFView.value}")
  }

  private val _pathPdf = MutableStateFlow("")
  val pathPdf: StateFlow<String> = _pathPdf

  fun onPathPdfChanged(pathPdf: String) {
    _pathPdf.value = pathPdf
    Log.d("testa", "path updated: ${_pathPdf.value}")

  }
  private val _namePdf = MutableStateFlow("")
  val namePdf: StateFlow<String> = _namePdf

  fun onNamePdfChanged(namePdf: String) {
    _namePdf.value = namePdf
  }
}