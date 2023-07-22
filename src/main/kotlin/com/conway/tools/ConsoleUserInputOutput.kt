package com.conway.tools


class ConsoleUserInputOutput : UserInputOutput {
    override fun displayLine(line: String) {
        println(line)
    }

    override fun readLine(): String {
        return readln()
    }

    override fun waitForAnyKey(exitPrompt: String) {
        System.`in`.read()
    }
}