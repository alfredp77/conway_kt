package com.conway.inputProcessors

import com.conway.game.GameParameters
import com.conway.game.GameRunner
import com.conway.game.GameState
import com.conway.tools.Commands
import com.conway.tools.LiveCellsPrinter
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class RunProcessorTest {

    private val gameRunner = mockk<GameRunner>(relaxed = true)
    private val printer = mockk<LiveCellsPrinter>(relaxed = true)

    private val runProcessor = RunProcessor(gameRunner, printer)
    @Test
    fun `should generate initial state on initialize`() {
        val parameters = GameParameters(generations = 2)
        val initialState = GameState()
        every { gameRunner.generateInitialState(parameters) } returns initialState

        val processedInput = runProcessor.initialize(parameters)

        assertEquals(initialState, runProcessor.currentState)
        assertEquals(runProcessor.nextGenerationPrompt, processedInput.prompt)
        assert(processedInput.shouldContinue)
        assert(processedInput.isValid)
    }

    @Test
    fun `should display initial position on initialize`() {
        val parameters = GameParameters(generations = 2)
        val initialState = GameState()
        every { gameRunner.generateInitialState(parameters) } returns initialState

        runProcessor.initialize(parameters)

        verify (exactly = 1) {printer.print("Initial position", initialState)}
    }

    @Test
    fun `should generate next state when requested`() {
        val parameters = GameParameters(generations = 2)
        val initialState = GameState()
        val nextState = GameState()
        every { gameRunner.generateInitialState(parameters) } returns initialState
        every { gameRunner.generateNextState(initialState) } returns nextState

        runProcessor.initialize(parameters)
        val processedInput = runProcessor.process(Commands.NEXT.value, parameters)

        assertEquals(nextState, runProcessor.currentState)
        assertEquals(runProcessor.nextGenerationPrompt, processedInput.prompt)
        assert(processedInput.shouldContinue)
        assert(processedInput.isValid)
    }

    @Test
    fun `should display generated position on process`() {
        val parameters = GameParameters(generations = 2)
        val initialState = GameState()
        every { gameRunner.generateInitialState(parameters) } returns initialState
        val nextState = GameState(generation = 1)
        every { gameRunner.generateNextState(initialState) } returns nextState

        runProcessor.initialize(parameters)
        runProcessor.process(Commands.NEXT.value, parameters)

        verify (exactly = 1) { printer.print("Generation 1", nextState) }
    }

    @Test
    fun `should stop generating when max number of generations is reached`() {
        val parameters = GameParameters(generations = 2)
        val initialState = GameState()
        val state1 = GameState(generation=1)
        val state2 = GameState(generation=2)
        every { gameRunner.generateInitialState(parameters) } returns initialState
        every { gameRunner.generateNextState(initialState) } returns state1
        every { gameRunner.generateNextState(state1) } returns state2

        runProcessor.initialize(parameters)
        runProcessor.process(Commands.NEXT.value, parameters)
        val processedInput = runProcessor.process(Commands.NEXT.value, parameters)

        assertEquals(state2, runProcessor.currentState)
        assertEquals(runProcessor.endGenerationPrompt, processedInput.prompt)
        assertFalse(processedInput.shouldContinue)
        assert(processedInput.isValid)
        verify (exactly = 1) { printer.print("Generation 1", state1) }
        verify (exactly = 1) { printer.print("Generation 2", state2) }
    }

    @Test
    fun `should prompt again when invalid input is received`() {
        val parameters = GameParameters(generations = 2)
        val initialState = GameState()
        every { gameRunner.generateInitialState(parameters) } returns initialState

        runProcessor.initialize(parameters)
        val processedInput = runProcessor.process("some invalid input", parameters)

        assertEquals("", processedInput.prompt)
        assert(processedInput.shouldContinue)
        assertFalse(processedInput.isValid)
    }
}