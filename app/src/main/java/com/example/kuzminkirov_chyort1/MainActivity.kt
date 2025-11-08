package com.example.kuzminkirov_chyort1

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity(), View.OnClickListener {

    // TextView для отображения выражения и результата
    private lateinit var expressionTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Инициализируем TextView
        expressionTextView = findViewById(R.id.textView)

        // Устанавливаем обработчики для всех кнопок
        setupClickListeners()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupClickListeners() {
        // Находим все кнопки по ID и устанавливаем для них listener
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
    }

    override fun onClick(view: View) {
        // Определяем, какая кнопка была нажата, по ее ID
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
            R.id.buttonParentheses -> appendToExpression("()")


            R.id.buttonAC -> {
                expressionTextView.text = ""
            }

            R.id.buttonBackspace -> {
                val currentText = expressionTextView.text.toString()
                if (currentText.isNotEmpty()) {
                    expressionTextView.text = currentText.substring(0, currentText.length - 1)
                }
            }

            R.id.buttonEquals -> {
                calculateResult()
            }
        }
    }

    private fun appendToExpression(string: String) {
        expressionTextView.append(string)
    }

    private fun calculateResult() {
        val expressionString = expressionTextView.text.toString()
        try {
            // Используем ExpressionBuilder для вычисления выражения
            val expression = ExpressionBuilder(expressionString).build()
            val result = expression.evaluate()

            // Проверяем, является ли результат целым числом
            if (result == result.toLong().toDouble()) {
                expressionTextView.text = result.toLong().toString()
            } else {
                expressionTextView.text = result.toString()
            }
        } catch (e: Exception) {
            // В случае ошибки выводим сообщение
            expressionTextView.text = "Error"
        }
    }
}
