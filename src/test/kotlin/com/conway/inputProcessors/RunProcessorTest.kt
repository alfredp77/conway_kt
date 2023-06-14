package com.conway.inputProcessors

import com.conway.game.GameParameters
import com.conway.game.GameRunner
import com.conway.game.GameState
import com.conway.tools.Commands
import com.conway.tools.NextGenerationPrompt
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class RunProcessorTest {

    private val gameRunner = mock<GameRunner>()
    private val runProcessor = RunProcessor(gameRunner)

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
}