package com.example.currencyconverterapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class RegisterActivity : AppCompatActivity() {

    lateinit var name_register: EditText
    lateinit var email_register: EditText
    lateinit var password_register: EditText
    lateinit var repassword_register: EditText
    lateinit var button_register: Button
    lateinit var button_login: Button
    lateinit var database: DatabaseClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        name_register = findViewById(R.id.et_username_register)
        email_register = findViewById(R.id.et_email_register)
        password_register = findViewById(R.id.et_password_register)
        repassword_register = findViewById(R.id.et_repassword_register)
        button_register = findViewById(R.id.button_register_reg)
        button_login = findViewById(R.id.button_login_reg)
        database = DatabaseClass(this)

        button_register.setOnClickListener {
            if (name_register.text.toString() == "" || email_register.text.toString() == "" || password_register.text.toString() == "" || repassword_register.text.toString() == ""){
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_LONG).show()
            }
            else{
                if (password_register.text.toString() == repassword_register.text.toString()){
                    database.insertUser(name_register.text.toString(), email_register.text.toString(), password_register.text.toString())
                    Toast.makeText(applicationContext, "User Registered Successfully. Please login", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(applicationContext, "Password mismatch. Try again", Toast.LENGTH_LONG).show()
                }
            }
        }

        button_login.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }


    }
}