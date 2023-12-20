package com.example.dietforskin.data.profile.person

data class Admin(
    val username: String,
    val email: String,
    val role: String,
    val uuid: String,
    val listOfPatients: List<String>
)
