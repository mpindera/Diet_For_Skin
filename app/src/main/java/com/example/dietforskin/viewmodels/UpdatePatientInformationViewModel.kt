package com.example.dietforskin.viewmodels

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.ViewModel

class UpdatePatientInformationViewModel : ViewModel() {

    private var _pdfUri = mutableStateOf<Uri?>(null)
    val pdfUri: MutableState<Uri?> = _pdfUri

    fun onPDFUriChanged(pdfUri: Uri?) {
        _pdfUri.value = pdfUri
    }

    fun getFileName(context: Context, uri: Uri): String? {
        val documentFile = DocumentFile.fromSingleUri(context, uri)
        return documentFile?.name
    }

}