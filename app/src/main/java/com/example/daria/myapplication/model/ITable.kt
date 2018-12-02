package com.example.daria.myapplication.model

data class Position(var row: Int = 0, var column: Int = 0) {

    fun isPossibleMove(newPos: Position, sizeTable: Size): Boolean =
        (!isSamePositionAsSelf(newPos) && isPositionInsideTable(newPos, sizeTable))


    private fun isSamePositionAsSelf(pos: Position): Boolean {
        return (pos.row == this.row) && (pos.column == this.column)
    }

    private fun isPositionInsideTable(pos: Position, sizeTable: Size): Boolean {
        return (pos.row in 0 until sizeTable.rowCount) && (pos.column in 0 until sizeTable.columnCount)
    }
}
data class Size(val rowCount: Int, val columnCount: Int)

interface ITable {
    fun getSizeTable(): Size
    fun getOrganismAt(position: Position): Organism?
    fun moveTo(organism: Organism, position: Position)
    fun reproduceTo(organism: Organism)
    fun die(organism: Organism)
}