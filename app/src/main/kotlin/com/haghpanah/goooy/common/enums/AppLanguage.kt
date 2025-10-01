package com.haghpanah.goooy.common.enums

enum class AppLanguage(val id: Int, val tag: String) {
    Fa(1, "fa"),
    En(2, "en");

    companion object {
        fun getDefault() = Fa
    }
}