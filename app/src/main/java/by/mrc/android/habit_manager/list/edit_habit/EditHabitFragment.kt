package by.mrc.android.habit_manager.list.edit_habit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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

    private lateinit var editHabitViewModel: EditHabitViewModel

    // Initialize ViewModel
    private val listViewModel: EditHabitViewModel by lazy {
        ViewModelProvider(this).get(EditHabitViewModel::class.java)
    }
    private lateinit var binding: FragmentEditHabitBinding

    companion object {
        var id: Int? = null
        var name: String? = null
        var status: EditHabitStatusEnum? = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditHabitBinding.inflate(inflater)

//        listViewModel.name.observe(viewLifecycleOwner, Observer {
//            binding.inputNameEditText.text = it
//        })

        binding.acceptButton.setOnClickListener {
            if (binding.inputNameEditText.text.isNullOrEmpty()) {
                binding.inputNameEditText.error = "Input habit name"
            } else {
                name = binding.inputNameEditText.text.toString()
                if (status == EditHabitStatusEnum.AddHabitInProgress) status = EditHabitStatusEnum.AddHabitSucceed else
                if (status == EditHabitStatusEnum.EditHabitInProgress) status = EditHabitStatusEnum.EditHabitSucceed
                view?.findNavController()?.popBackStack() // go out of here
            }
        }

        binding.cancelButton.setOnClickListener {
            status = EditHabitStatusEnum.Canceled
            view?.findNavController()?.popBackStack() // go out of here
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.inputNameEditText.setText(name)
    }
}
