package com.example.doubletapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {
    private val SECOND_ACTIVITY_REQUEST_CODE = 0
    val list = mutableListOf<Habit>()
    val adapter = HabitsAdapter(list)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val listView = findViewById<FloatingActionButton>(R.id.recyclerView) as RecyclerView
        val manager = LinearLayoutManager(this);
        listView.layoutManager = manager
        listView.adapter = adapter
    }

    fun startNewHabit(view: View) {
        val intent = Intent(this, NewHabit::class.java)
        startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE)
    }

    fun editHabit(view: View) {
        val intent = Intent(this, NewHabit::class.java)
        startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            SECOND_ACTIVITY_REQUEST_CODE -> {
                if (resultCode == RESULT_OK) {
                    val name = data?.getStringExtra("name") ?: ""
                    val description = data?.getStringExtra("description") ?: ""
                    val priority_string = data?.getStringExtra("priority") ?: ""
                    val priority = when(priority_string){
                        "Высокий приоритет" -> Priority.HIGH
                        "Средний приоритет" -> Priority.MEDIUM
                        "Низкий приоритет" -> Priority.LOW
                        else -> throw Exception()
                    }
                    val type = data?.getStringExtra("type") ?: "" == "true"
                    val period = (data?.getStringExtra("period") ?: "0").toInt()
                    val times = (data?.getStringExtra("times") ?: "0").toInt()
                    list.add(0, Habit(name, description, priority, type, period, times))
                    adapter.notifyItemInserted(0)
                }
            }
        }
    }
}

enum class Priority(val pr: String) {
    HIGH("Высокий приоритет"),
    MEDIUM("Средний приоритет"),
    LOW("Низкий приоритет")
}

class Habit(val name: String, val description: String, val priority:  Priority, val isGood: Boolean, val Period: Int, val Times: Int, val Color: Int = 0) {

}

class HabitsAdapter (private val mHabit: MutableList<Habit>) : RecyclerView.Adapter<HabitsAdapter.ViewHolder>()
{
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView = itemView.findViewById<TextView>(R.id.name)
        val descriptionTextView = itemView.findViewById<TextView>(R.id.description)
        val priorityTextView = itemView.findViewById<TextView>(R.id.priority)
        val typeTextView = itemView.findViewById<TextView>(R.id.type)
        val periodTextView = itemView.findViewById<TextView>(R.id.period)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitsAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val habitView = inflater.inflate(R.layout.item_habit, parent, false)
        return ViewHolder(habitView)
    }

    override fun onBindViewHolder(viewHolder: HabitsAdapter.ViewHolder, position: Int) {
        val habit: Habit = mHabit.get(position)
        viewHolder.itemView
        val textView = viewHolder.nameTextView
        textView.setText(habit.name)
        val description = viewHolder.descriptionTextView
        description.setText(habit.description)
        val priority = viewHolder.priorityTextView
        priority.setText(habit.priority.pr)
        val type = viewHolder.typeTextView
        if (habit.isGood) {
            type.setText("Полезная")
        }
        else type.setText("Вредная")
        val period = viewHolder.periodTextView
        period.setText("Сделать ${habit.Times} раз за ${habit.Period}")
    }

    override fun getItemCount(): Int {
        return mHabit.size
    }
}

