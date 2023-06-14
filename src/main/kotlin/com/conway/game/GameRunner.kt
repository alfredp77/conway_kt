package com.conway.game

interface GameRunner {
    fun generateInitialState(parameters: GameParameters): GameState
    fun generateNextState(initialState: GameState): GameState
}
