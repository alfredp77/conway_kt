package com.conway.game


data class Cell(val x:Int, val y:Int) {
    companion object CellHelpers {

        fun findLiveNeighbours(cell: Cell, allCells: HashSet<Cell>): Sequence<Cell> {
            return sequence{
                for (neighbour in findAllNeighbours(cell)) {
                    if (neighbour in allCells) {
                        yield(neighbour)
                    }
                }
            }
        }

        fun findAllNeighbours(cell: Cell) = sequenceOf(
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