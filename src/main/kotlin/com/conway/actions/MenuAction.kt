package com.conway.actions

import com.conway.game.GameParameters
import com.conway.inputProcessors.InputProcessor
import com.conway.tools.UserInputOutput

class MenuAction(private val userInputOutput: UserInputOutput, private val inputProcessor: InputProcessor) {
    fun execute(gameParameters: GameParameters) {
        val processedInput = inputProcessor.initialize(gameParameters)
        userInputOutput.displayLine(processedInput.prompt)
    }

}
