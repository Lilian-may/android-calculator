package com.example.kuzminkirov_chyort1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kuzminkirov_chyort1.R
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var expressionTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        expressionTextView = findViewById(R.id.textView)

        setupClickListeners()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupClickListeners() {
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

        findViewById<Button>(R.id.buttonDot).setOnClickListener(this)
        findViewById<Button>(R.id.buttonParentheses).setOnClickListener(this)
        findViewById<Button>(R.id.buttonPercent).setOnClickListener(this)

        findViewById<Button>(R.id.buttonAC).setOnClickListener(this)
        findViewById<Button>(R.id.buttonBackspace).setOnClickListener(this)
        findViewById<Button>(R.id.buttonEquals).setOnClickListener(this)

        // Исправлено: Назначаем обработчик для правильной кнопки
        findViewById<Button>(R.id.buttonProgrammerMode).setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
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
            R.id.buttonDot -> appendToExpression(".")

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
            }

            R.id.buttonBackspace -> {
                val currentText = expressionTextView.text.toString()
                if (currentText.isNotEmpty()) {
                    expressionTextView.text = currentText.dropLast(1)
                }
            }

            R.id.buttonEquals -> {
                calculateResult()
            }

            R.id.buttonProgrammerMode -> {
                startActivity(Intent(this, ProgrammerModeActivity::class.java))
            }
        }
    }

    private fun appendToExpression(string: String) {
        expressionTextView.append(string)
    }

    private fun calculateResult() {
        val expressionString = expressionTextView.text.toString()
        try {
            val expression = ExpressionBuilder(expressionString).build()
            val result = expression.evaluate()

            if (result == result.toLong().toDouble()) {
                expressionTextView.text = result.toLong().toString()
            } else {
                expressionTextView.text = result.toString()
            }
        } catch (e: Exception) {
            expressionTextView.text = getString(R.string.error_message)
        }
    }
}
