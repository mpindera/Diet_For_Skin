package com.example.dietforskin.report

import android.content.Context
import android.widget.Toast
import com.example.dietforskin.R

class Reports(val context: Context) {
    /** Login **/
    fun errorFetchFromDatabase() {
        Toast.makeText(context, context.getString(R.string.error_fetching_data), Toast.LENGTH_LONG)
            .show()
    }

    /** Login **/
    fun loggedSuccess(name: String) {
        Toast.makeText(context, "Hello, $name.", Toast.LENGTH_SHORT).show()
    }

    /** Register **/
    fun errorForSavingToDatabase() {
        Toast.makeText(
            context, context.getText(R.string.error_for_saving_to_database), Toast.LENGTH_SHORT
        ).show()
    }

    /** Register **/
    fun savedInDatabase() {
        Toast.makeText(context, context.getText(R.string.saved_in_database), Toast.LENGTH_SHORT)
            .show()
    }

    /** Register **/
    fun registerPerson() {
        Toast.makeText(context, context.getText(R.string.registe_person), Toast.LENGTH_SHORT).show()
    }

    /** Register **/
    fun errorRegisterPerson() {
        Toast.makeText(context, context.getText(R.string.error_register_person), Toast.LENGTH_SHORT)
            .show()
    }

    /** Login , Register , Forgot Password **/

    fun errorEmailDoesNotExists() {
        Toast.makeText(context, context.getString(R.string.email_not_exists), Toast.LENGTH_LONG)
            .show()
    }

    fun errorPasswordInvalid() {
        Toast.makeText(context, context.getString(R.string.email_not_exists), Toast.LENGTH_LONG)
            .show()
    }

    fun pdfUploadedSuccessfully() {
        Toast.makeText(context, context.getString(R.string.pdf_uploaded_successfully), Toast.LENGTH_SHORT).show()
    }

    fun pdfUploadFailed() {
        Toast.makeText(context, context.getString(R.string.pdf_upload_failed), Toast.LENGTH_SHORT).show()
    }


}