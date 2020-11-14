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
import com.google.gson.Gson
import java.util.*

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
                EditHabitFragment.description = currentHabit.description
                EditHabitFragment.color = currentHabit.color
                EditHabitFragment.targetTime = currentHabit.targetTime
                EditHabitFragment.status = EditHabitFragment.EditHabitStatusEnum.EditHabitInProgress

                findNavController().navigate(R.id.action_nav_list_to_nav_add_habit)
            }, HabitListAdapter.DeleteHabitButtonListener { id ->
                // Delete Habit Button
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
            EditHabitFragment.status = EditHabitFragment.EditHabitStatusEnum.AddHabitInProgress
            view.findNavController().navigate(R.id.action_nav_list_to_nav_add_habit)
        }

        listViewModel.habits.observe(viewLifecycleOwner, Observer {
            it?.let { adapter.submitList(it) }
        })
        ListViewModel.instance = listViewModel

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
            @Suppress("UNREACHABLE_CODE")
            return throw Resources.NotFoundException("There is no habit with such ID")
        }
        return current
    }

    override fun onResume() {
        super.onResume()

        when (EditHabitFragment.status) {
            // When habit is adding
            EditHabitFragment.EditHabitStatusEnum.AddHabitSucceed -> {
                val id = if (listViewModel.habits.value!!.isNotEmpty()) (listViewModel.habits.value!!.last().id + 1) else 0
                val gson = Gson()

                listViewModel.insert( Habit(
                        id = id,
                        name = EditHabitFragment.name.toString(),
                        description = EditHabitFragment.description.toString(),
                        color = EditHabitFragment.color!!,
                        targetTime = EditHabitFragment.targetTime!!,
                        progress = gson.toJson(MutableList(0) { Date(0) })
                ) )

                EditHabitFragment.id = null
                EditHabitFragment.name = null
                EditHabitFragment.description = null
                EditHabitFragment.color = null
                EditHabitFragment.targetTime = null
                EditHabitFragment.status = null
            }

            // When habit is editing
            EditHabitFragment.EditHabitStatusEnum.EditHabitSucceed -> {
                val currentHabit = findHabitById(EditHabitFragment.id!!)
                currentHabit.name = EditHabitFragment.name!!
                currentHabit.description = EditHabitFragment.description
                currentHabit.color = EditHabitFragment.color!!
                currentHabit.targetTime = EditHabitFragment.targetTime!!

                listViewModel.update(currentHabit)

                EditHabitFragment.id = null
                EditHabitFragment.name = null
                EditHabitFragment.description = null
                EditHabitFragment.color = null
                EditHabitFragment.targetTime = null
                EditHabitFragment.status = null
            }

            // When habit editing/adding is canceled
            else -> {
                EditHabitFragment.id = null
                EditHabitFragment.name = null
                EditHabitFragment.description = null
                EditHabitFragment.color = null
                EditHabitFragment.targetTime = null
                EditHabitFragment.status = null
            }
        }
    }
}