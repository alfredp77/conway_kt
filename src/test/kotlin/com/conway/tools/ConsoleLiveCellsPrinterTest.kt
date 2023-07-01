package com.conway.tools

import com.conway.game.Cell
import com.conway.game.GameParameters
import com.conway.game.GameState
import kotlin.test.Test
import kotlin.test.assertEquals


class ConsoleLiveCellsPrinterTest {
    private val consoleTestHelper = ConsoleTestHelper()
    private val printer = ConsoleLiveCellsPrinter()
    @Test
    fun `should print all dead cells`() {
        val gameState = GameState(emptyList(), 0, GameParameters(4, 3))

        printer.print("this is the prompt", gameState)

        val output = consoleTestHelper.getOutput()
        assertEquals("""
            this is the prompt
            . . . .
            . . . .
            . . . .
        """.trimIndent(), output.trimEnd())
    }

    @Test
    fun `should print live and dead cells`() {
        val liveCells = listOf(
            Cell(2, 2),
            Cell(3, 3),
            Cell(3,2),
            Cell(4, 4)
        )
        val gameState = GameState(liveCells, 0, GameParameters(4, 5))

        printer.print("live and dead cells", gameState)

        val output = consoleTestHelper.getOutput()
        assertEquals("""
            live and dead cells
            . . . .
            . . . o
            . . o .
            . o o .
            . . . .
        """.trimIndent(), output.trimEnd())
    }
}

