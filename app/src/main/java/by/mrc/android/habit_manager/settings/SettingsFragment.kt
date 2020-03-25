package by.mrc.android.habit_manager.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import by.mrc.android.habit_manager.databinding.FragmentSettingsBinding

// Here user set Settings
class SettingsFragment : Fragment() {

    // Initialize ViewModel
    private val settingsViewModel: SettingsViewModel by lazy {
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
        // Sync ViewModel and UI
        settingsViewModel.text.observe(this, Observer {
            binding.textSettings.text = it
        })
        return binding.root
    }
}