package com.conway.actions

import com.conway.game.GameParameters

interface Action {
    val description: String
    val id: String
    fun execute(gameParameters: GameParameters): GameParameters
}