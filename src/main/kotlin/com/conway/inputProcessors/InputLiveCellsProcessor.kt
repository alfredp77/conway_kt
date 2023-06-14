package com.conway.inputProcessors

import com.conway.game.Cell
import com.conway.game.GameParameters
import com.conway.tools.Commands
import com.conway.tools.InvalidInputMessage

class InputLiveCellsProcessor : InputProcessor {
    override val id = "3"
    override val description = "Please enter live cell position in x y format (example: 1 2), * to clear all the previously entered cells"

    override fun process(input: String, gameParameters: GameParameters): ProcessedInput {
        if (input == Commands.CLEAR.value) {
            return ProcessedInput.validAndContinue("", gameParameters.copy(liveCellsPositions = emptyList()))
        }

        val cell = parseCell(input)
        if (cell == null || gameParameters.liveCellsPositions.contains(cell)) {
            return ProcessedInput.invalid(InvalidInputMessage, gameParameters)
        }
        return ProcessedInput.validAndContinue("",
            gameParameters.copy(liveCellsPositions = gameParameters.liveCellsPositions + cell))
    }

    private fun parseCell(input: String): Cell? {
        input.split(" ").let {
            if (it.size != 2) {
                return null
            }
            val x = it[0].toIntOrNull()
            val y = it[1].toIntOrNull()
            if (x == null || y == null) {
                return null
            }
            return Cell(x, y)
        }
    }
}