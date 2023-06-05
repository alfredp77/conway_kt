package com.conway.actions

import com.conway.game.GameParameters
import com.conway.inputProcessors.InputProcessor
import com.conway.tools.Commands
import com.conway.tools.InvalidInputMessage
import com.conway.tools.UserInputOutput

class MenuAction(private val userInputOutput: UserInputOutput, private val inputProcessor: InputProcessor) {
    val description = inputProcessor.description
    val id = inputProcessor.id

    //val id get()
    fun execute(gameParameters: GameParameters): GameParameters {
        var current = inputProcessor.initialize(gameParameters)
        while (current.shouldContinue) {
            userInputOutput.displayLine(getPrompt(current.prompt))
            val input = userInputOutput.readLine()
            if (input == Commands.EXIT.value) {
                break;
            }

            current = inputProcessor.process(input, current.gameParameters)
            if (!current.isValid) {
                userInputOutput.displayLine(InvalidInputMessage)
            }
        }

        if (current.prompt.isNotEmpty()) {
            userInputOutput.displayLine(getExitPrompt(current.prompt))
        }
        return current.gameParameters
    }

    companion object {
        fun getPrompt(prompt: String) = "${prompt} or ${Commands.EXIT.value} to exit"
        fun getExitPrompt(prompt: String) = "${prompt}. Press any key to go back to main menu"
    }
}
