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

    @Test
    fun `wait for any key should display prompt if supplied`() {
        val consoleUserInputOutput = ConsoleUserInputOutput()

        consoleUserInputOutput.waitForAnyKey("Press any key to continue")

        assertEquals("Press any key to continue", consoleTestHelper.getOutput().trimEnd())
    }

    @AfterTest
    fun cleanUp() {
        consoleTestHelper.cleanUp()
    }
}