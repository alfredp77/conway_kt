package com.conway.tools

enum class Commands (val value: String) {
    EXIT("#"),
    UNKNOWN("unknown"),
    NEXT(">"),
    CLEAR("*");

    companion object {
        fun fromString(value: String): Commands {
            return when (value) {
                "*" -> CLEAR
                "#" -> EXIT
                ">" -> NEXT
                else -> UNKNOWN
            }
        }
    }
}
