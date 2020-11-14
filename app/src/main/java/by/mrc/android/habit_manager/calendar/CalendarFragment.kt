package by.mrc.android.habit_manager.calendar

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import by.mrc.android.habit_manager.MainActivity
import by.mrc.android.habit_manager.data.Habit
import by.mrc.android.habit_manager.databinding.FragmentCalendarBinding
import by.mrc.android.habit_manager.list.ListViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import sun.bob.mcalendarview.listeners.OnDateClickListener
import sun.bob.mcalendarview.vo.DateData

// Here user interacts with calendar
class CalendarFragment : Fragment() {

    // Initialize ViewModel
    private lateinit var binding: FragmentCalendarBinding

    var habit: Habit? = null
    private val gson = Gson()
    val type = object : TypeToken<MutableList<DateData>>() {}.type!!
    lateinit var progress: MutableList<DateData>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalendarBinding.inflate(inflater)

        binding.calendarView.setOnDateClickListener(object : OnDateClickListener() {
            override fun onDateClick(view: View?, date: DateData?) {
                if (!progress.contains(date)) {
                    binding.calendarView.markDate(date)
                    progress.add(date!!)

                    if (binding.calendarView.markedDates.all.size == habit!!.targetTime)
                        Toast.makeText(MainActivity.context, "Congratulations! You just completed your habit!", Toast.LENGTH_LONG).show()
                } else {
                    binding.calendarView.unMarkDate(date)
                    progress.remove(date)
                }
                calendarUpdate()
            }
        })

        if (ListViewModel.instance?.habits?.value?.size == 0) {
            Toast.makeText(MainActivity.context, "Error: you have no habits!", Toast.LENGTH_SHORT).show()
            return null
        }
        habit = ListViewModel.instance?.habits?.value?.get(0)
        binding.txtvHabitValue.text = habit!!.name

        progress = gson.fromJson<MutableList<DateData>>(habit?.progress, type)!!

        binding.habitValueLayout.setOnClickListener {
            var index = -1

            val list = MutableList(0) {""}

            for (i in ListViewModel.instance!!.habits.value!!)
                list.add(i.name)

            val selectHabitDialog: AlertDialog? = requireActivity().let {
                val builder = AlertDialog.Builder(it)
                builder.apply {
                    setSingleChoiceItems(list.toTypedArray(),
                        index) { dialog, which ->
                        index = which
                        saveHabit()

                        habit = ListViewModel.instance!!.habits.value!![index]
                        progress = gson.fromJson(habit?.progress, type)
                        binding.txtvHabitValue.text = habit!!.name
                        binding.calendarView.setMarkedStyle(android.R.style.Theme_Light, habit!!.color)
                        calendarUpdate()
                        dialog.dismiss()
                    }
                    setTitle("Habit")
                    setNegativeButton("Cancel") {_, _ -> }
                }
                builder.create()
            }
            selectHabitDialog!!.show()
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        if (habit != null) {
            binding.calendarView.setMarkedStyle(android.R.style.Theme_Light, habit!!.color)
            calendarUpdate()
        }
    }

    override fun onPause() {
        super.onPause()
        saveHabit()
    }

    fun calendarUpdate() {
        while (binding.calendarView.markedDates.all.size > 0)
            binding.calendarView.unMarkDate(binding.calendarView.markedDates.all[0])
        for (i in progress)
            binding.calendarView.markDate(DateData(i.year, i.month, i.day))
        binding.calendarView.invalidate()
    }

    private fun saveHabit() {
        if (habit != null) {
            habit!!.progress = gson.toJson(progress)
            ListViewModel.instance!!.update(habit!!)
        }
    }
}