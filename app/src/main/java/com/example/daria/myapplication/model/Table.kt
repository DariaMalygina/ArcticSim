package com.example.daria.myapplication.model

import java.util.*
import kotlin.collections.ArrayList

class Table(var size: Size = Size(15, 10)): ITable {
    private val random: Random = Random()
    private var organisms: ArrayList<Organism> = ArrayList()

    fun clearTable() {
        organisms.clear()
    }

    fun startNewGame() {
        clearTable()
        for (row in 0 until size.rowCount) {
            for (column in 0 until size.columnCount) {
                val r = random.nextInt(100)
                when (r) {
                    in 0 until 5 -> organisms.add(Orca(Position(row, column)))
                    in 50 until 100 -> organisms.add(Penguin(Position(row, column)))
                }
            }
        }
    }

    fun performSimulation () {
        val organisms = ArrayList(organisms)
        for (o in organisms) {
            if (o.isAlive) {
                o.makeTurn(this)
            }
        }
    }

    fun getOrganisms(): ArrayList<Organism> = organisms

    override fun getSizeTable() = size

    override fun getOrganismAt(position: Position): Organism? {
        return organisms.find { it.position == position }
    }

    override fun moveTo(organism: Organism, position: Position) {
        organism.position = position
    }

    override fun reproduceTo(organism: Organism) {
        organisms.add(organism)
    }

    override fun die(organism: Organism) {
        organism.kill()
        organisms.remove(organism)
    }
}