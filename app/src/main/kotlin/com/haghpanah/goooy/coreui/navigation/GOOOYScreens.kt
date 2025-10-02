package com.haghpanah.goooy.coreui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class GOOOYScreens {

    @Serializable
    data object Intention

    @Serializable
    data object Answer

    @Serializable
    data object OnBoardingLanguageSelector

    @Serializable
    data object OnBoardingThemeSelector
}