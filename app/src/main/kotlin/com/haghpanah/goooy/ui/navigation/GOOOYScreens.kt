package com.haghpanah.goooy.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class GOOOYScreens {

    @Serializable
    data object Intention

    @Serializable
    data object Answer

    @Serializable
    data object Setting
}