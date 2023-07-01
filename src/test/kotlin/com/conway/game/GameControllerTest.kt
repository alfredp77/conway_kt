package com.conway.game

import com.conway.actions.Action
import com.conway.tools.*
import org.mockito.kotlin.*
import kotlin.test.Test

class GameControllerTest {
    private val gameParameters = GameParameters()
    private val userInputOutput = mock<UserInputOutput>()

    @Test
    fun `should exit when exit condition is met`() {
        val action1 = createMockAction("1","foo")
        val action1Result = GameParameters()
        action1.stub {
            on { this.execute(gameParameters) } doReturn action1Result
        }

        val action2 = createMockAction("2","bar")
        userInputOutput.stub {
            on { readLine() } doReturn "1" doReturn "2"
        }

        val gameController = GameController(userInputOutput, listOf(action1, action2)) { it == action1Result }
        gameController.run(gameParameters)

        verify(action1).execute(gameParameters)
        verify(action2, never()).execute(any())
    }

    @Test
    fun `should take a list of actions and display them`() {
        val action1 = createMockAction("1","foo")
        val action2 = createMockAction("2","bar")
        userInputOutput.stub {
            on { this.readLine() } doReturn "1"
        }
        val gameController = GameController(userInputOutput, listOf(action1, action2))
        gameController.run(gameParameters)

        verify(userInputOutput).displayLine(WelcomeMessage)
        verify(userInputOutput).displayLine("[1] foo")
        verify(userInputOutput).displayLine("[2] bar")
        verify(userInputOutput).displayLine(PleaseEnterSelection)
    }

    private fun createMockAction(id: String, description: String): Action {
        val action: Action = mock()
        action.stub {
            on { this.id } doReturn id
            on { this.description } doReturn description
            on { this.execute(any()) } doReturn GameParameters(exit = true,)
        }
        return action
    }

    @Test
    fun `should execute selected action`() {
        val action1 = createMockAction("1","foo")
        val action2 = createMockAction("2","bar")
        val selectedAction = createMockAction("3","baz")

        userInputOutput.stub {
            on { readLine() } doReturn "3"
        }
        val actions = listOf(action1, action2, selectedAction)
        val gameController = GameController(userInputOutput, actions)
        gameController.run(gameParameters)

        verify(selectedAction).execute(gameParameters)
    }

    @Test
    fun `should show the menu again when input is invalid`() {
        val action1 = createMockAction("1","foo")
        val action2 = createMockAction("2","bar")

        userInputOutput.stub {
            on { readLine() } doReturn "3" doReturn "2"
        }
        val actions = listOf(action1, action2)
        val gameController = GameController(userInputOutput, actions)
        gameController.run(gameParameters)

        verify(userInputOutput, times(1)).displayLine(WelcomeMessage)
        verify(userInputOutput).displayLine(InvalidInputMessage)
        verify(userInputOutput, times(2)).displayLine("[1] foo")
        verify(userInputOutput, times(2)).displayLine("[2] bar")
        verify(userInputOutput, times(2)).displayLine(PleaseEnterSelection)
        verify(action2).execute(gameParameters)
    }

    @Test
    fun `on exit should show thank you message`() {
        val action1 = createMockAction("1","foo")
        userInputOutput.stub {
            on { readLine() } doReturn "1"
        }

        val gameController = GameController(userInputOutput, listOf(action1))
        gameController.run(gameParameters)

        verify(userInputOutput).displayLine(ThankYouMessage)
    }
}