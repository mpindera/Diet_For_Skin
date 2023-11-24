package com.example.dietforskin.report

import android.content.Context
import android.widget.Toast

class Reports(val context: Context) {
    fun errorFetchFromDatabase() {
        Toast.makeText(context, "Error fetch from database", Toast.LENGTH_LONG).show()
    }

    fun funkcjaB() {
        println("To jest funkcja B")
    }

    fun funkcjaC() {
        println("To jest funkcja C")
    }
}