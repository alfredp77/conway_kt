package com.conway.inputProcessors

import com.conway.game.GameParameters

interface InputProcessor {
    fun initialize(gameParameters: GameParameters): ProcessedInput
}