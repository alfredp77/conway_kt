package com.conway.tools

import com.conway.game.GameState

interface LiveCellsPrinter {
    fun print(prompt:String, gameState:GameState)
}

