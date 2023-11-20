package com.example.dietforskin.data.profile.patient

import com.example.dietforskin.data.profile.guest.Guest

class Patient(
    override val username: String,
    override val password: String,
    override val email: String,
    override val accessToAdmin: Boolean = false,
    override val accessToPatient: Boolean = true
) : Guest(username, password, email, accessToAdmin, accessToPatient)