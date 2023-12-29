package com.example.dietforskin.data.profile.person

data class Admin(
    val name: String,
    val surname: String,
    val email: String,
    val role: String,
    val uuid: String,
    val listOfPatients: List<String>
)
