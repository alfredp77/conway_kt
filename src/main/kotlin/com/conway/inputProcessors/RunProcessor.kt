package com.conway.inputProcessors

import com.conway.game.GameParameters
import com.conway.game.GameRunner
import com.conway.game.GameState
import com.conway.tools.Commands
import com.conway.tools.InvalidInputMessage
import com.conway.tools.LiveCellsPrinter
import com.conway.tools.NextGenerationPrompt

class RunProcessor(private val gameRunner: GameRunner, private val printer: LiveCellsPrinter) : InputProcessor {
    var currentState: GameState = GameState(emptyList())
        get() {
            return field
        }
    override val id = "4"
    override val description = "Run"

    override fun initialize(gameParameters: GameParameters): ProcessedInput {
        val initialState = gameRunner.generateInitialState(gameParameters)
        currentState = initialState
        printer.print("Initial position", initialState)
        return ProcessedInput(NextGenerationPrompt, gameParameters, shouldContinue = true)
    }

    override fun process(input: String, gameParameters: GameParameters): ProcessedInput {
        if (input != Commands.NEXT.value) {
            return ProcessedInput.invalid("", gameParameters)
        }
        val nextState = gameRunner.generateNextState(currentState)
        currentState = nextState
        printer.print("Generation ${currentState.generation}", nextState)
        return ProcessedInput(NextGenerationPrompt, gameParameters, shouldContinue = nextState.generation < gameParameters.generations)
    }
}
