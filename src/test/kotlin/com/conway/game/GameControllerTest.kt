package com.conway.game

import com.conway.actions.Action
import com.conway.tools.*
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.BeforeTest
import kotlin.test.Test

class GameControllerTest {
    private val gameParameters = GameParameters()
    private val userInputOutput = mockk<UserInputOutput>()

    @BeforeTest
    fun setUp() {
        every { userInputOutput.displayLine(any()) } answers { callOriginal() }
        every { userInputOutput.readLine() } returns ""
    }

    @Test
    fun `should exit when exit condition is met`() {
        val action1 = createMockAction("1","foo")
        val action1Result = GameParameters()
        every { action1.execute(gameParameters) } returns action1Result
        val action2 = createMockAction("2","bar")

        every { userInputOutput.readLine() } returnsMany listOf("1", "2")

        val gameController = GameController(userInputOutput, listOf(action1, action2)) { it == action1Result }
        gameController.run(gameParameters)

        verify { action1.execute(gameParameters) }
        verify (inverse=true) { action2.execute(any()) }
    }

    @Test
    fun `should take a list of actions and display them`() {
        val action1 = createMockAction("1","foo")
        val action2 = createMockAction("2","bar")
        every { userInputOutput.readLine() } returns "1"

        val gameController = GameController(userInputOutput, listOf(action1, action2))
        gameController.run(gameParameters)

        verify { userInputOutput.displayLine(WelcomeMessage) }
        verify { userInputOutput.displayLine("[1] foo") }
        verify { userInputOutput.displayLine("[2] bar") }
        verify { userInputOutput.displayLine(PleaseEnterSelection) }
    }

    private fun createMockAction(id: String, description: String): Action {
        val action = mockk<Action>()
        every { action.id } returns id
        every { action.description } returns description
        every { action.execute(any()) } returns GameParameters(exit = true)
        return action
    }

    @Test
    fun `should execute selected action`() {
        val action1 = createMockAction("1","foo")
        val action2 = createMockAction("2","bar")
        val selectedAction = createMockAction("3","baz")
        every { userInputOutput.readLine() } returns "3"

        val actions = listOf(action1, action2, selectedAction)
        val gameController = GameController(userInputOutput, actions)
        gameController.run(gameParameters)

        verify { selectedAction.execute(gameParameters) }
    }

    @Test
    fun `should show the menu again when input is invalid`() {
        val action1 = createMockAction("1","foo")
        val action2 = createMockAction("2","bar")
        every { userInputOutput.readLine() } returnsMany listOf("3", "2")
        val actions = listOf(action1, action2)
        val gameController = GameController(userInputOutput, actions)
        gameController.run(gameParameters)

        verify (exactly = 1) { userInputOutput.displayLine(WelcomeMessage) }
        verify (exactly = 1) { userInputOutput.displayLine(InvalidInputMessage) }
        verify (exactly = 2) { userInputOutput.displayLine("[1] foo") }
        verify (exactly = 2) { userInputOutput.displayLine("[2] bar") }
        verify (exactly = 2) { userInputOutput.displayLine(PleaseEnterSelection) }
        verify (exactly = 1) { action2.execute(gameParameters) }
    }

    @Test
    fun `on exit should show thank you message`() {
        val action1 = createMockAction("1","foo")
        every { userInputOutput.readLine() } returns "1"

        val gameController = GameController(userInputOutput, listOf(action1))
        gameController.run(gameParameters)

        verify { userInputOutput.displayLine(ThankYouMessage) }
    }
}