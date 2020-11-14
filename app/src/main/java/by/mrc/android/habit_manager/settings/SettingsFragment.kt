package by.mrc.android.habit_manager.settings

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import by.mrc.android.habit_manager.databinding.FragmentSettingsBinding

// Here user set Settings
class SettingsFragment : Fragment() {

    companion object {
        lateinit var fragmentContext: SettingsFragment
    }

    // Initialize ViewModel
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Initialize Binding
        binding = FragmentSettingsBinding.inflate(inflater)

        fragmentContext = this
        binding.layoutTheme.setOnClickListener {
            onSelectThemeClick()
        }

        binding.txtvThemeValue.text = SettingsValues.theme.get()
        return binding.root
    }

    private fun onSelectThemeClick() {
        val listOfEnum = listOf(
            SettingsThemeEnum.GREEN.get(),
            SettingsThemeEnum.ORANGE.get(),
            SettingsThemeEnum.PURPLE.get()
        )

        var index = -1

        val selectThemeDialog: AlertDialog? = requireActivity().let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setSingleChoiceItems(listOfEnum.toTypedArray(),
                index) { dialog, which ->
                    index = which
                    SettingsValues.theme = when (which) {
                        0 -> SettingsThemeEnum.GREEN
                        1 -> SettingsThemeEnum.ORANGE
                        2 -> SettingsThemeEnum.PURPLE
                        else -> throw Error("ERROR")
                    }
                    Toast.makeText(context, "Please restart application to apply changes", Toast.LENGTH_LONG).show()
                    binding.txtvThemeValue.text = SettingsValues.theme.get()
                    dialog.dismiss()
                }
                setTitle("Theme")
                setNegativeButton("Cancel") {_, _ -> }
            }
            builder.create()
        }
        selectThemeDialog!!.show()
    }
}