package com.conway.tools

interface UserInputOutput {
    fun displayLine(line: String)
    fun readLine(): String
    fun waitForAnyKey(exitPrompt: String = "")
}

