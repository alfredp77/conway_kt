package com.conway.inputProcessors

import com.conway.game.GameParameters
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class InputGridSizeProcessorTest {
    private val inputGridSizeProcessor = InputGridSizeProcessor()

    @Test
    fun `should return the same game parameters when input cannot be parsed` () {
        val gameParameters = GameParameters()

        val processedInput = inputGridSizeProcessor.process("invalid input", gameParameters)

        assertFalse(processedInput.isValid)
        assert(processedInput.shouldContinue)
        assertEquals(gameParameters, processedInput.gameParameters)
    }


    @Test
    fun `should parse input to grid size` () {
        val gameParameters = GameParameters(maxWidth = 10, maxHeight = 15)

        val processedInput = inputGridSizeProcessor.process("5 7", gameParameters)

        assertValid(5, 7, processedInput)
    }

    private fun assertValid(expectedWidth: Int, expectedHeight: Int, processedInput: ProcessedInput) {
        assert(processedInput.isValid)
        assertFalse(processedInput.shouldContinue)
        assertEquals(expectedWidth, processedInput.gameParameters.width)
        assertEquals(expectedHeight, processedInput.gameParameters.height)
    }

    @Test
    fun `should validate grid size within limits` () {

        fun assertInvalid(input:String, gameParameters: GameParameters) {
            val processedInput = inputGridSizeProcessor.process("0 0", gameParameters)
            assertFalse(processedInput.isValid)
            assert(processedInput.shouldContinue)
            assertEquals(gameParameters, processedInput.gameParameters)
        }

        val gameParameters = GameParameters(maxWidth = 10, maxHeight = 15)
        assertInvalid("0 0", gameParameters)
        assertInvalid("11 8", gameParameters)
        assertInvalid("9 16", gameParameters)
    }

    @Test
    fun `should allow defining grid with max height and width`() {
        val gameParameters = GameParameters(maxWidth = 10, maxHeight = 15)
        val processedInput = inputGridSizeProcessor.process("10 15", gameParameters)

        assertValid(10, 15, processedInput)
    }

    @Test
    fun `should accept any positive numbers for height when max height is not defined`() {
        val gameParameters = GameParameters(maxWidth = 10)
        val processedInput = inputGridSizeProcessor.process("7 16", gameParameters)

        assertValid(7, 16, processedInput)
    }

    @Test
    fun `should accept any positive numbers for width when max width is not defined`() {
        val gameParameters = GameParameters(maxHeight = 10)
        val processedInput = inputGridSizeProcessor.process("14 8", gameParameters)

        assertValid(14, 8, processedInput)
    }
    @Test
    fun `should retain other properties of supplied gameParameters`() {
        val gameParameters = GameParameters(generations = 3, maxWidth = 8, maxHeight = 10)
        val processedInput = inputGridSizeProcessor.process("7 10", gameParameters)

        assertValid(7, 10, processedInput)
        assertEquals(3, processedInput.gameParameters.generations)
    }

}