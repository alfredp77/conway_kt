package com.conway.actions

import com.conway.game.GameParameters

class QuitAction : Action{
    override val description = "Quit"
    override val id = "5"

    override fun execute(gameParameters: GameParameters): GameParameters {
        return GameParameters(exit = true)
    }
}