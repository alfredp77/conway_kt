package com.conway.game

data class Cell(val x:Int, val y:Int) {
    companion object CellHelpers {

        fun findNeighbours(cell: Cell, allCells: HashSet<Cell>): NeighbouringCells {
            val allNeighbours = findAllNeighbours(cell).toHashSet()

            val neighbours = mutableListOf<Cell>()
            val missing = mutableListOf<Cell>()
            for (neighbour in allNeighbours) {
                if (neighbour in allCells) {
                    neighbours.add(neighbour)
                } else {
                    missing.add(neighbour)
                }
            }

            return NeighbouringCells(neighbours, missing)
        }

        fun findAllNeighbours(cell: Cell) = listOf(
            Cell(cell.x - 1, cell.y - 1),
            Cell(cell.x - 1, cell.y),
            Cell(cell.x - 1, cell.y + 1),
            Cell(cell.x, cell.y - 1),
            Cell(cell.x, cell.y + 1),
            Cell(cell.x + 1, cell.y - 1),
            Cell(cell.x + 1, cell.y),
            Cell(cell.x + 1, cell.y + 1)
        )
    }
}


data class NeighbouringCells(val neighbours: List<Cell>, val missing: List<Cell>)