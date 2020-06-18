package by.mrc.android.habit_manager.list

import android.graphics.Color
import android.provider.Settings
import android.view.*
import androidx.appcompat.content.res.AppCompatResources.getColorStateList
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import by.mrc.android.habit_manager.MainActivity
import by.mrc.android.habit_manager.R
import by.mrc.android.habit_manager.data.Habit
import by.mrc.android.habit_manager.data.HabitColorEnum
import by.mrc.android.habit_manager.databinding.HabitListItemBinding
import by.mrc.android.habit_manager.settings.SettingsValues
import java.text.DecimalFormat

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
        val color = when (current.color) {
            HabitColorEnum.RED.toString() -> Color.rgb(213,0,0)
            HabitColorEnum.ORANGE.toString() -> Color.rgb(255,171,0)
            HabitColorEnum.YELLOW.toString() -> Color.rgb(255,214,0)
            HabitColorEnum.GREEN.toString() -> Color.rgb(0,200,83)
            HabitColorEnum.AQUA.toString() -> Color.rgb(0,184,212)
            HabitColorEnum.BLUE.toString() -> Color.rgb(41,98,255)
            HabitColorEnum.PURPLE.toString() -> Color.rgb(170,0,255)
            else -> null
        }
        if (color != null)
            binding.habitListItemName.setTextColor(color)
    }
}