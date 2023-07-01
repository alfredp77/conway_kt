package com.conway.tools

import com.conway.game.Cell
import com.conway.game.GameState

class ConsoleLiveCellsPrinter : LiveCellsPrinter {
    override fun print(prompt: String, gameState: GameState) {
        println(prompt)
        for (y in gameState.parameters.height downTo 1) {
            for (x in 1..gameState.parameters.width) {
                if (x > 1) {
                    print(" ")
                }
                if (Cell(x, y) in gameState.liveCells) {
                    print("o")
                } else {
                    print(".")
                }
            }
            println()
        }
    }

}