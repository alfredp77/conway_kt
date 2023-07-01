package com.conway.tools

import java.io.ByteArrayInputStream
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

    fun setInput(input: String) {
        val inputStream = ByteArrayInputStream(input.toByteArray())

        // Redirect the standard input to the custom input stream
        System.setIn(inputStream)
    }

    fun cleanUp() {
        outputStream.close()
        System.setIn(System.`in`)
        System.setOut(System.out)
    }
}