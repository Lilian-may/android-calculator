package com.example.kuzminkirov_chyort1

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

private lateinit var buttonZeroClick: Button
private lateinit var buttonOneClick: Button
private lateinit var buttonTwoClick: Button
private lateinit var buttonThreeClick: Button
private lateinit var buttonFourClick: Button
private lateinit var buttonFiveClick: Button
private lateinit var buttonSixClick: Button
private lateinit var buttonSevenClick: Button
private lateinit var buttonEightClick: Button
private lateinit var buttonNineClick: Button
private lateinit var buttonBackspaceClick: Button
private lateinit var buttonEqualsClick: Button



class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        initializeButtons()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initializeButtons() {
        buttonZeroClick = findViewById(R.id.buttonZero)
        buttonOneClick = findViewById(R.id.buttonOne)
        buttonTwoClick = findViewById(R.id.buttonTwo)
        buttonThreeClick = findViewById(R.id.buttonThree)
        buttonFourClick = findViewById(R.id.buttonFour)
        buttonFiveClick = findViewById(R.id.buttonFive)
        buttonSixClick = findViewById(R.id.buttonSix)
        buttonSevenClick = findViewById(R.id.buttonSeven)
        buttonEightClick = findViewById(R.id.buttonEight)
        buttonNineClick = findViewById(R.id.buttonNine)
        buttonBackspaceClick = findViewById(R.id.buttonBackspace)
        buttonEqualsClick = findViewById(R.id.buttonEquals)

    }

    private fun buttonClicks(view: View) {
        Toast.makeText(this, "Button clicked", Toast.LENGTH_SHORT).show()
    }

}