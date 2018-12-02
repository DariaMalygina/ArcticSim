package com.example.daria.myapplication.presenter

import com.example.daria.myapplication.model.Organism
import com.example.daria.myapplication.model.Size

interface IView {
    fun onNewState(organisms: List<Organism>)
    fun onNewSize(size: Size)
}