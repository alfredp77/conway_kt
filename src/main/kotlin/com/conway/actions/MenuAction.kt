package com.conway.actions

import com.conway.game.GameParameters
import com.conway.inputProcessors.InputProcessor
import com.conway.tools.Commands
import com.conway.tools.UserInputOutput

class MenuAction(private val userInputOutput: UserInputOutput, private val inputProcessor: InputProcessor) {
    fun execute(gameParameters: GameParameters): GameParameters {
        var current = inputProcessor.initialize(gameParameters)
        userInputOutput.displayLine(current.prompt)

        while (current.shouldContinue) {
            val input = userInputOutput.readLine()
            if (input == Commands.EXIT.value) {
                break;
            }

            current = inputProcessor.process(input, current.gameParameters)
            if (!current.isValid) {
                userInputOutput.displayLine(current.prompt)
            }
        }

        return current.gameParameters
    }

}
