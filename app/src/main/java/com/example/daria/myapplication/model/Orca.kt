package com.example.daria.myapplication.model

import java.util.*

class Orca(position: Position): Organism(position) {
    private var hungryMoves = 0

    override fun makeTurn(table: ITable) {
        incrementMoves()
        val moves = getPossibleMoves(table.getSizeTable())
        when (getMoves()) {
            8 -> {
                reproduce(table, moves)
                resetMoves()
            }
            else -> swim(table, moves)
        }

        if (isStarvingDeath()) {
            table.die(this)
        }
    }

    private fun isStarvingDeath(): Boolean = hungryMoves == 3

    private fun eat() { hungryMoves = 0 }

    private fun starve() { hungryMoves++ }

    private fun reproduce(table: ITable, moves: List<Position>) {
        for (position in moves) {
            val organism = table.getOrganismAt(position)
            if (organism == null) {
                table.reproduceTo(Orca(position))
                break
            }
        }
    }

    private fun swim(table: ITable, moves: List<Position>) {
        if (!isAatPenguin(table, moves)) {
            starve()
            val random = Random().nextInt(moves.size)
            val position = moves[random]
            val organism = table.getOrganismAt(position)
            if (organism == null) {
                table.moveTo(this, position)
            }
        }
    }

    private fun isAatPenguin(table: ITable, moves: List<Position>): Boolean {
        for (position in moves) {
            val organism = table.getOrganismAt(position)
            if (organism is Penguin) {
                table.moveTo(this,organism.position)
                table.die(organism)
                eat()
                return true
            }
        }
        return false
    }
}