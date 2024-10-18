package com.dam.xevi.pochopenjat

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.transition.Visibility
import com.dam.xevi.pochopenjat.models.Jugador
import com.dam.xevi.pochopenjat.models.Partida
import com.dam.xevi.pochopenjat.singleton.AppSingleton
import java.text.Normalizer
import java.util.regex.Pattern

class Jugar_Partida : AppCompatActivity() {
    private lateinit var partida: Partida
    private lateinit var jugador: Jugador
    private lateinit var jugador2: Jugador
    private lateinit var paraula: List<Char>
    private lateinit var jugadorTV: TextView
    private lateinit var fallosTV: TextView
    private lateinit var paraulaLO: LinearLayout
    private lateinit var penjatIV: ImageView
    private lateinit var nextTurnBT: ImageButton
    private lateinit var tornarAJugarBT: Button
    private lateinit var guanyadorTV: TextView
    private lateinit var guanyadorLO: LinearLayout
    private lateinit var lletresTL: TableLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_jugar_partida)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        guanyadorLO = findViewById(R.id.guanyadorLO)
        tornarAJugarBT = findViewById(R.id.tornarAJugatBT)
        nextTurnBT = findViewById(R.id.nextTurnBT)
        penjatIV = findViewById(R.id.penjatIV)
        paraulaLO = findViewById(R.id.paraulaLO)
        guanyadorTV = findViewById(R.id.guanyadorTV)
        fallosTV = findViewById(R.id.fallosTV)
        jugadorTV = findViewById(R.id.jugadorTV)
        partida = AppSingleton.getInstance().getPartida()
        lletresTL = findViewById(R.id.tableLayout)
        if (partida.jugador1!!.turn){
            jugador = partida.jugador1!!
            jugador2 = partida.jugador2!!
            paraula = jugador2.paraula
        }
        else {
            jugador = partida.jugador2!!
            jugador2 = partida.jugador1!!
            paraula = jugador2.paraula
        }
        jugadorTV.text = jugador.nom
        fallosTV.text = jugador.fallos.toString()
        penjatIV.setImageResource(
            when (jugador.fallos) {
                0 -> R.drawable.penjat0
                1 -> R.drawable.penjat1
                2 -> R.drawable.penjat2
                3 -> R.drawable.penjat3
                4 -> R.drawable.penjat4
                5 -> R.drawable.penjat5
                8 -> R.drawable.penjat6
                else -> R.drawable.penjat0
            }
        )
        for(i in paraula){
            val textView = TextView(this).apply {
                id = View.generateViewId()
                layoutParams = LinearLayout.LayoutParams(
                    0,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply { weight = 1f }
                gravity = android.view.Gravity.CENTER
                text = i.toString()
                textSize = 40f
                tag = i.toString()
                if(senseAccents(i) in jugador.lletresEscrites){
                    visibility = View.VISIBLE
                }
                else visibility = View.INVISIBLE
            }
            paraulaLO.addView(textView)
        }
        for(i in jugador.lletresEscrites) {
            pintarLletra(i.toString())
        }
    }
    fun ProbarLletra(view: View){
        val lletre = view.tag
        jugador.lletresEscrites.add(lletre as Char)
        pintarLletra(lletre.toString())
        if(lletre in paraula){
            val tv = paraulaLO.findViewWithTag<TextView>(lletre)
            tv.visibility = View.VISIBLE
        }
    }
    fun senseAccents(input: Char): Char {
        val normalized = Normalizer.normalize(input.toString(), Normalizer.Form.NFD)
        val pattern: Pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
        val result = pattern.matcher(normalized).replaceAll("")
        return when (input) {
            'Ç' -> input
            else -> result.firstOrNull() ?: input
        }
    }
    private fun pintarLletra(lletra: String){
        val tv = lletresTL.findViewWithTag<TextView>(lletra)
        if(tv != null){
            tv.setBackgroundColor(Color.parseColor("#000000"))
            tv.setTextColor(Color.parseColor("#FFFFFF"))
            tv.isClickable = false
        }
        else {
            Log.w("MainActivity", "TextView with tag $lletra not found.")
        }
    }

}