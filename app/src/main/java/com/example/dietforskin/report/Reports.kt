package com.example.dietforskin.report

import android.content.Context
import android.widget.Toast
import com.example.dietforskin.R

class Reports(val context: Context) {
    fun errorFetchFromDatabase() {
        Toast.makeText(context, context.getString(R.string.error_fetching_data), Toast.LENGTH_LONG).show()
    }

    fun errorEmailDoesNotExists() {
        Toast.makeText(context, context.getString(R.string.email_not_exists), Toast.LENGTH_LONG).show()
    }

    fun errorForSavingToDatabase() {
        Toast.makeText(context, context.getText(R.string.error_for_saving_to_database), Toast.LENGTH_SHORT).show()
    }
    fun errorFillAllFields() {
        Toast.makeText(context, context.getText(R.string.error_fill_all_fields), Toast.LENGTH_SHORT).show()
    }
    fun savedInDatabase() {
        Toast.makeText(context, context.getText(R.string.saved_in_database), Toast.LENGTH_SHORT).show()
    }
    fun registerPerson() {
        Toast.makeText(context, context.getText(R.string.registe_person), Toast.LENGTH_SHORT).show()
    }
    fun errorRegisterPerson() {
        Toast.makeText(context, context.getText(R.string.error_register_person), Toast.LENGTH_SHORT).show()
    }
    fun loggedSuccess(username: String) {
        Toast.makeText(context, "Hello, $username.", Toast.LENGTH_SHORT).show()
    }
}