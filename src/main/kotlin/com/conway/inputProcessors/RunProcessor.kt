package com.conway.inputProcessors

import com.conway.game.GameParameters
import com.conway.game.GameRunner
import com.conway.game.GameState
import com.conway.tools.NextGenerationPrompt

class RunProcessor(private val gameRunner: GameRunner) : InputProcessor {
    var currentState: GameState = GameState()
        get() {
            return field
        }
    override val id = "4"
    override val description = "Run"

    override fun initialize(gameParameters: GameParameters): ProcessedInput {
        val initialState = gameRunner.generateInitialState(gameParameters)
        currentState = initialState
        return ProcessedInput(NextGenerationPrompt, gameParameters, shouldContinue = true)
    }

    override fun process(input: String, gameParameters: GameParameters): ProcessedInput {
        val nextState = gameRunner.generateNextState(currentState)
        currentState = nextState
        return ProcessedInput(NextGenerationPrompt, gameParameters, shouldContinue = true)
    }
}
