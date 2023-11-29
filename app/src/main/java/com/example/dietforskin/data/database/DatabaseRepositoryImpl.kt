package com.example.dietforskin.data.database

import android.content.Context
import com.example.dietforskin.data.profile.person.Person
import com.example.dietforskin.report.Reports
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class DatabaseRepositoryImpl(private val database: Firebase, private val context: Context) :
    DatabaseRepository {
    override suspend fun addPersonToDatabase(person: Person): DatabaseResource {
        return try {
            database.firestore.collection("users").add(person)
            DatabaseResource.Success(reports = Reports(context = context).savedInDatabase())
        } catch (e: Exception) {
            DatabaseResource.Error(reports = Reports(context = context).errorForSavingToDatabase())
        }
    }

}