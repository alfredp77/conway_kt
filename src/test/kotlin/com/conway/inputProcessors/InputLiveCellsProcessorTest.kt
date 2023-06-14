package com.conway.inputProcessors

import com.conway.game.Cell
import com.conway.game.GameParameters
import com.conway.tools.Commands
import com.conway.tools.InvalidInputMessage
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class InputLiveCellsProcessorTest {
    private val inputLiveCellsProcessor = InputLiveCellsProcessor()

    @Test
    fun `should add parsed cell position into live cells positions`() {
        val gameParameters = GameParameters()
        val result = inputLiveCellsProcessor.process("1 2", gameParameters)
        assert(result.isValid)
        assert(result.shouldContinue)
        assert(result.gameParameters.liveCellsPositions.contains(Cell(1, 2)))
    }

    @Test
    fun `should return invalid input when input cannot be parsed`() {
        val gameParameters = GameParameters()
        val result = inputLiveCellsProcessor.process("invalid input", gameParameters)
        assert(!result.isValid)
        assert(result.shouldContinue)
        assertEquals(InvalidInputMessage, result.prompt)
        assertEquals(gameParameters, result.gameParameters)
    }

    @Test
    fun `should return invalid input when adding duplicate cell position`() {
        val gameParameters = GameParameters(liveCellsPositions = listOf(Cell(1, 2)))
        val result = inputLiveCellsProcessor.process("1 2", gameParameters)
        assert(!result.isValid)
        assert(result.shouldContinue)
        assertEquals(InvalidInputMessage, result.prompt)
        assertEquals(gameParameters, result.gameParameters)
    }

    @Test
    fun `should return valid input when adding cell position that is not duplicate`() {
        val gameParameters = GameParameters(liveCellsPositions = listOf(Cell(1, 2)))
        val result = inputLiveCellsProcessor.process("2 2", gameParameters)
        assert(result.isValid)
        assert(result.shouldContinue)
        assertEquals("", result.prompt)
        assertEquals(gameParameters.copy(liveCellsPositions = listOf(Cell(1, 2), Cell(2, 2))), result.gameParameters)
    }

    @Test
    fun `should clear all cells`() {
        val gameParameters = GameParameters(liveCellsPositions = listOf(Cell(1, 2)))
        val result = inputLiveCellsProcessor.process(Commands.CLEAR.value, gameParameters)
        assert(result.isValid)
        assert(result.shouldContinue)
        assertEquals("", result.prompt)
        assertEquals(gameParameters.copy(liveCellsPositions = emptyList()), result.gameParameters)
    }
}

