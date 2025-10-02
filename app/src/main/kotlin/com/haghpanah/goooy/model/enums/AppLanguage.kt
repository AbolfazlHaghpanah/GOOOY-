package com.haghpanah.goooy.model.enums

enum class AppLanguage(val tag: String) {
    Fa("fa"),
    En("en");

    companion object {
        fun getDefault() = Fa
    }
}