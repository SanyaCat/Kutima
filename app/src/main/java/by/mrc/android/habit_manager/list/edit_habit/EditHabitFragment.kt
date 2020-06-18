package by.mrc.android.habit_manager.list.edit_habit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import by.mrc.android.habit_manager.data.HabitColorEnum
import by.mrc.android.habit_manager.databinding.FragmentEditHabitBinding

class EditHabitFragment : DialogFragment() {

    enum class EditHabitStatusEnum {
        AddHabitInProgress,
        EditHabitInProgress,
        AddHabitSucceed,
        EditHabitSucceed,
        Canceled
    }

    private val editHabitViewModel: EditHabitViewModel by lazy {
        ViewModelProvider(this).get(EditHabitViewModel::class.java)
    }

    private lateinit var binding: FragmentEditHabitBinding

    companion object {
        var id: Int? = null
        var name: String? = null
        var description: String? = null
        var color: String? = null
        var targetTime: Int? = null
        var status: EditHabitStatusEnum? = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentEditHabitBinding.inflate(
                inflater
            )

        binding.acceptButton.setOnClickListener {
            var noErrors = true
            if (binding.inputNameEditText.text.isNullOrEmpty()) {
                binding.inputNameEditText.error = "Input habit name"
                noErrors = false
            }

            if (binding.inputTimeEditText.text.isNullOrEmpty()) {
                binding.inputTimeEditText.error = "Input target time"
                noErrors = false
            }

            if (noErrors) {
                name = binding.inputNameEditText.text.toString()
                description = binding.inputDescriptionEditText.text.toString()
                targetTime = binding.inputTimeEditText.text.toString().toInt()
                color = when {
                    binding.radioButtonRed.isChecked -> HabitColorEnum.RED.toString()
                    binding.radioButtonOrange.isChecked -> HabitColorEnum.ORANGE.toString()
                    binding.radioButtonYellow.isChecked -> HabitColorEnum.YELLOW.toString()
                    binding.radioButtonGreen.isChecked -> HabitColorEnum.GREEN.toString()
                    binding.radioButtonAqua.isChecked -> HabitColorEnum.AQUA.toString()
                    binding.radioButtonBlue.isChecked -> HabitColorEnum.BLUE.toString()
                    binding.radioButtonPurple.isChecked -> HabitColorEnum.PURPLE.toString()
                    else -> HabitColorEnum.BLACK.toString()
                }

                if (status == EditHabitStatusEnum.AddHabitInProgress) status =
                    EditHabitStatusEnum.AddHabitSucceed else
                if (status == EditHabitStatusEnum.EditHabitInProgress) status =
                    EditHabitStatusEnum.EditHabitSucceed
                view?.findNavController()?.popBackStack() // go out of here
            }
        }

        binding.cancelButton.setOnClickListener {
            status =
                EditHabitStatusEnum.Canceled
            view?.findNavController()?.popBackStack() // go out of here
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.inputNameEditText.setText(name)
        binding.inputDescriptionEditText.setText(description)
        if (targetTime != null)
            binding.inputTimeEditText.setText(targetTime.toString())
        when (color) {
            HabitColorEnum.RED.toString() -> binding.radioButtonRed.isChecked = true
            HabitColorEnum.ORANGE.toString() -> binding.radioButtonOrange.isChecked = true
            HabitColorEnum.YELLOW.toString() -> binding.radioButtonYellow.isChecked = true
            HabitColorEnum.GREEN.toString() -> binding.radioButtonGreen.isChecked = true
            HabitColorEnum.AQUA.toString() -> binding.radioButtonAqua.isChecked = true
            HabitColorEnum.BLUE.toString() -> binding.radioButtonBlue.isChecked = true
            HabitColorEnum.PURPLE.toString() -> binding.radioButtonPurple.isChecked = true
            else -> binding.radioButtonBlack.isChecked = true
        }
    }
}