package by.mrc.android.habit_manager

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
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

    // Creating menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        // TODO("Click share")
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    // On clicking menu item
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // On item Share click
            R.id.item_share -> {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.setType("text/plain").putExtra(
                    Intent.EXTRA_TEXT,
                    Uri.parse(this.getString(R.string.share_intent_content))
                )
                startActivity(shareIntent)
            }
        }
        return true
    }
}
