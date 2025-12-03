package com.example.kuzminkirov_chyort1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.kuzminkirov_chyort1.R
import net.objecthunter.exp4j.ExpressionBuilder
import java.util.Locale

enum class NumberSystem {
    HEXADECIMAL, DECIMAL, OCTAL, BINARY
}

class ProgrammerModeActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var hexValue: TextView
    private lateinit var decValue: TextView
    private lateinit var octValue: TextView
    private lateinit var binValue: TextView

    private var currentNumberSystem = NumberSystem.DECIMAL
    private var currentExpression = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_programmer_mode)

        hexValue = findViewById(R.id.hexadecimal_value)
        decValue = findViewById(R.id.decimal_value)
        octValue = findViewById(R.id.octal_value)
        binValue = findViewById(R.id.binary_value)

        setupClickListeners()
        updateNumberSystemUI()
        updateButtonStates()
        updateNumberDisplays()
    }

    private fun setupClickListeners() {
        // Number system selectors
        findViewById<View>(R.id.hexadecimal_item).setOnClickListener(this)
        findViewById<View>(R.id.decimal_item).setOnClickListener(this)
        findViewById<View>(R.id.octal_item).setOnClickListener(this)
        findViewById<View>(R.id.binary_item).setOnClickListener(this)

        // Buttons
        findViewById<Button>(R.id.buttonA).setOnClickListener(this)
        findViewById<Button>(R.id.buttonB).setOnClickListener(this)
        findViewById<Button>(R.id.buttonC).setOnClickListener(this)
        findViewById<Button>(R.id.buttonD).setOnClickListener(this)
        findViewById<Button>(R.id.buttonE).setOnClickListener(this)
        findViewById<Button>(R.id.buttonF).setOnClickListener(this)

        findViewById<Button>(R.id.buttonZero).setOnClickListener(this)
        findViewById<Button>(R.id.buttonOne).setOnClickListener(this)
        findViewById<Button>(R.id.buttonTwo).setOnClickListener(this)
        findViewById<Button>(R.id.buttonThree).setOnClickListener(this)
        findViewById<Button>(R.id.buttonFour).setOnClickListener(this)
        findViewById<Button>(R.id.buttonFive).setOnClickListener(this)
        findViewById<Button>(R.id.buttonSix).setOnClickListener(this)
        findViewById<Button>(R.id.buttonSeven).setOnClickListener(this)
        findViewById<Button>(R.id.buttonEight).setOnClickListener(this)
        findViewById<Button>(R.id.buttonNine).setOnClickListener(this)

        findViewById<Button>(R.id.buttonPlus).setOnClickListener(this)
        findViewById<Button>(R.id.buttonMinus).setOnClickListener(this)
        findViewById<Button>(R.id.buttonMultiplication).setOnClickListener(this)
        findViewById<Button>(R.id.buttonDivision).setOnClickListener(this)
        findViewById<Button>(R.id.buttonPercent).setOnClickListener(this)
        findViewById<Button>(R.id.buttonParentheses).setOnClickListener(this)
        findViewById<Button>(R.id.buttonAC).setOnClickListener(this)
        findViewById<Button>(R.id.buttonBackspace).setOnClickListener(this)
        findViewById<Button>(R.id.buttonEquals).setOnClickListener(this)
        findViewById<Button>(R.id.buttonEngineerMode).setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.hexadecimal_item -> setCurrentNumberSystem(NumberSystem.HEXADECIMAL)
            R.id.decimal_item -> setCurrentNumberSystem(NumberSystem.DECIMAL)
            R.id.octal_item -> setCurrentNumberSystem(NumberSystem.OCTAL)
            R.id.binary_item -> setCurrentNumberSystem(NumberSystem.BINARY)

            R.id.buttonA -> appendToExpression("A")
            R.id.buttonB -> appendToExpression("B")
            R.id.buttonC -> appendToExpression("C")
            R.id.buttonD -> appendToExpression("D")
            R.id.buttonE -> appendToExpression("E")
            R.id.buttonF -> appendToExpression("F")

            R.id.buttonZero -> appendToExpression("0")
            R.id.buttonOne -> appendToExpression("1")
            R.id.buttonTwo -> appendToExpression("2")
            R.id.buttonThree -> appendToExpression("3")
            R.id.buttonFour -> appendToExpression("4")
            R.id.buttonFive -> appendToExpression("5")
            R.id.buttonSix -> appendToExpression("6")
            R.id.buttonSeven -> appendToExpression("7")
            R.id.buttonEight -> appendToExpression("8")
            R.id.buttonNine -> appendToExpression("9")

            R.id.buttonPlus -> appendToExpression("+")
            R.id.buttonMinus -> appendToExpression("-")
            R.id.buttonMultiplication -> appendToExpression("*")
            R.id.buttonDivision -> appendToExpression("/")
            R.id.buttonPercent -> appendToExpression("%")

            R.id.buttonParentheses -> {
                val openParenCount = currentExpression.count { it == '(' }
                val closeParenCount = currentExpression.count { it == ')' }
                if (openParenCount > closeParenCount && currentExpression.lastOrNull()?.isLetterOrDigit() == true) {
                    appendToExpression(")")
                } else {
                    appendToExpression("(")
                }
            }

            R.id.buttonAC -> {
                currentExpression = ""
                updateNumberDisplays()
            }

            R.id.buttonBackspace -> {
                if (currentExpression.isNotEmpty()) {
                    currentExpression = currentExpression.dropLast(1)
                    updateNumberDisplays()
                }
            }

            R.id.buttonEquals -> calculateResult()
            R.id.buttonEngineerMode -> {
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }

    private fun appendToExpression(string: String) {
        currentExpression += string
        updateNumberDisplays()
    }

    private fun updateNumberDisplays() {
        if (currentExpression.isEmpty()) {
            hexValue.text = "0"
            decValue.text = "0"
            octValue.text = "0"
            binValue.text = "0"
        } else {
            hexValue.text = convertExpression(currentExpression, 16)
            decValue.text = convertExpression(currentExpression, 10)
            octValue.text = convertExpression(currentExpression, 8)
            binValue.text = convertExpression(currentExpression, 2)
        }
    }

    private fun convertExpression(expression: String, targetRadix: Int): String {
        val tokens = expression.split("(?<=[+*/()])|(?=[+*/()])".toRegex()).filter { it.isNotBlank() }
        val currentRadix = getRadix(currentNumberSystem)

        val convertedTokens = tokens.map { token ->
            val number = token.toLongOrNull(currentRadix)
            if (number != null) {
                number.toString(targetRadix).uppercase(Locale.getDefault())
            } else {
                token
            }
        }
        return convertedTokens.joinToString("")
    }


    private fun setCurrentNumberSystem(system: NumberSystem) {
        currentNumberSystem = system
        currentExpression = ""
        updateNumberDisplays()
        updateNumberSystemUI()
        updateButtonStates()
    }

    private fun getRadix(system: NumberSystem): Int {
        return when (system) {
            NumberSystem.HEXADECIMAL -> 16
            NumberSystem.DECIMAL -> 10
            NumberSystem.OCTAL -> 8
            NumberSystem.BINARY -> 2
        }
    }

    private fun updateNumberSystemUI() {
        findViewById<View>(R.id.hexadecimal_indicator).visibility = if (currentNumberSystem == NumberSystem.HEXADECIMAL) View.VISIBLE else View.INVISIBLE
        findViewById<View>(R.id.decimal_indicator).visibility = if (currentNumberSystem == NumberSystem.DECIMAL) View.VISIBLE else View.INVISIBLE
        findViewById<View>(R.id.octal_indicator).visibility = if (currentNumberSystem == NumberSystem.OCTAL) View.VISIBLE else View.INVISIBLE
        findViewById<View>(R.id.binary_indicator).visibility = if (currentNumberSystem == NumberSystem.BINARY) View.VISIBLE else View.INVISIBLE
    }

    private fun updateButtonStates() {
        val radix = getRadix(currentNumberSystem)
        val isDec = currentNumberSystem == NumberSystem.DECIMAL

        findViewById<Button>(R.id.buttonA).isEnabled = radix > 10
        findViewById<Button>(R.id.buttonB).isEnabled = radix > 11
        findViewById<Button>(R.id.buttonC).isEnabled = radix > 12
        findViewById<Button>(R.id.buttonD).isEnabled = radix > 13
        findViewById<Button>(R.id.buttonE).isEnabled = radix > 14
        findViewById<Button>(R.id.buttonF).isEnabled = radix > 15

        findViewById<Button>(R.id.buttonTwo).isEnabled = radix > 2
        findViewById<Button>(R.id.buttonThree).isEnabled = radix > 3
        findViewById<Button>(R.id.buttonFour).isEnabled = radix > 4
        findViewById<Button>(R.id.buttonFive).isEnabled = radix > 5
        findViewById<Button>(R.id.buttonSix).isEnabled = radix > 6
        findViewById<Button>(R.id.buttonSeven).isEnabled = radix > 7
        findViewById<Button>(R.id.buttonEight).isEnabled = radix > 8
        findViewById<Button>(R.id.buttonNine).isEnabled = radix > 9

        findViewById<Button>(R.id.buttonDot).isEnabled = isDec
    }

    private fun calculateResult() {
        if (currentExpression.isEmpty()) return

        try {
            val decimalExpression = convertExpressionToDecimal(currentExpression)
            val result = ExpressionBuilder(decimalExpression).build().evaluate().toLong()
            currentExpression = result.toString(getRadix(currentNumberSystem)).uppercase(Locale.getDefault())
            updateNumberDisplays()

        } catch (e: Exception) {
            currentExpression = ""
            decValue.text = getString(R.string.error_message)
        }
    }

    private fun convertExpressionToDecimal(expression: String): String {
        val radix = getRadix(currentNumberSystem)
        if (radix == 10) return expression

        val tokens = expression.split("(?<=[+*/()])|(?=[+*/()])".toRegex()).filter { it.isNotBlank() }
        val decimalTokens = tokens.map { token ->
            token.toLongOrNull(radix)?.toString() ?: token
        }
        return decimalTokens.joinToString("")
    }
}
