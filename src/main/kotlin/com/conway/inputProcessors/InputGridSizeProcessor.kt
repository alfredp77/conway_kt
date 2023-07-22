package com.conway.inputProcessors

import com.conway.game.GameParameters
import com.conway.tools.InvalidInputMessage

class InputGridSizeProcessor : InputProcessor {
    override val id = "1"
    override val description = "Specify grid size"
    override val prompt = "Please enter grid size (width height)"
    override fun process(input: String, gameParameters: GameParameters): ProcessedInput {
        input.split(" ").let {
            if (it.size != 2) {
                return ProcessedInput.invalid(InvalidInputMessage, gameParameters)
            }
            val width = it[0].toIntOrNull()
            val height = it[1].toIntOrNull()
            if (width == null || height == null ||
                width < 1 || height < 1 ||
                (gameParameters.maxWidth in 1 until width) ||
                (gameParameters.maxHeight in 1 until height)) {
                return ProcessedInput.invalid(InvalidInputMessage, gameParameters)
            }
            return ProcessedInput.validAndExit("", gameParameters.copy(width = width, height = height))
        }
    }
}
