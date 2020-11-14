package by.mrc.android.habit_manager.list.edit_habit

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.findNavController
import by.mrc.android.habit_manager.databinding.FragmentEditHabitBinding

class EditHabitFragment : DialogFragment() {

    enum class EditHabitStatusEnum {
        AddHabitInProgress,
        EditHabitInProgress,
        AddHabitSucceed,
        EditHabitSucceed,
        Canceled
    }

    private lateinit var binding: FragmentEditHabitBinding

    companion object {
        var id: Int? = null
        var name: String? = null
        var description: String? = null
        var color: Int? = null
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
                    binding.radioButtonRed.isChecked -> Color.rgb(213, 0, 0)
                    binding.radioButtonOrange.isChecked -> Color.rgb(255, 171, 0)
                    binding.radioButtonYellow.isChecked -> Color.rgb(255, 214, 0)
                    binding.radioButtonGreen.isChecked -> Color.rgb(0, 200, 83)
                    binding.radioButtonAqua.isChecked -> Color.rgb(0, 184, 212)
                    binding.radioButtonBlue.isChecked -> Color.rgb(41, 98, 255)
                    binding.radioButtonPurple.isChecked -> Color.rgb(170, 0, 255)
                    else -> Color.BLACK
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
            Color.rgb(213, 0, 0) -> binding.radioButtonRed.isChecked = true
            Color.rgb(255, 171, 0) -> binding.radioButtonOrange.isChecked = true
            Color.rgb(255, 214, 0) -> binding.radioButtonYellow.isChecked = true
            Color.rgb(0, 200, 83) -> binding.radioButtonGreen.isChecked = true
            Color.rgb(0, 184, 212) -> binding.radioButtonAqua.isChecked = true
            Color.rgb(41, 98, 255) -> binding.radioButtonBlue.isChecked = true
            Color.rgb(170, 0, 255) -> binding.radioButtonPurple.isChecked = true
            else -> binding.radioButtonBlack.isChecked = true
        }
    }
}