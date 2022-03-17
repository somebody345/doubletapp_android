package com.example.doubletapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class NewHabit : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_habit)
    }

    fun addNewHabit(view: View) {
        val name = (findViewById(R.id.new_name) as EditText).text.toString()
        val description = (findViewById(R.id.new_description) as EditText).text.toString()
        val priority = (findViewById(R.id.spinner) as Spinner).selectedItem.toString()
        val type = (findViewById(R.id.radioButtonGood) as RadioButton).isChecked.toString()
        val period = (findViewById(R.id.new_period) as EditText).text.toString()
        val times = (findViewById(R.id.new_times) as EditText).text.toString()

        val intent = Intent()
        intent.putExtra("name", name)
        intent.putExtra("description", description)
        intent.putExtra("priority", priority)
        intent.putExtra("type", type)
        intent.putExtra("period", period)
        intent.putExtra("times", times)
        setResult(RESULT_OK, intent)
        finish()
    }
}