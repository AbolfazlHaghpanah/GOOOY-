package com.haghpanah.goooy.model

enum class ThemeStyle(val id: Int) {
    Dark(1),
    Light(2),
    SystemBased(3);

    companion object {
        fun getDefault() = Dark
    }
}