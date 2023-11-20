package com.example.dietforskin.data.profile.guest

open class Guest(
    open val username: String,
    open val password: String,
    open val email: String,
    open val accessToAdmin: Boolean = false,
    open val accessToPatient: Boolean = false
)
