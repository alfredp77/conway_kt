package com.conway.inputProcessors

import com.conway.game.GameParameters

interface InputProcessor {
    fun initialize(gameParameters: GameParameters): ProcessedInput {
        return ProcessedInput("", gameParameters)
    }
    fun process(input: String): ProcessedInput {
        return ProcessedInput("", GameParameters())
    }
}