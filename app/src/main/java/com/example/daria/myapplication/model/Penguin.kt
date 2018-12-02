package com.example.daria.myapplication.model

import java.util.*

class Penguin(position: Position): Organism(position) {
    override fun makeTurn(table: ITable) {
        incrementMoves()
        val moves = getPossibleMoves(table.getSizeTable())
        when (getMoves()) {
            3 -> {
                reproduce(table, moves)
                resetMoves()
            }
            else -> swim(table, moves)
        }
    }

    private fun reproduce(table: ITable, moves: List<Position>) {
        for (position in moves) {
            val organism = table.getOrganismAt(position)
            if (organism == null) {
                table.reproduceTo(Penguin(position))
                break
            }
        }
    }

    private fun swim(table: ITable, moves: List<Position>) {
        val random = Random().nextInt(moves.size)
        val position = moves[random]
        val organism = table.getOrganismAt(position)
        if (organism == null) {
            table.moveTo(this, position)
        }
    }
}