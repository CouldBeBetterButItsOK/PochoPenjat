package com.dam.xevi.pochopenjat

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doOnTextChanged
import com.dam.xevi.pochopenjat.models.Jugador
import com.dam.xevi.pochopenjat.models.Partida
import com.dam.xevi.pochopenjat.singleton.AppSingleton

class MainActivity : AppCompatActivity() {
    private lateinit var partida: Partida
    private var jugador1: Jugador? = null
    private var jugador2: Jugador? = null
    private var pista: Boolean = false
    private lateinit var nomet: EditText
    private lateinit var jugador: TextView
    private lateinit var paraulaet: EditText
    private lateinit var nocb: CheckBox
    private lateinit var sicb: CheckBox
    private lateinit var acceptarbt: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        nomet = findViewById(R.id.nomET)
        paraulaet = findViewById(R.id.paraulaET)
        nocb = findViewById(R.id.sensePistaCheckBox)
        sicb = findViewById(R.id.ambPistaCheckBox)
        acceptarbt = findViewById(R.id.acceptarBT)
        jugador = findViewById(R.id.textView3)
        nomet.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                paraulaSecreta()
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })
        paraulaet.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                paraulaSecreta()
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })
    }
    fun acceptar(view: View){
        if(jugador1 == null) {
            jugador1 = Jugador(nomet.text.toString(),paraulaet.text.toString().uppercase().toList())
            jugador1!!.pista = pista
            jugador1!!.turn = true
            nomet.text.clear()
            paraulaet.text.clear()
            jugador.setText("Jugador 2:")
        }
        else{
            jugador2 = Jugador(nomet.text.toString(),paraulaet.text.toString().uppercase().toList())
            jugador2?.pista = pista
            if(jugador1!!.pista){
                jugador1!!.lletresEscrites.add(jugador2!!.paraula[0])
            }
            if(jugador2!!.pista){
                jugador2!!.lletresEscrites.add(jugador1!!.paraula[0])
            }
            partida = Partida(jugador1,jugador2)
            val singleton = AppSingleton.getInstance()
            singleton.setPartida(partida)
            val intent = Intent(this, Jugar_Partida::class.java)
            startActivity(intent)
        }
    }
    fun ambpista(view: View){
        nocb.isChecked = false
        pista = true
    }
    fun sensepista(view: View){
        sicb.isChecked = false
        pista = false
    }
    fun paraulaSecreta(){
        val regex = Regex("^[a-zA-ZÇç]{6,12}$")
        if(regex.matches(paraulaet.text) && !nomet.text.isEmpty()){
            acceptarbt.visibility = View.VISIBLE
        }
        else{
            acceptarbt.visibility = View.INVISIBLE
        }
    }
}