package com.example.currencyconverterapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : AppCompatActivity() {

    lateinit var et_login: EditText
    lateinit var et_password: EditText
    lateinit var button_login: Button
    lateinit var button_register: Button
    lateinit var database: DatabaseClass


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        et_login = findViewById(R.id.et_username_login)
        et_password = findViewById(R.id.et_password_login)
        button_login = findViewById(R.id.button_login)
        button_register = findViewById(R.id.button_register)
        database = DatabaseClass(this)

        button_login.setOnClickListener {
            if (et_login.text.toString() == "" || et_password.text.toString() == ""){
                Toast.makeText(applicationContext, "Fill in all the fields", Toast.LENGTH_LONG).show()
            }
            else{
                if (database.checkUser(et_login.text.toString(), et_password.text.toString())){
                    Toast.makeText(applicationContext, et_login.text.toString()+" logged in successfully", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(applicationContext, "Invalid username or password. Try again", Toast.LENGTH_LONG).show()
                }
            }
        }

        button_register.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "FIll in all the fields to register", Toast.LENGTH_LONG).show()
        }

    }
}