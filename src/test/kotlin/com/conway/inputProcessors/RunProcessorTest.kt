package com.conway.inputProcessors

import com.conway.game.GameParameters
import com.conway.game.GameRunner
import com.conway.game.GameState
import com.conway.tools.Commands
import com.conway.tools.LiveCellsPrinter
import com.conway.tools.NextGenerationPrompt
import kotlin.test.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class RunProcessorTest {

    private val gameRunner = mock<GameRunner>()
    private val printer = mock<LiveCellsPrinter>()

    private val runProcessor = RunProcessor(gameRunner, printer)
    @Test
    fun `should generate initial state on initialize`() {
        val parameters = GameParameters(generations = 2)
        val initialState = GameState()
        whenever(gameRunner.generateInitialState(parameters)).thenReturn(initialState)

        val processedInput = runProcessor.initialize(parameters)

        assertEquals(initialState, runProcessor.currentState)
        assertEquals(NextGenerationPrompt, processedInput.prompt)
        assert(processedInput.shouldContinue)
        assert(processedInput.isValid)
    }

    @Test
    fun `should display initial position on initialize`() {
        val parameters = GameParameters(generations = 2)
        val initialState = GameState()
        whenever(gameRunner.generateInitialState(parameters)).thenReturn(initialState)

        runProcessor.initialize(parameters)

        verify(printer, times(1)).print("Initial position", initialState)
    }

    @Test
    fun `should generate next state when requested`() {
        val parameters = GameParameters(generations = 2)
        val initialState = GameState()
        val nextState = GameState()
        whenever(gameRunner.generateInitialState(parameters)).thenReturn(initialState)
        whenever(gameRunner.generateNextState(initialState)).thenReturn(nextState)

        runProcessor.initialize(parameters)
        val processedInput = runProcessor.process(Commands.NEXT.value, parameters)

        assertEquals(nextState, runProcessor.currentState)
        assertEquals(NextGenerationPrompt, processedInput.prompt)
        assert(processedInput.shouldContinue)
        assert(processedInput.isValid)
    }

    @Test
    fun `should display generated position on process`() {
        val parameters = GameParameters(generations = 2)
        val initialState = GameState()
        whenever(gameRunner.generateInitialState(parameters)).thenReturn(initialState)
        val nextState = GameState(generation = 1)
        whenever(gameRunner.generateNextState(initialState)).thenReturn(nextState)

        runProcessor.initialize(parameters)
        runProcessor.process(Commands.NEXT.value, parameters)

        verify(printer, times(1)).print("Generation 1", nextState)
    }

    @Test
    fun `should stop generating when max number of generations is reached`() {
        val parameters = GameParameters(generations = 2)
        val initialState = GameState()
        val state1 = GameState(generation=1)
        val state2 = GameState(generation=2)
        whenever(gameRunner.generateInitialState(parameters)).thenReturn(initialState)
        whenever(gameRunner.generateNextState(initialState)).thenReturn(state1)
        whenever(gameRunner.generateNextState(state1)).thenReturn(state2)

        runProcessor.initialize(parameters)
        runProcessor.process(Commands.NEXT.value, parameters)
        val processedInput = runProcessor.process(Commands.NEXT.value, parameters)

        assertEquals(state2, runProcessor.currentState)
        assertEquals(NextGenerationPrompt, processedInput.prompt)
        assertFalse(processedInput.shouldContinue)
        assert(processedInput.isValid)
        verify(printer, times(1)).print("Generation 1", state1)
        verify(printer, times(1)).print("Generation 2", state2)
    }

    @Test
    fun `should prompt again when invalid input is received`() {
        val parameters = GameParameters(generations = 2)
        val initialState = GameState()
        whenever(gameRunner.generateInitialState(parameters)).thenReturn(initialState)

        runProcessor.initialize(parameters)
        val processedInput = runProcessor.process("some invalid input", parameters)

        assertEquals("", processedInput.prompt)
        assert(processedInput.shouldContinue)
        assertFalse(processedInput.isValid)
    }
}