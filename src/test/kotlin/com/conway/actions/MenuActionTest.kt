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
        whenever(inputProcessor.process(any(), any())).thenCallRealMethod()
        whenever(userInputOutput.readLine()).thenReturn("")
    }


    @Test
    fun `should first display prompt from initialize result of input processor on execute`() {
        val gameParameters = GameParameters()
        whenever(inputProcessor.initialize(gameParameters)).thenReturn(ProcessedInput.validAndContinue(
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

        verify(inputProcessor, never()).process(any(), any())
    }

    @Test
    fun `should return gameParameters from processed input`() {
        val gameParameters = GameParameters()
        whenever(userInputOutput.readLine()).thenReturn("testInput")
        val processedGameParameters = GameParameters(1)
        whenever(inputProcessor.process("testInput", gameParameters))
            .thenReturn(ProcessedInput.validAndExit("", processedGameParameters))

        val menuAction = MenuAction(userInputOutput, inputProcessor)

        val result = menuAction.execute(gameParameters)

        assertEquals(processedGameParameters, result)
    }

    @Test
    fun `should ask for input again when it is invalid`() {
        val gameParameters = GameParameters()
        whenever(userInputOutput.readLine()).thenReturn("testInput", Commands.EXIT.value)
        val processedGameParameters = GameParameters(1)
        whenever(inputProcessor.process("testInput", gameParameters))
            .thenReturn(ProcessedInput.invalid("wrong", processedGameParameters))

        val menuAction = MenuAction(userInputOutput, inputProcessor)

        val result = menuAction.execute(gameParameters)

        assertEquals(processedGameParameters, result)
    }

    @Test
    fun `should ask for input again when it is valid and needs more input`() {
        val gameParameters = GameParameters()
        whenever(userInputOutput.readLine()).thenReturn("testInput1", "testInput2")
        val processedGameParameters = GameParameters(1)
        val exitGameParameters = GameParameters(2)
        whenever(inputProcessor.process("testInput1", gameParameters))
            .thenReturn(ProcessedInput.validAndContinue("", processedGameParameters))
        whenever(inputProcessor.process("testInput2", processedGameParameters))
            .thenReturn(ProcessedInput.validAndExit("", exitGameParameters))

        val menuAction = MenuAction(userInputOutput, inputProcessor)

        val result = menuAction.execute(gameParameters)

        assertEquals(exitGameParameters, result)
    }
}