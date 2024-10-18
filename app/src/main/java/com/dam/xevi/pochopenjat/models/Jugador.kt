package com.dam.xevi.pochopenjat.models

class Jugador (
    val nom: String,
    var paraula: List<Char>,
    var lletresEscrites: MutableList<Char> = mutableListOf(),
    var lletresCorrecte: MutableList<Char> = mutableListOf(),
    var fallos: Int = 0,
    var pista: Boolean = false,
    var turn: Boolean = false
)

