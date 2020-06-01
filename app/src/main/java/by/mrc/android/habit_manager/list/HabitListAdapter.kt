package by.mrc.android.habit_manager.list

import android.view.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import by.mrc.android.habit_manager.MainActivity
import by.mrc.android.habit_manager.data.Habit
import by.mrc.android.habit_manager.databinding.HabitListItemBinding

class HabitListAdapter(
    val editHabitListener: EditHabitButtonListener,
    val deleteHabitListener: DeleteHabitButtonListener
) : ListAdapter<Habit, HabitListAdapter.HabitViewHolder>(HabitListDiffCallback()) {

    lateinit var binding: HabitListItemBinding

    class HabitListDiffCallback : DiffUtil.ItemCallback<Habit>() {
        override fun areItemsTheSame(oldItem: Habit, newItem: Habit): Boolean
                = oldItem.id == newItem.id // compares item id's

        override fun areContentsTheSame(oldItem: Habit, newItem: Habit): Boolean
                = oldItem == newItem // compares items
    }

    class HabitViewHolder(binding: HabitListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val name = binding.habitListItemName
        val editButton = binding.habitListItemEditButton
        val deleteButton = binding.habitListItemDeleteButton
    }

    class EditHabitButtonListener(val editHabitListener: (id: Int) -> Unit) {
        fun onClick(habit: Habit) = editHabitListener(habit.id)
    }

    class DeleteHabitButtonListener(val deleteHabitListener: (id: Int) -> Unit) {
        fun onClick(habit: Habit) = deleteHabitListener(habit.id)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        binding = HabitListItemBinding.inflate(LayoutInflater.from(MainActivity.context), parent, false)
        return HabitViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val current = getItem(position)
        binding.habitListItemName.text = current.name
        binding.habitListItemEditButton.setOnClickListener { editHabitListener.onClick(current) }
        binding.habitListItemDeleteButton.setOnClickListener { deleteHabitListener.onClick(current) }
    }
}