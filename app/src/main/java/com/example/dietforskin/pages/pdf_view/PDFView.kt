package com.example.dietforskin.pages.pdf_view

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.paging.PagingSource
import java.io.File
import java.io.IOException

@Composable
fun PDFView() {
  var pdfBitmaps by remember { mutableStateOf<List<ImageBitmap>>(emptyList()) }

  DisposableEffect(Unit) {
    try {
      val fileDescriptor = ParcelFileDescriptor.open(File("link"), ParcelFileDescriptor.MODE_READ_ONLY)
      val pdfRenderer = PdfRenderer(fileDescriptor)

      val pageCount = pdfRenderer.pageCount
      val bitmaps = mutableListOf<ImageBitmap>()

      for (i in 0 until pageCount) {
        val page = pdfRenderer.openPage(i)
        val bitmap = Bitmap.createBitmap(page.width, page.height, Bitmap.Config.ARGB_8888)
        page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
        bitmaps.add(bitmap.asImageBitmap())
        page.close()
      }

      pdfRenderer.close()

      pdfBitmaps = bitmaps
    } catch (e: IOException) {
      e.printStackTrace()
    }

    onDispose { }
  }

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
  ) {
    if (pdfBitmaps.isNotEmpty()) {
      LazyColumn {
        items(pdfBitmaps) { bitmap ->
          Image(
            bitmap = bitmap,
            contentDescription = null,
            modifier = Modifier
              .fillMaxSize()
          )
        }
      }
    } else {
      Text(
        text = "Błąd wczytywania pliku PDF",
        fontWeight = FontWeight.Bold,
        modifier = Modifier
          .fillMaxSize()
          .padding(16.dp),
        color = MaterialTheme.colorScheme.error
      )
    }
  }
}