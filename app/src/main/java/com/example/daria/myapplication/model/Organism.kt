package com.example.daria.myapplication.model

import java.util.ArrayList

abstract class Organism(var position: Position) {
    private var movesNumber = 0
    var isAlive = true
    private set

    abstract fun makeTurn(table: ITable)

    fun kill() { isAlive = false }

    protected fun getMoves(): Int = movesNumber

    protected fun incrementMoves() = movesNumber++

    protected fun resetMoves() { movesNumber = 0 }

    protected fun getPossibleMoves(sizeTable: Size): List<Position> {
        val possibleMoves = ArrayList<Position>()
        val topLeft = Position(position.row - 1, position.column - 1)
        val bottomRight = Position(position.row + 1, position.column + 1)

        for (column in topLeft.column..bottomRight.column) {
            for (row in topLeft.row..bottomRight.row) {
                val newPos = Position(row, column)
                if (position.isPossibleMove(newPos, sizeTable)) {
                    possibleMoves.add(newPos)
                }
            }
        }
        return possibleMoves
    }
}