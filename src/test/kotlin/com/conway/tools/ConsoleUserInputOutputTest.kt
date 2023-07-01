package com.conway.tools

import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ConsoleUserInputOutputTest {
    private val consoleTestHelper = ConsoleTestHelper()

    @Test
    fun `should print line`() {
        val consoleUserInputOutput = ConsoleUserInputOutput()

        consoleUserInputOutput.displayLine("Hello World")

        assertEquals("Hello World", consoleTestHelper.getOutput().trimEnd())
    }

    @Test
    fun `should read line`() {
        val consoleUserInputOutput = ConsoleUserInputOutput()
        consoleTestHelper.setInput("Hello World")

        val line = consoleUserInputOutput.readLine()

        assertEquals("Hello World", line)
    }

    @AfterTest
    fun cleanUp() {
        consoleTestHelper.cleanUp()
    }
}