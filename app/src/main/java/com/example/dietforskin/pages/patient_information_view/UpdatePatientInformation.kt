package com.example.dietforskin.pages.patient_information_view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.documentfile.provider.DocumentFile
import com.example.dietforskin.R
import com.example.dietforskin.data.profile.PagesSite
import com.example.dietforskin.pages.CommonElements
import com.example.dietforskin.report.Reports
import com.example.dietforskin.ui.theme.colorCircle
import com.example.dietforskin.ui.theme.colorPinkMain
import com.example.dietforskin.viewmodels.ProfileViewModel
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import kotlinx.coroutines.delay
import java.util.UUID

@Composable
fun UpdatePatientInformation(profileViewModel: ProfileViewModel, context: Context) {
    profileViewModel.updateSelectionOfPagesSite(PagesSite.UPDATE_PROFILE)

    var pdfUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        pdfUri = uri
        uploadPdfToFirebase(pdfUri, context)
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
            PreviewPDFFile(pdfUri, context)

            Spacer(modifier = Modifier.height(20.dp))

            ElevatedButton(
                onClick = {
                    launcher.launch("application/pdf")
                },
                elevation = ButtonDefaults.buttonElevation(10.dp),
                colors = ButtonDefaults.buttonColors(
                    colorPinkMain
                )
            ) {
                Text(stringResource(id = R.string.upload_PDF_File), color = Color.Black)
            }
        }
    }
}

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
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        )
    }

    bitmap.value?.asImageBitmap()?.let { Image(bitmap = it, contentDescription = null) }
}

fun getFileName(context: Context, uri: Uri): String? {
    val documentFile = DocumentFile.fromSingleUri(context, uri)
    return documentFile?.name
}

private fun uploadPdfToFirebase(pdfUri: Uri?, context: Context) {

    pdfUri?.let { uri ->
        val storageRef = Firebase.storage.reference.child("pdfs/${"s"}.pdf") //TODO napis
        val uploadTask = storageRef.putFile(uri)

        uploadTask.addOnSuccessListener {
            Reports(context).pdfUploadedSuccessfully()
        }.addOnFailureListener {
            Reports(context).pdfUploadFailed()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun test() {
    val context = LocalContext.current
    val pdfUri by remember { mutableStateOf<Uri?>(null) }
    Box(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.border(1.dp, Color.Black))

        Canvas(modifier = Modifier.align(Alignment.TopEnd), onDraw = {
            drawCircle(
                color = colorCircle, radius = 450.dp.toPx()
            )
        })
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            PreviewPDFFile(pdfUri, context)
            Spacer(modifier = Modifier.height(20.dp))

            ElevatedButton(
                onClick = {

                },
                elevation = ButtonDefaults.buttonElevation(10.dp),
                colors = ButtonDefaults.buttonColors(
                    colorPinkMain
                )
            ) {
                Text(stringResource(id = R.string.upload_PDF_File), color = Color.Black)
            }
        }
    }
}

@Composable
fun PreviewPDFFile(pdfUri: Uri?, context: Context) {
    var showBitmap by remember { mutableStateOf(false) }

    LaunchedEffect(showBitmap) {
        delay(3000)
        showBitmap = true
    }

    Card(
        modifier = Modifier
            .width(220.dp)
            .height(220.dp),
        elevation = CardDefaults.cardElevation(20.dp)
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
                elevation = CardDefaults.cardElevation(8.dp)
            ) {

                if (showBitmap) {
                    if (pdfUri != null) {
                        PdfRendererViewer(pdfUri)
                    }
                }
            }

            if (pdfUri != null) {
                val fileName = getFileName(context, pdfUri)

                Text(text = "$fileName", modifier = Modifier.padding(vertical = 5.dp))
            }
        }
    }
}
