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

    private lateinit var expressionTextView: TextView
    private lateinit var hexValue: TextView
    private lateinit var decValue: TextView
    private lateinit var octValue: TextView
    private lateinit var binValue: TextView

    private var currentNumberSystem = NumberSystem.DECIMAL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_programmer_mode)

        expressionTextView = findViewById(R.id.textView)
        hexValue = findViewById(R.id.hexadecimal_value)
        decValue = findViewById(R.id.decimal_value)
        octValue = findViewById(R.id.octal_value)
        binValue = findViewById(R.id.binary_value)

        setupClickListeners()
        updateNumberSystemUI()
    }

    private fun setupClickListeners() {
        // Number system selectors
        findViewById<View>(R.id.hexadecimal_item).setOnClickListener(this)
        findViewById<View>(R.id.decimal_item).setOnClickListener(this)
        findViewById<View>(R.id.octal_item).setOnClickListener(this)
        findViewById<View>(R.id.binary_item).setOnClickListener(this)

        // Buttons
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
                val currentText = expressionTextView.text.toString()
                val openParenCount = currentText.count { it == '(' }
                val closeParenCount = currentText.count { it == ')' }
                if (openParenCount > closeParenCount && currentText.lastOrNull()?.isDigit() == true) {
                    appendToExpression(")")
                } else {
                    appendToExpression("(")
                }
            }

            R.id.buttonAC -> {
                expressionTextView.text = ""
                updateNumberDisplays(0)
            }

            R.id.buttonBackspace -> {
                val currentText = expressionTextView.text.toString()
                if (currentText.isNotEmpty()) {
                    expressionTextView.text = currentText.dropLast(1)
                    updateValueFromExpression()
                }
            }

            R.id.buttonEquals -> calculateResult()
            R.id.buttonEngineerMode -> {
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }

    private fun appendToExpression(string: String) {
        expressionTextView.append(string)
        updateValueFromExpression()
    }

    private fun updateValueFromExpression() {
        val lastNumber = expressionTextView.text.toString().split("+", "-", "*", "/", "(", ")").lastOrNull { it.isNotEmpty() } ?: "0"
        val radix = getRadix(currentNumberSystem)
        val number = lastNumber.toLongOrNull(radix) ?: 0
        updateNumberDisplays(number)
    }

    private fun updateNumberDisplays(number: Long) {
        hexValue.text = number.toString(16).uppercase(Locale.getDefault())
        decValue.text = number.toString(10)
        octValue.text = number.toString(8)
        binValue.text = number.toString(2)
    }

    private fun setCurrentNumberSystem(system: NumberSystem) {
        currentNumberSystem = system
        updateNumberSystemUI()
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

    private fun calculateResult() {
        try {
            val expressionString = expressionTextView.text.toString()
            val expression = ExpressionBuilder(expressionString).build()
            val result = expression.evaluate().toLong()
            expressionTextView.text = result.toString(getRadix(currentNumberSystem))
            updateNumberDisplays(result)
        } catch (e: Exception) {
            expressionTextView.text = getString(R.string.error_message)
        }
    }
}
