package com.conway.game

import kotlin.test.Test
import kotlin.test.assertEquals

class BasicGameRunnerTest {
    private val gameRunner = BasicGameRunner()
    private val parameters = GameParameters(generations = 2, liveCellsPositions = listOf(Cell(1, 1), Cell(2,2), Cell(1,2)))
    @Test
    fun `should generate initial state`() {
        val initialState = gameRunner.generateInitialState(parameters)

        assertEquals(parameters, initialState.parameters)
        assertEquals(parameters.liveCellsPositions, initialState.liveCells)
        assertEquals(0, initialState.generation)
    }

    @Test
    fun `should increment number of generation in next state`() {
        val initialState = gameRunner.generateInitialState(parameters)
        val nextState = gameRunner.generateNextState(initialState)
        val lastState = gameRunner.generateNextState(nextState)

        assertEquals(1, nextState.generation)
        assertEquals(2, lastState.generation)
    }

    @Test
    fun `cell with less than two neighbours should die in the next state`() {
        val state = GameState(listOf(Cell(2, 2)), 0, parameters)

        val nextState = gameRunner.generateNextState(state)

        assertEquals(emptyList(), nextState.liveCells)
    }

    @Test
    fun `cell with two neighbours should survive in the next state`() {
        val state = GameState(listOf(Cell(2, 2), Cell(1, 1), Cell(1, 2)), 0, parameters)

        val nextState = gameRunner.generateNextState(state)

        val deadCells = state.liveCells.filter { it !in nextState.liveCells }
        assertEquals(emptyList(), deadCells)
    }

    @Test
    fun `cell with three neighbours should survive in the next state`() {
        val state = GameState(listOf(Cell(2, 2), Cell(1, 1), Cell(1, 2), Cell(2, 1)), 0, parameters)

        val nextState = gameRunner.generateNextState(state)

        assertEquals(listOf(Cell(2, 2), Cell(1, 1), Cell(1, 2), Cell(2, 1)), nextState.liveCells)
    }

    @Test
    fun `cell with more than three neighbours should die in the next state`() {
        val state = GameState(listOf(Cell(2, 2), Cell(2, 3), Cell(3, 2), Cell(3, 3), Cell(4, 2)), 0, parameters)

        val nextState = gameRunner.generateNextState(state)

        val deadCells = state.liveCells.filter { it !in nextState.liveCells }

        assertEquals(listOf(Cell(3, 2), Cell(3, 3)), deadCells)
    }

    @Test
    fun `dead cell with three neighbours should become alive in the next state`() {
        val state = GameState(listOf(Cell(2, 2), Cell(2, 3), Cell(3, 2)), 0, parameters)

        val nextState = gameRunner.generateNextState(state)

        assertEquals(listOf(Cell(2, 2), Cell(2, 3), Cell(3, 2), Cell(3, 3)), nextState.liveCells)
    }
}