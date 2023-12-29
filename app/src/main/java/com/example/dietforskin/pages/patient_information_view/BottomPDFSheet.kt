package com.example.dietforskin.pages.patient_information_view

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.dietforskin.R
import com.example.dietforskin.pages.CommonElements
import com.example.dietforskin.viewmodels.UpdatePatientInformationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomPDFSheet(
    uuid: String,
    updatePatientInformationViewModel: UpdatePatientInformationViewModel,
    visibilityOfBottomPDFSheet: MutableState<Boolean>,
    onDismissSheet: () -> Unit
) {

    ModalBottomSheet(onDismissRequest = {
        onDismissSheet()
    }, dragHandle = {
        Column(
            modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BottomSheetDefaults.DragHandle()
            Text(
                text = stringResource(id = R.string.list_of_PDF_File),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(10.dp))
            Divider()

        }
    }) {
        BottomSheetContent(
            onHideButtonClick = { visibilityOfBottomPDFSheet.value = false },
            visibilityOfBottomPDFSheet = visibilityOfBottomPDFSheet,
            onDismissSheet = onDismissSheet,
            uuid = uuid
        )
    }
}

@Composable
fun BottomSheetContent(
    onHideButtonClick: () -> Unit,
    visibilityOfBottomPDFSheet: MutableState<Boolean>,
    onDismissSheet: () -> Unit,
    uuid: String
) {
    CommonElements().db.collection("pdfs").document().get()
        .addOnSuccessListener { document ->
            if (document != null) {
                Log.d("TAGa", "DocumentSnapshot data: ${document.data}")
            } else {
                Log.d("TAGa", "No such document")
            }
        }
        .addOnFailureListener { exception ->
            Log.d("TAGa", "get failed with ", exception)
        }

    LazyColumn(contentPadding = PaddingValues(16.dp)) {
        item {
            Button(modifier = Modifier.fillMaxWidth(), onClick = {
                onHideButtonClick()
                onDismissSheet()
            }) {
                Text("Hide Bottom Sheet")
            }
        }

        items(20) {
            ListItem(headlineContent = {
                Text("Item $it")
            }, leadingContent = {
                Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null)
            })
        }
    }
}