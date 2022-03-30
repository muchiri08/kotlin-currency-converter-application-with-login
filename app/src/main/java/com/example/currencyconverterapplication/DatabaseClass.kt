package com.example.currencyconverterapplication

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseClass (context: Context) : SQLiteOpenHelper(context, databaseName, factory, version) {
    companion object{
        internal val databaseName = "userDb"
        internal val factory = null
        internal val version = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE user_table(id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, email VARCHAR, password VARCHAR)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun insertUser(name: String, email: String, password: String){
        val db: SQLiteDatabase = writableDatabase
        val cv: ContentValues = ContentValues()
        cv.put("name", name)
        cv.put("email", email)
        cv.put("password", password)

        db.insert("user_table", null, cv)
        db.close()
    }

    fun checkUser(name: String, password: String):Boolean{
        val db: SQLiteDatabase = readableDatabase
        val query = "SELECT * FROM user_table WHERE name = '$name' AND password = '$password'"
        val cursor: Cursor = db.rawQuery(query, null)

        if (cursor.count <= 0){
            cursor.close()
            return false
        }
        cursor.close()
        return true
    }
}