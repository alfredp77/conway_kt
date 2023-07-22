package com.conway.inputProcessors

import com.conway.game.GameParameters
import com.conway.tools.InvalidInputMessage

class InputNumberOfGenerationsProcessor : InputProcessor {
    override val id = "2"
    override val description = "Specify number of generations"
    override val prompt = "Please enter number of generations"
    override fun process(input: String, gameParameters: GameParameters): ProcessedInput {
        val generations = input.toIntOrNull()
        if (generations == null ||
            generations < 1 ||
            (gameParameters.minGenerations > 0 && generations < gameParameters.minGenerations) ||
            (gameParameters.maxGenerations in 1 until generations)) {
            return ProcessedInput.invalid(InvalidInputMessage, gameParameters)
        }
        return ProcessedInput.validAndExit("", gameParameters.copy(generations = generations))
    }
}