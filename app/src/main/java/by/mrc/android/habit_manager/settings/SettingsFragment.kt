package by.mrc.android.habit_manager.settings

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.mrc.android.habit_manager.databinding.FragmentSettingsBinding

// Here user set Settings
class SettingsFragment : Fragment() {

    companion object {
        lateinit var fragmentContext: SettingsFragment
    }

    // Initialize ViewModel
    private val viewModel: SettingsViewModel by lazy {
        ViewModelProvider(this).get(SettingsViewModel::class.java)
    }
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

    fun onSelectThemeClick() {
        val listOfEnum = listOf(
            SettingsThemeEnum.GREEN.get(),
            SettingsThemeEnum.DARK_GREEN.get(),
            SettingsThemeEnum.ORANGE.get(),
            SettingsThemeEnum.DARK_ORANGE.get(),
            SettingsThemeEnum.PURPLE.get(),
            SettingsThemeEnum.DARK_PURPLE.get()
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
                        1 -> SettingsThemeEnum.DARK_GREEN
                        2 -> SettingsThemeEnum.ORANGE
                        3 -> SettingsThemeEnum.DARK_ORANGE
                        4 -> SettingsThemeEnum.PURPLE
                        5 -> SettingsThemeEnum.DARK_PURPLE
                        else -> throw Error("ERROR")
                    }
                    Toast.makeText(context, "Please restart application to apply changes", Toast.LENGTH_LONG).show()
                    //Toast.makeText(context, SettingsValues.theme.get(), Toast.LENGTH_LONG).show()
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