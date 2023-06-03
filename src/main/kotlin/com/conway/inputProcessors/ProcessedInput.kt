package com.conway.inputProcessors

import com.conway.game.GameParameters

data class ProcessedInput(val prompt: String,
        val gameParameters: GameParameters,
        val isValid: Boolean = true,
        val shouldContinue: Boolean = false) {
    companion object {
        fun invalid(prompt: String, gameParameters: GameParameters): ProcessedInput {
            return ProcessedInput(prompt, gameParameters, false)
        }

        fun validAndContinue(prompt: String, gameParameters: GameParameters): ProcessedInput {
            return ProcessedInput(prompt, gameParameters, true, true)
        }

        fun validAndExit(prompt: String, gameParameters: GameParameters): ProcessedInput {
            return ProcessedInput(prompt, gameParameters, true, false)
        }
    }
}
