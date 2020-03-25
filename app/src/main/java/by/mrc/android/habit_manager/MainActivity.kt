package by.mrc.android.habit_manager

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.navigation.findNavController
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import androidx.databinding.DataBindingUtil
import androidx.navigation.ui.*
import by.mrc.android.habit_manager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )

        // Toolbar (upper line)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Action "add" button
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            // TODO("Add new Habit")
            Snackbar.make(view, "New habit is adding...", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        // Navigation
        val navController = this.findNavController(R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(toolbar, navController, binding.drawerLayout)
        binding.navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        // TODO("Click options menu")
        menuInflater.inflate(R.menu.main, menu)
        return true
    }
}
