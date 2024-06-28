package com.example.tipscalculator

import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.example.tipscalculator.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var percentage: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRadioButtons()
        setupButtons()
    }

    private fun setupRadioButtons() {
        // Configurar Porcentagem selecionada
        binding.rbOptionOne.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                percentage = 10
                updateRadioButtons(binding.rbOptionOne)
            }
        }

        binding.rbOptionTwo.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                percentage = 15
                updateRadioButtons(binding.rbOptionTwo)
            }
        }

        binding.rbOptionThree.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                percentage = 20
                updateRadioButtons(binding.rbOptionThree)
            }
        }
    }

    private fun updateRadioButtons(selectedRadioButton: RadioButton) {
        clearRadioButtonBackgrounds()

        // Aplicar background selecionado
        selectedRadioButton.setBackgroundResource(R.drawable.radio_button_selected)
    }

    private fun clearRadioButtonBackgrounds() {
        binding.rbOptionOne.setBackgroundResource(R.drawable.radio_button_background)
        binding.rbOptionTwo.setBackgroundResource(R.drawable.radio_button_background)
        binding.rbOptionThree.setBackgroundResource(R.drawable.radio_button_background)
    }

    private fun setupButtons() {
        // Botão Limpar
        binding.btnClean.setOnClickListener {
            clean()
        }

        // Configurar botão Calculate
        binding.btnDone.setOnClickListener {
            val totalTableText = binding.tieTotal.text?.toString()
            val numOfPeopleText = binding.tiNumPeople.text?.toString()

            // Validação de campos vazios
            if (totalTableText.isNullOrEmpty() || numOfPeopleText.isNullOrEmpty()) {
                Snackbar.make(binding.root, "Please, fill all the blanks.", Snackbar.LENGTH_LONG).show()
            } else {
                var totalTable = totalTableText.toFloat() // entrada valor float
                var nPeople = numOfPeopleText.toInt() // entrada inteira

                val totalPerPerson = totalTable / nPeople
                val tips = totalPerPerson * percentage / 100
                val finalAmount = totalPerPerson + tips

                // Exibir o resultado na tela
                val finalResult = String.format("R$ %.2f", finalAmount)
                binding.valorSaldo.text = finalResult
            }
        }
    }

    private fun clean() {
        binding.tieTotal.text?.clear()
        binding.tiNumPeople.text?.clear()
        binding.rbOptionOne.isChecked = false
        binding.rbOptionTwo.isChecked = false
        binding.rbOptionThree.isChecked = false
        binding.valorSaldo.text = "R$ 00,00"

        val hideKeyboard = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        hideKeyboard.hideSoftInputFromWindow(binding.root.windowToken, 0)

        clearRadioButtonBackgrounds()
    }
}
