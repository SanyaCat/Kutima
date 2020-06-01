package by.mrc.android.habit_manager.list

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.mrc.android.habit_manager.MainActivity
import by.mrc.android.habit_manager.R
import by.mrc.android.habit_manager.data.Habit
import by.mrc.android.habit_manager.databinding.FragmentListBinding
import by.mrc.android.habit_manager.list.edit_habit.EditHabitFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

//import by.mrc.android.habit_manager.databinding.FragmentListBinding

// Here list of habits is showing
class ListFragment : Fragment() {

    // Initialize ViewModel
    private val listViewModel: ListViewModel by lazy {
        ViewModelProvider(this).get(ListViewModel::class.java)
    }
    private lateinit var binding: FragmentListBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HabitListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Initialize Binding
        binding = FragmentListBinding.inflate(inflater)

        recyclerView = binding.recyclerViewList
        adapter = HabitListAdapter(
            HabitListAdapter.EditHabitButtonListener { id ->
                // Edit Habit Listener
                EditHabitFragment.id = id
                val currentHabit = findHabitById(id)
                EditHabitFragment.name = currentHabit.name
                EditHabitFragment.status = EditHabitFragment.EditHabitStatusEnum.EditHabitInProgress
                findNavController().navigate(R.id.action_nav_list_to_nav_add_habit)
                //Toast.makeText(MainActivity.context, "Editing $id...", Toast.LENGTH_SHORT).show()
            }, HabitListAdapter.DeleteHabitButtonListener { id ->
                // Delete Habit Button
                // TODO("Delete Habit")
                // TODO("Are you sure you want to delete habit?")
                val currentHabit = findHabitById(id)
                Toast.makeText(MainActivity.context, "Deleting ${currentHabit.name}...", Toast.LENGTH_SHORT).show()
                listViewModel.delete(currentHabit)
                recyclerView.invalidate()
            }
        )
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(MainActivity.context)

        // Add Habit Button
        val fab: FloatingActionButton = binding.fab
        fab.setOnClickListener { view ->
            // TODO("Add new Habit")
            EditHabitFragment.status = EditHabitFragment.EditHabitStatusEnum.AddHabitInProgress
            view.findNavController().navigate(R.id.action_nav_list_to_nav_add_habit)
        }

        listViewModel.habits.observe(viewLifecycleOwner, Observer { //habits ->
            it?.let { adapter.submitList(it)}
        })

        return binding.root
    }

    private fun findHabitById(id: Int): Habit {
        var current: Habit? = null
        for (i in listViewModel.habits.value!!) {
            if (i.id == id) {
                current = i
                break
            }
        }
        if (current == null) {
            return throw Resources.NotFoundException("There is no habit with such ID")
        }
        return current
    }

    override fun onResume() {
        super.onResume()

        when (EditHabitFragment.status) {
            EditHabitFragment.EditHabitStatusEnum.AddHabitSucceed -> {
                val id = if (listViewModel.habits.value!!.isNotEmpty()) (listViewModel.habits.value!!.last().id + 1) else 0
                listViewModel.insert(
                    Habit(
                        name = EditHabitFragment.name.toString(),
                        id = id,
                        timeToComplete = 66
                    )
                )
                EditHabitFragment.id = null
                EditHabitFragment.name = null
                EditHabitFragment.status = null
            }
            EditHabitFragment.EditHabitStatusEnum.EditHabitSucceed -> {
                val currentHabit = findHabitById(EditHabitFragment.id!!)
                currentHabit.name = EditHabitFragment.name!!
                listViewModel.update(currentHabit)
                EditHabitFragment.id = null
                EditHabitFragment.name = null
                EditHabitFragment.status = null
            }
            else -> {
                // Cancel Editing/Adding
                EditHabitFragment.id = null
                EditHabitFragment.name = null
                EditHabitFragment.status = null
            }
        }
    }
}