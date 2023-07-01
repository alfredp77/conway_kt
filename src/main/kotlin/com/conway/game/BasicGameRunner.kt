package com.conway.game

class BasicGameRunner : GameRunner {
    override fun generateInitialState(parameters: GameParameters): GameState {
        return GameState(parameters.liveCellsPositions, 0, parameters)
    }

    override fun generateNextState(currentState: GameState): GameState {
        val nextGeneration = currentState.generation + 1
        val currentLiveCells = currentState.liveCells.toHashSet()
        val nextAllCells = (currentState.liveCells.flatMap{
            Cell.findAllNeighbours(it)
        } + currentState.liveCells).toHashSet()

        val nextLiveCells = nextAllCells.filter { cell ->
            val (neighbours, _) = Cell.findNeighbours(cell, currentLiveCells)
            neighbours.count() == 3 || (neighbours.count() == 2 && cell in currentLiveCells)
        }
        return GameState(nextLiveCells, nextGeneration, currentState.parameters)
    }
}