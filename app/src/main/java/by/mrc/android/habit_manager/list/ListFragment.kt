package by.mrc.android.habit_manager.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import by.mrc.android.habit_manager.databinding.FragmentListBinding

// Here list of habits is showing
class ListFragment : Fragment() {

    // Initialize ViewModel
    private val listViewModel: ListViewModel by lazy {
        ViewModelProvider(this).get(ListViewModel::class.java)
    }
    private lateinit var binding: FragmentListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Initialize Binding
        binding = FragmentListBinding.inflate(inflater)
        // Sync ViewModel and UI
        listViewModel.text.observe(this, Observer {
            binding.textList.text = it
        })
        return binding.root
    }
}