package com.conway.game

import kotlin.test.Test
import kotlin.test.assertEquals

class CellHelpersTest {
    @Test
    fun `findNeighbours should return empty list when no neighbours`() {
        val cell = Cell(0, 0)
        val allCells = hashSetOf(cell)
        val neighbours = Cell.findLiveNeighbours(cell, allCells).toList()
        assertEquals(emptyList(), neighbours)
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

        val neighbours = Cell.findLiveNeighbours(cell, allCells).toList()

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
    fun `test to fix bug`() {
        val allCells = hashSetOf(
            Cell(3,2),
            Cell(5,2),
            Cell(2,3),
            Cell(3,4),
            Cell(5,4),
            Cell(4,5))

        val neighbours = Cell.findLiveNeighbours(Cell(5, 4), allCells).toList()

        assertEquals(1, neighbours.size)
    }
}