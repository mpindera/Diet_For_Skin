package com.example.dietforskin.report

import android.content.Context
import android.widget.Toast

class Reports(val context: Context) {
    fun errorFetchFromDatabase() {
        Toast.makeText(context, "Błąd podczas pobierania danych", Toast.LENGTH_LONG).show()
    }

    fun errorEmailDoesNotExists() {
        Toast.makeText(context, "Taki email nie istnieje.", Toast.LENGTH_SHORT).show()
    }

    fun errorForSavingToDatabase() {
        Toast.makeText(context, "Błąd podczas zapisu do bazy danych.", Toast.LENGTH_SHORT).show()
    }
    fun savedInDatabase() {
        Toast.makeText(context, "Dane zostały zapisane.", Toast.LENGTH_SHORT).show()
    }
    fun errorFillAllFields() {
        Toast.makeText(context, "Wypełnij wszystkie informacje.", Toast.LENGTH_SHORT).show()
    }
    fun u() {
        Toast.makeText(context, "s s s.", Toast.LENGTH_SHORT).show()
    }
    fun s() {
        Toast.makeText(context, "a a a.", Toast.LENGTH_SHORT).show()
    }
}