package com.conway.inputProcessors

import com.conway.game.GameParameters
import com.conway.game.GameRunner
import com.conway.game.GameState
import com.conway.tools.Commands
import com.conway.tools.LiveCellsPrinter

class RunProcessor(private val gameRunner: GameRunner, private val printer: LiveCellsPrinter) : InputProcessor {
    var currentState: GameState = GameState(emptyList())
        get() {
            return field
        }

    val nextGenerationPrompt = "Enter ${Commands.NEXT.value} to generate next generation"
    val endGenerationPrompt = "Reached end of generations"

    override val id = "4"
    override val description = "Run"
    override val prompt = nextGenerationPrompt

    override fun initialize(gameParameters: GameParameters): ProcessedInput {
        val initialState = gameRunner.generateInitialState(gameParameters)
        currentState = initialState
        printer.print("Initial position", initialState)
        return ProcessedInput(prompt, gameParameters, shouldContinue = true)
    }

    override fun process(input: String, gameParameters: GameParameters): ProcessedInput {
        if (input != Commands.NEXT.value) {
            return ProcessedInput.invalid("", gameParameters)
        }
        val nextState = gameRunner.generateNextState(currentState)
        currentState = nextState
        printer.print("Generation ${currentState.generation}", nextState)
        val shouldContinue = nextState.generation < gameParameters.generations
        val prompt = if (shouldContinue) nextGenerationPrompt else endGenerationPrompt
        return ProcessedInput(prompt, gameParameters, shouldContinue = shouldContinue)
    }
}
