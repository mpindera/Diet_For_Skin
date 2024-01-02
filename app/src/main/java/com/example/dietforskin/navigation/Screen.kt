package com.example.dietforskin.navigation

sealed class Screen(val route: String) {
    object Splash: Screen("splash_screen")
    object PatientInformation: Screen("patient_information/{uuid}")
    object PatientFiles: Screen("patient_files/{uuid}")

}
