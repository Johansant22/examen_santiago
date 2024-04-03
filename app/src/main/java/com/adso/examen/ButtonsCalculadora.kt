package com.adso.examen

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ButtonsCalculadora : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_buttons_calculadora)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        class ButtonCalculadora : AppCompatActivity() {
            private lateinit var resultadoTextView: TextView
            private var operacionPendiente: ((Int, Int) -> Int)? = null
            private var numeroAnterior = 0
            private var resultado = 0

            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_buttons_calculadora)

                resultadoTextView = findViewById(R.id.resultadoTextView)
                setupButtonListeners()
            }

            private fun setupButtonListeners() {
                val botonesNumericos = listOf(
                    findViewById<Button>(R.id.button1),
                    findViewById<Button>(R.id.button2),
                    findViewById<Button>(R.id.button3),
                    findViewById<Button>(R.id.button4),
                    findViewById<Button>(R.id.button5),
                    findViewById<Button>(R.id.button6),
                    findViewById<Button>(R.id.button7),
                    findViewById<Button>(R.id.button8),
                    findViewById<Button>(R.id.button9),
                    findViewById<Button>(R.id.button10)
                )

                val botonesOperadores = listOf(
                    findViewById<Button>(R.id.button11),
                    findViewById<Button>(R.id.button12),
                    findViewById<Button>(R.id.button13),
                    findViewById<Button>(R.id.button14)
                )

                val botonIgual = findViewById<Button>(R.id.buttonIgual)
                val botonBorrar = findViewById<Button>(R.id.buttonBorrar)

                // Función onClick para todos los botones numéricos
                val numerosClickListener: (View) -> Unit = { view ->
                    val numero = (view as Button).text.toString().toInt()
                    agregarNumero(numero)
                }

                botonesNumericos.forEach { boton ->
                    boton.setOnClickListener(numerosClickListener)
                }

                // Función onClick para todos los botones de operadores
                val operadoresClickListener: (View) -> Unit = { view ->
                    val operador = (view as Button).text.toString()
                    seleccionarOperacion(operador)
                }

                botonesOperadores.forEach { boton ->
                    boton.setOnClickListener(operadoresClickListener)
                }

                botonIgual.setOnClickListener { calcular() }
                botonBorrar.setOnClickListener { borrar() }
            }

            private fun agregarNumero(numero: Int) {
                resultado = resultado * 10 + numero
                actualizarResultado()
            }

            private fun seleccionarOperacion(operador: String) {
                when (operador) {
                    "+" -> operacionPendiente = { a, b -> a + b }
                    "-" -> operacionPendiente = { a, b -> a - b }
                    "*" -> operacionPendiente = { a, b -> a * b }
                    "/" -> operacionPendiente = { a, b -> a / b }
                }
                numeroAnterior = resultado
                resultado = 0
            }

            private fun calcular() {
                if (operacionPendiente != null) {
                    resultado = operacionPendiente!!(numeroAnterior, resultado)
                    operacionPendiente = null
                    actualizarResultado()
                }
            }

            private fun borrar() {
                resultado = 0
                numeroAnterior = 0
                operacionPendiente = null
                actualizarResultado()
            }

            private fun actualizarResultado() {
                resultadoTextView.text = resultado.toString()
            }
        }


}
}