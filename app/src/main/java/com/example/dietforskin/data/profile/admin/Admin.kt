package com.example.dietforskin.data.profile.admin

import com.example.dietforskin.data.profile.guest.Guest


class Admin(
    override val username: String,
    override val password: String,
    override val email: String,
    override val accessToAdmin: Boolean = true,
    override val accessToPatient: Boolean = true
) : Guest(username, password, email, accessToAdmin,accessToPatient)
