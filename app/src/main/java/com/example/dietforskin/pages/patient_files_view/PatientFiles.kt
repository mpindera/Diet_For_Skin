package com.example.dietforskin.pages.patient_files_view

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.dietforskin.viewmodels.PatientFilesViewModel

@Composable
fun PatientFiles(patientFilesViewModel: PatientFilesViewModel, context: Context, uuid: String) {
  Column {
    Text(text = uuid)
  }
}