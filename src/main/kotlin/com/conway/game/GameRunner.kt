package com.conway.game

interface GameRunner {
    fun generateInitialState(parameters: GameParameters): GameState
    fun generateNextState(currentState: GameState): GameState
}
