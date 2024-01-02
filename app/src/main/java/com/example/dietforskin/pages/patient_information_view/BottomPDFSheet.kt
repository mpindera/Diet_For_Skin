package com.example.dietforskin.pages.patient_information_view

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.dietforskin.R
import com.example.dietforskin.pages.CommonElements
import com.example.dietforskin.viewmodels.UpdatePatientInformationViewModel
import com.google.android.gms.tasks.Tasks
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomPDFSheet(
    updatePatientInformationViewModel: UpdatePatientInformationViewModel,
    visibilityOfBottomPDFSheet: MutableState<Boolean>,
    onDismissSheet: () -> Unit,
    uuid: String
) {

    ModalBottomSheet(onDismissRequest = {
        onDismissSheet()
    }, dragHandle = {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BottomSheetDefaults.DragHandle()
            Text(
                text = stringResource(id = R.string.list_of_PDF_File),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(10.dp))
            Divider()
        }
    }, modifier = Modifier
        .fillMaxSize()

    ) {
        BottomSheetContent(
            onHideButtonClick = { visibilityOfBottomPDFSheet.value = false },
            visibilityOfBottomPDFSheet = visibilityOfBottomPDFSheet,
            onDismissSheet = onDismissSheet,
            updatePatientInformationViewModel = updatePatientInformationViewModel,
            uuid = uuid
        )
    }
}

@Composable
fun BottomSheetContent(
    onHideButtonClick: () -> Unit,
    visibilityOfBottomPDFSheet: MutableState<Boolean>,
    onDismissSheet: () -> Unit,
    updatePatientInformationViewModel: UpdatePatientInformationViewModel,
    uuid: String
) {
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

    LazyColumn {
        items(pdfList) { pdf ->
            Column {
                Text(modifier = Modifier.padding(30.dp), text = pdf)
                Divider()
            }
        }
    }

}