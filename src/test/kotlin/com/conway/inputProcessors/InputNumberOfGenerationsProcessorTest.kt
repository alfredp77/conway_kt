package com.conway.inputProcessors

import com.conway.game.GameParameters
import com.conway.tools.InvalidInputMessage
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class InputNumberOfGenerationsProcessorTest {
    private val processor = InputNumberOfGenerationsProcessor()

    @Test
    fun `should return the same game parameters when input cannot be parsed`() {
        val gameParameters = GameParameters()

        val processedInput = processor.process("invalid input", gameParameters)

        assertFalse(processedInput.isValid)
        assert(processedInput.shouldContinue)
        assertEquals(gameParameters, processedInput.gameParameters)
    }

    @Test
    fun `should parse input to number of generations`() {
        val gameParameters = GameParameters()

        val processedInput = processor.process("5", gameParameters)

        assertValid(5, processedInput)
    }

    private fun assertValid(expected: Int, processedInput: ProcessedInput) {
        assert(processedInput.isValid)
        assertFalse(processedInput.shouldContinue)
        assertEquals(expected, processedInput.gameParameters.generations)
    }

    fun assertInvalid(input: String, gameParameters: GameParameters) {
        val processedInput = processor.process(input, gameParameters)
        assertFalse(processedInput.isValid)
        assert(processedInput.shouldContinue)
        assertEquals(InvalidInputMessage, processedInput.prompt)
        assertEquals(gameParameters, processedInput.gameParameters)
    }

    @Test
    fun `should validate number of generations within limits`() {
        val gameParameters = GameParameters(minGenerations = 10, maxGenerations = 20,)
        assertInvalid("0", gameParameters)
        assertInvalid("9", gameParameters)
        assertInvalid("21", gameParameters)
    }

    @Test
    fun `should only check generations is greater than zero when minGenerations is not specified`() {
        val gameParameters = GameParameters()
        assertInvalid("-1", gameParameters)
        assertInvalid("0", gameParameters)
    }

    @Test
    fun `should retain other properties of supplied gameParameters`() {
        val gameParameters = GameParameters(maxWidth = 8,)
        val processedInput = processor.process("7", gameParameters)

        assertValid(7, processedInput)
        assertEquals(8, processedInput.gameParameters.maxWidth)
    }
}