package com.example.daria.myapplication.presenter

import com.example.daria.myapplication.model.Size
import com.example.daria.myapplication.model.Table

class Presenter(val view: IView) {
    private var table: Table = Table()

    fun resetGame() {
        table.startNewGame()
        view.onNewState(table.getOrganisms())
    }

    fun performSimulation () {
        table.performSimulation()
        view.onNewState(table.getOrganisms())
    }

    fun changeTableSize(size: Size?) {
        if (size != null) {
            table.clearTable()
            table.size = size
            view.onNewSize(size)
        }
    }
}