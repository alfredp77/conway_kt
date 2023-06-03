package com.conway.tools

enum class Commands (val value: String) {
    EXIT("#"),
    UNKNOWN("unknown");

    companion object {
        fun fromString(value: String): Commands {
            return when (value) {
                "#" -> EXIT
                else -> UNKNOWN
            }
        }
    }
}
