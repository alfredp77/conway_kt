package com.conway.actions

import com.conway.game.GameParameters
import com.conway.inputProcessors.InputProcessor
import com.conway.inputProcessors.ProcessedInput
import com.conway.tools.Commands
import com.conway.tools.InvalidInputMessage
import com.conway.tools.UserInputOutput
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

abstract class MockInputProcessor : InputProcessor

class MenuActionTest {

    private val userInputOutput = mockk<UserInputOutput>()
    private val inputProcessor = mockk<MockInputProcessor>()
    @BeforeTest
    fun setUp() {
        every { inputProcessor.id } returns "1"
        every { inputProcessor.description } returns "test"
        every { inputProcessor.prompt } returns "defaultPrompt"
        every { inputProcessor.initialize(any()) } answers { callOriginal() }
        every  { inputProcessor.process(any(), any()) } answers { callOriginal() }
        every  { userInputOutput.readLine() } returns ""
        every { userInputOutput.displayLine(any()) } answers { callOriginal() }
        every { userInputOutput.waitForAnyKey(any()) } answers { callOriginal() }
    }


    @Test
    fun `should use id and description from inputProcessor`() {
        every { inputProcessor.id } returns "testId"
        every { inputProcessor.description } returns "testDescription"

        val menuAction = MenuAction(userInputOutput, inputProcessor)

        assertEquals("testId", menuAction.id)
        assertEquals("testDescription", menuAction.description)
    }

    @Test
    fun `should first display prompt from initialize result of input processor on execute`() {
        val gameParameters = GameParameters()
        every { inputProcessor.initialize(any()) } returns ProcessedInput.validAndContinue(
            "testPrompt",
            gameParameters
        )

        val menuAction = MenuAction(userInputOutput, inputProcessor)

        menuAction.execute(gameParameters)

        verify { userInputOutput.displayLine(MenuAction.getPrompt("testPrompt"))}
    }

    @Test
    fun `should use prompt from processor if initialize result is empty`() {
        val gameParameters = GameParameters()
        every { inputProcessor.initialize(any()) } returns ProcessedInput.validAndContinue(
            "",
            gameParameters
        )
        every { inputProcessor.prompt } returns "testPrompt"

        val menuAction = MenuAction(userInputOutput, inputProcessor)

        menuAction.execute(gameParameters)

        verify { userInputOutput.displayLine(MenuAction.getPrompt("testPrompt"))}
    }

    @Test
    fun `should exit immediately on exit command`() {
        val gameParameters = GameParameters()
        every {  userInputOutput.readLine() } returns Commands.EXIT.value

        val menuAction = MenuAction(userInputOutput, inputProcessor)

        menuAction.execute(gameParameters)

        verify (inverse = true) { inputProcessor.process(any(), any()) }
    }

    @Test
    fun `should return gameParameters from processed input`() {
        val gameParameters = GameParameters(1,)
        every {userInputOutput.readLine()} returns "testInput"
        val processedGameParameters = GameParameters(2,)
        every {inputProcessor.process("testInput", gameParameters)} returns ProcessedInput.validAndExit("", processedGameParameters)

        val menuAction = MenuAction(userInputOutput, inputProcessor)

        val result = menuAction.execute(gameParameters)

        assertEquals(processedGameParameters, result)
    }

    @Test
    fun `should ask for input again when it is invalid`() {
        val gameParameters = GameParameters(1,)
        every { userInputOutput.readLine() } returnsMany listOf("testInput", Commands.EXIT.value)
        val processedGameParameters = GameParameters(2,)
        every { inputProcessor.process("testInput", gameParameters) } returns ProcessedInput.invalid("wrong", processedGameParameters)

        val menuAction = MenuAction(userInputOutput, inputProcessor)

        val result = menuAction.execute(gameParameters)

        assertEquals(processedGameParameters, result)
        verify (exactly = 1) { userInputOutput.displayLine(InvalidInputMessage) }
    }

    @Test
    fun `should ask for input again when it is valid and needs more input`() {
        val gameParameters = GameParameters()
        every { userInputOutput.readLine() } returnsMany listOf("testInput1", "testInput2")
        val processedGameParameters = GameParameters(1,)
        val exitGameParameters = GameParameters(2,)
        every { inputProcessor.process("testInput1", gameParameters) } returns ProcessedInput.validAndContinue("", processedGameParameters)
        every { inputProcessor.process("testInput2", processedGameParameters) } returns ProcessedInput.validAndExit("", exitGameParameters)

        val menuAction = MenuAction(userInputOutput, inputProcessor)

        val result = menuAction.execute(gameParameters)

        assertEquals(exitGameParameters, result)
    }

    @Test
    fun `should display last prompt on exit if any`() {
        val gameParameters = GameParameters(1,)
        every { userInputOutput.readLine() } returns "testInput1"
        val processedGameParameters = GameParameters(2,)
        every { inputProcessor.process("testInput1", gameParameters) } returns ProcessedInput.validAndExit("xyz", processedGameParameters)

        val menuAction = MenuAction(userInputOutput, inputProcessor)

        menuAction.execute(gameParameters)

        verify { userInputOutput.waitForAnyKey(MenuAction.getExitPrompt("xyz")) }
    }

    @Test
    fun `should not display anything on exit when there's no exit prompt`() {
        val gameParameters = GameParameters(1,)
        every { userInputOutput.readLine() } returns "testInput1"
        val processedGameParameters = GameParameters(2,)
        every { inputProcessor.process("testInput1", gameParameters) } returns ProcessedInput.validAndExit("", processedGameParameters)

        val menuAction = MenuAction(userInputOutput, inputProcessor)

        menuAction.execute(gameParameters)

        verify (inverse = true) { userInputOutput.displayLine(MenuAction.getExitPrompt("")) }
    }
}