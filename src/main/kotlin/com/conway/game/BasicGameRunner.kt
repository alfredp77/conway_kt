package com.conway.game

class BasicGameRunner : GameRunner {
    override fun generateInitialState(parameters: GameParameters): GameState {
        return GameState(parameters.liveCellsPositions, 0, parameters)
    }

    override fun generateNextState(currentState: GameState): GameState {
        val nextGeneration = currentState.generation + 1
        val currentLiveCells = currentState.liveCells.toHashSet()
        val nextAllCells = (currentState.liveCells.flatMap{
            filterCells(Cell.findAllNeighbours(it), currentState)
        } + currentState.liveCells).toHashSet()

        val nextLiveCells = nextAllCells.filter { cell ->
            val neighboursCount = filterCells(Cell.findLiveNeighbours(cell, currentLiveCells), currentState).count()
            neighboursCount == 3 || (neighboursCount == 2 && cell in currentLiveCells)
        }
        return GameState(nextLiveCells, nextGeneration, currentState.parameters)
    }

    private fun filterCells(cells: Sequence<Cell>, currentState: GameState): Sequence<Cell> {
        if (currentState.parameters.width == 0 || currentState.parameters.height == 0) return cells
        return cells.filter{ it.x in 1..currentState.parameters.width && it.y in 1..currentState.parameters.height}
    }
}