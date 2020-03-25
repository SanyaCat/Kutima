package by.mrc.android.habit_manager.share

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import by.mrc.android.habit_manager.databinding.FragmentShareBinding

// Here user can share info about application
class ShareFragment : Fragment() {

    // Initialize ViewModel
    private val shareViewModel: ShareViewModel by lazy {
        ViewModelProvider(this).get(ShareViewModel::class.java)
    }
    private lateinit var binding: FragmentShareBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Initialize Binding
        binding = FragmentShareBinding.inflate(inflater)
        // Sync ViewModel and UI
        shareViewModel.text.observe(this, Observer {
            binding.textShare.text = it
        })
        return binding.root
    }
}