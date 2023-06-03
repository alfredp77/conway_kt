package com.conway.actions

import com.conway.game.GameParameters
import com.conway.inputProcessors.InputProcessor
import com.conway.inputProcessors.ProcessedInput
import com.conway.tools.Commands
import com.conway.tools.UserInputOutput
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import kotlin.test.assertEquals

abstract class MockInputProcessor : InputProcessor

class MenuActionTest {

    private val inputProcessor: MockInputProcessor = mock()
    private val userInputOutput: UserInputOutput = mock()
    @BeforeEach
    fun setUp() {
        whenever(inputProcessor.initialize(any())).thenCallRealMethod()
        whenever(inputProcessor.process(any())).thenCallRealMethod()
        whenever(userInputOutput.readLine()).thenReturn("")
    }


    @Test
    fun `should first display prompt from initialize result of input processor on execute`() {
        val gameParameters = GameParameters()
        whenever(inputProcessor.initialize(gameParameters)).thenReturn(ProcessedInput(
            "testPrompt",
            gameParameters
        ))
        val menuAction = MenuAction(userInputOutput, inputProcessor)

        menuAction.execute(gameParameters)

        verify(userInputOutput).displayLine("testPrompt")
    }

    @Test
    fun `should exit immediately on exit command`() {
        val gameParameters = GameParameters()
        whenever(userInputOutput.readLine()).thenReturn(Commands.EXIT.value)
        val menuAction = MenuAction(userInputOutput, inputProcessor)

        menuAction.execute(gameParameters)

        verify(inputProcessor, never()).process(any())
    }

    @Test
    fun `should return gameParameters from processed input`() {
        val gameParameters = GameParameters()
        whenever(userInputOutput.readLine()).thenReturn("testInput")
        val processedGameParameters = GameParameters(1)
        whenever(inputProcessor.process("testInput"))
            .thenReturn(ProcessedInput("", processedGameParameters))

        val menuAction = MenuAction(userInputOutput, inputProcessor)

        val result = menuAction.execute(gameParameters)

        assertEquals(processedGameParameters, result)
    }

    @Test
    fun `should ask for input again when it is invalid`() {
        val gameParameters = GameParameters()
        whenever(userInputOutput.readLine()).thenReturn("testInput", Commands.EXIT.value)
        val processedGameParameters = GameParameters(1)
        whenever(inputProcessor.process("testInput"))
            .thenReturn(ProcessedInput.invalid("wrong", processedGameParameters))

        val menuAction = MenuAction(userInputOutput, inputProcessor)

        val result = menuAction.execute(gameParameters)

        assertEquals(gameParameters, result)
    }
}