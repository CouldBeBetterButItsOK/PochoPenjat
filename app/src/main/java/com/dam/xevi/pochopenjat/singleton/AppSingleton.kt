package com.dam.xevi.pochopenjat.singleton

import com.dam.xevi.pochopenjat.models.Partida

class AppSingleton private constructor() {
    private lateinit var partida:Partida
    companion object{
        @Volatile
        private  var instance: AppSingleton? = null
        fun getInstance(): AppSingleton{
            if(instance == null){
                synchronized(this){
                    if (instance == null){
                        instance = AppSingleton()
                    }
                }
            }
            return instance!!
        }
    }
    fun setPartida(partida :Partida){
        this.partida = partida
    }
    fun getPartida(): Partida{
        return this.partida
    }
}