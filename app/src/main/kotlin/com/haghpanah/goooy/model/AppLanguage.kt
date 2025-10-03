package com.haghpanah.goooy.model

enum class AppLanguage(val tag: String) {
    Fa("fa"),
    En("en");

    companion object {
        fun getDefault() = Fa
    }
}