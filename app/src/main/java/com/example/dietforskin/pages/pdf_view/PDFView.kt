package com.example.dietforskin.pages.pdf_view

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.example.dietforskin.data.profile.PagesSite
import com.example.dietforskin.viewmodels.ProfileViewModel
import java.io.File
import java.io.IOException

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PDFView(pathPDF: String, namePdf: String, profileViewModel: ProfileViewModel) {
  var pdfBitmaps by remember { mutableStateOf<List<ImageBitmap>>(emptyList()) }
  var currentPage by remember { mutableStateOf(0) }

  profileViewModel.updateSelectionOfPagesSite(PagesSite.DOCUMENT_VIEW_PATIENT)

  DisposableEffect(Unit) {
    try {
      val fileDescriptor = ParcelFileDescriptor.open(
        File(pathPDF),
        ParcelFileDescriptor.MODE_READ_ONLY
      )
      val pdfRenderer = PdfRenderer(fileDescriptor)

      val pageCount = pdfRenderer.pageCount
      val bitmaps = mutableListOf<ImageBitmap>()

      for (i in 0 until pageCount) {
        val page = pdfRenderer.openPage(i)
        val bitmap = Bitmap.createBitmap(
          page.width,
          page.height,
          Bitmap.Config.ARGB_8888
        )
        page.render(
          bitmap,
          null,
          null,
          PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY
        )
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
      val pagerState = rememberPagerState(pageCount = { pdfBitmaps.size })

      HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
      ) { page ->
        currentPage = page

        Image(
          bitmap = pdfBitmaps[page],
          contentDescription = null,
          modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        )
      }
      Text(
        text = namePdf,
        modifier = Modifier
          .align(Alignment.TopCenter)
          .padding(16.dp),
        color = MaterialTheme.colorScheme.primary
      )
    } else {
      Text(
        text = "Błąd wczytywania pliku PDF",
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.error
      )
    }
  }
}

@Composable
fun DotIndicator(selected: Boolean) {
  val color = if (selected) Color.Red else Color.Gray

  Box(
    modifier = Modifier
      .size(8.dp)
      .clip(CircleShape)
      .background(color)
      .padding(4.dp)
  )
}
