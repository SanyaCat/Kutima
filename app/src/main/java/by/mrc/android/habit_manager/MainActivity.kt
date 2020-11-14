package by.mrc.android.habit_manager

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import by.mrc.android.habit_manager.databinding.ActivityMainBinding
import by.mrc.android.habit_manager.settings.SettingsThemeEnum
import by.mrc.android.habit_manager.settings.SettingsValues
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPref: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var gsonBuilder: GsonBuilder
    private lateinit var gson: Gson

    companion object {
        lateinit var context: Context
    }

    @SuppressLint("NewApi", "WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        context = this
        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )

        //binding = ActivityMainBinding.inflate(layoutInflater)

        // Toolbar (upper line)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Navigation
        val navController = this.findNavController(R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(toolbar, navController, binding.drawerLayout)
        binding.navView.setupWithNavController(navController)

        try {
            sharedPref = getSharedPreferences("by.mrc.android.habit_manager", Context.MODE_APPEND)
            gsonBuilder = GsonBuilder()
            gson = gsonBuilder.create()
            val value = gson.fromJson(sharedPref.getString("SETTINGS", ""), SettingsValues::class.java)
            SettingsValues.theme = value.theme
        } catch (e: Exception) {
            SettingsValues.theme = SettingsThemeEnum.GREEN
        }

        // Themes
        when (SettingsValues.theme) {
            SettingsThemeEnum.GREEN -> {
                setTheme(R.style.Theme_Green)
                toolbar.background = getDrawable(R.color.greenPrimary)
                binding.navView.getHeaderView(0).background = getDrawable(R.drawable.side_nav_bar_green)
            }
            SettingsThemeEnum.ORANGE -> {
                setTheme(R.style.Theme_Orange)
                toolbar.background = getDrawable(R.color.orangePrimary)
                binding.navView.getHeaderView(0).background = getDrawable(R.drawable.side_nav_bar_orange)
            }
            SettingsThemeEnum.PURPLE -> {
                setTheme(R.style.Theme_Purple)
                toolbar.background = getDrawable(R.color.purplePrimary)
                binding.navView.getHeaderView(0).background = getDrawable(R.drawable.side_nav_bar_purple)
            }
        }
    }

    override fun onPause() {
        sharedPref = getSharedPreferences("by.mrc.android.habit_manager", Context.MODE_PRIVATE)
        editor = sharedPref.edit()
        gsonBuilder = GsonBuilder()
        gson = gsonBuilder.create()
        val value = gson.toJson(SettingsValues)
        editor.putString("SETTINGS", value)
        editor.apply()

        super.onPause()
    }

    // Creating menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present
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
