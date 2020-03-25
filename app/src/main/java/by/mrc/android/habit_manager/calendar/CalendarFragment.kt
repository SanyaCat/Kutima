package by.mrc.android.habit_manager.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import by.mrc.android.habit_manager.databinding.FragmentCalendarBinding

// Here user interacts with calendar
class CalendarFragment : Fragment() {

    // Initialize ViewModel
    private val calendarViewModel: CalendarViewModel by lazy {
        ViewModelProvider(this).get(CalendarViewModel::class.java)
    }
    private lateinit var binding: FragmentCalendarBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Initialize Binding
        binding = FragmentCalendarBinding.inflate(inflater)
        // Sync ViewModel and UI
        calendarViewModel.text.observe(this, Observer {
            binding.textCalendar.text = it
        })
        return binding.root
    }
}