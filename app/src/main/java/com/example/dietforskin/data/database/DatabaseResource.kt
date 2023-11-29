package com.example.dietforskin.data.database

sealed class DatabaseResource {
    data class Success(val reports: Unit? = null) : DatabaseResource()
    data class Error(val reports: Unit) : DatabaseResource()

}