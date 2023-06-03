package com.conway.actions

import com.conway.game.GameParameters
import com.conway.inputProcessors.InputProcessor
import com.conway.tools.Commands
import com.conway.tools.UserInputOutput

class MenuAction(private val userInputOutput: UserInputOutput, private val inputProcessor: InputProcessor) {
    fun execute(gameParameters: GameParameters): GameParameters {
        val initial = inputProcessor.initialize(gameParameters)
        userInputOutput.displayLine(initial.prompt)

        val input = userInputOutput.readLine()
        if (input == Commands.EXIT.value) {
            return gameParameters
        }

        val processedInput = inputProcessor.process(input)
        return processedInput.gameParameters
    }

}
