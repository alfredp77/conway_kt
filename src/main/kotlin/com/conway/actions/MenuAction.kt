package com.conway.actions

import com.conway.game.GameParameters
import com.conway.inputProcessors.InputProcessor
import com.conway.tools.Commands
import com.conway.tools.UserInputOutput

class MenuAction(private val userInputOutput: UserInputOutput, private val inputProcessor: InputProcessor) {
    fun execute(gameParameters: GameParameters): GameParameters {
        val initial = inputProcessor.initialize(gameParameters)
        userInputOutput.displayLine(initial.prompt)

        do {
            val input = userInputOutput.readLine()
            if (input == Commands.EXIT.value) {
                return gameParameters
            }

            val processedInput = inputProcessor.process(input)
            if (processedInput.isValid && !processedInput.shouldContinue) {
                return processedInput.gameParameters
            } else {
                userInputOutput.displayLine(processedInput.prompt)
            }
        } while (processedInput.shouldContinue)

        return gameParameters
    }

}
