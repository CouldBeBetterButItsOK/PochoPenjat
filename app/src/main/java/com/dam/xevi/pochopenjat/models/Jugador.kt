package com.dam.xevi.pochopenjat.models

class Jugador (
    val nom: String,
    var paraula: List<Char>,
    var lletresEscrites:List<Char>,
    var lletresCorrecte:List<Char>,
    var fallos:Int = 0,
    var pista:Boolean = false
)

