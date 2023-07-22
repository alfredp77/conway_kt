package com.conway.actions

import com.conway.game.GameParameters
import com.conway.inputProcessors.InputProcessor
import com.conway.tools.Commands
import com.conway.tools.InvalidInputMessage
import com.conway.tools.UserInputOutput

class MenuAction(private val userInputOutput: UserInputOutput, private val inputProcessor: InputProcessor) : Action {
    override val description = inputProcessor.description
    override val id = inputProcessor.id

    override fun execute(gameParameters: GameParameters): GameParameters {
        var current = inputProcessor.initialize(gameParameters)
        while (current.shouldContinue) {
            val prompt = current.prompt.ifEmpty { inputProcessor.prompt }
            userInputOutput.displayLine(getPrompt(prompt))
            val input = userInputOutput.readLine()
            if (input == Commands.EXIT.value) {
                break
            }

            current = inputProcessor.process(input, current.gameParameters)
            if (!current.isValid) {
                userInputOutput.displayLine(InvalidInputMessage)
            }
        }

        if (current.prompt.isNotEmpty()) {
            userInputOutput.waitForAnyKey(getExitPrompt(current.prompt))
        }
        return current.gameParameters
    }

    companion object {
        fun getPrompt(prompt: String) = "${prompt} or ${Commands.EXIT.value} to exit"
        fun getExitPrompt(prompt: String) = "${prompt}. Press any key to go back to main menu"
    }
}
