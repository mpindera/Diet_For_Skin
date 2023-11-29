package com.example.dietforskin.data.database

import com.example.dietforskin.data.profile.person.Person

interface DatabaseRepository {
    suspend fun addPersonToDatabase(person: Person): DatabaseResource

}