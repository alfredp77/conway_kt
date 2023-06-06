package com.conway.game

import com.conway.actions.Action
import com.conway.tools.*

class GameController(private val userInputOutput: UserInputOutput, private val actions: List<Action>,
    private val endCondition: (x:GameParameters)->Boolean = { it.exit }) {
    fun run(gameParameters: GameParameters) {
        userInputOutput.displayLine(WelcomeMessage)
        var currentGameParameters = gameParameters
        do {
            actions.forEach { action ->
                userInputOutput.displayLine("[${action.id}] ${action.description}")
            }
            userInputOutput.displayLine(PleaseEnterSelection)
            val input = userInputOutput.readLine()
            val selectedAction = actions.find { it.id == input }
            if (selectedAction == null) {
                userInputOutput.displayLine(InvalidInputMessage)
            }
            else {
                currentGameParameters = selectedAction.execute(currentGameParameters)
            }

        } while (!endCondition(currentGameParameters))

        userInputOutput.displayLine(ThankYouMessage)
    }
}
