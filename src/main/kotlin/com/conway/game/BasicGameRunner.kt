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
            val neighboursCount = Cell.findLiveNeighbours(cell, currentLiveCells).count()
            neighboursCount == 3 || (neighboursCount == 2 && cell in currentLiveCells)
        }
        return GameState(nextLiveCells, nextGeneration, currentState.parameters)
    }
}