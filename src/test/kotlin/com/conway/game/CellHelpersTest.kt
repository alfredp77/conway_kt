package com.conway.game

import kotlin.test.Test
import kotlin.test.assertEquals

class CellHelpersTest {
    @Test
    fun `findNeighbours should return empty list when no neighbours`() {
        val cell = Cell(0, 0)
        val allCells = hashSetOf(cell)
        val neighbours = Cell.findNeighbours(cell, allCells)
        assert(neighbours.neighbours.isEmpty())
    }

    @Test
    fun `should return all neighbouring cells`() {
        val cell = Cell(1,1)
        val allCells = hashSetOf(cell,
            Cell(0,0),
            Cell(0,1),
            Cell(0,2),
            Cell(1,0),
            Cell(1,2),
            Cell(2,0),
            Cell(2,1),
            Cell(2,2),
            Cell(3,3))

        val (neighbours, _) = Cell.findNeighbours(cell, allCells)

        assertEquals(8, neighbours.size)
        assertEquals(hashSetOf(
            Cell(0,0),
            Cell(0,1),
            Cell(0,2),
            Cell(1,0),
            Cell(1,2),
            Cell(2,0),
            Cell(2,1),
            Cell(2,2)), neighbours.toHashSet())
    }

    @Test
    fun `should also return missing neighbours`() {
        val cell = Cell(1,1)
        val allCells = hashSetOf(cell,
            Cell(0,1),
            Cell(1,0),
            Cell(1,2))

        val (_, missing) = Cell.findNeighbours(cell, allCells)

        assertEquals(5, missing.size)
        assertEquals(hashSetOf(
            Cell(0,0),
            Cell(0,2),
            Cell(2,0),
            Cell(2,1),
            Cell(2,2)), missing.toHashSet())
    }
}