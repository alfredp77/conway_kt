package com.conway.actions

import com.conway.game.GameParameters
import com.conway.inputProcessors.InputProcessor
import com.conway.inputProcessors.ProcessedInput
import com.conway.tools.UserInputOutput
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

class MenuActionTest {
    @Test
    fun `should first display prompt from initialize result of input processor on execute`() {
        // Given
        val inputProcessor = mock(InputProcessor::class.java)
        val userInputOutput = mock(UserInputOutput::class.java)
        val gameParameters = GameParameters()
        `when`(inputProcessor.initialize(gameParameters)).thenReturn(ProcessedInput("testPrompt", gameParameters))
        val menuAction = MenuAction(userInputOutput, inputProcessor)

        // When
        menuAction.execute(gameParameters)

        // Then
        verify(userInputOutput).displayLine("testPrompt")
    }

}