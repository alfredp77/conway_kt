package com.conway.tools

import java.io.ByteArrayOutputStream
import java.io.PrintStream

class ConsoleTestHelper {
    private val outputStream: ByteArrayOutputStream = ByteArrayOutputStream()
    init {
        val printStream = PrintStream(outputStream)
        System.setOut(printStream)
    }

    fun getOutput(): String {
        return outputStream.toString().replace(System.lineSeparator(), "\n")
    }
}