/**
 * Team 3 - TCSS 450 - Spring 2024
 */
package edu.tacoma.uw.huskyhaze

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

/**
 * Activity class for the settings.
 */
class SettingsActivity : AppCompatActivity() {

    private lateinit var switcher: Switch
    private var nightMode = false
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    /**
     * Initializes settings page which include the dark mode switch and about us button.
     * Displays the correct theme based on if the dark mode switch is active or not.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)

        val userNameTextView = findViewById<TextView>(R.id.usernameTextView)
        val userEmailTextView = findViewById<TextView>(R.id.userEmailTextView)

        // Get SharedPreferences for user data
        val userSharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val userName = userSharedPreferences.getString("user_name", "Guest") ?: "Guest"
        val userEmail = userSharedPreferences.getString("user_email", "")

        userNameTextView.text = userName
        userEmailTextView.text = userEmail

        supportActionBar?.hide()
        switcher = findViewById(R.id.dark_light_switch)

        // Get SharedPreferences for night mode (use a different variable name)
        sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE)
        nightMode = sharedPreferences.getBoolean("night", false)

        if (nightMode) {
            switcher.isChecked = true
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        switcher.setOnClickListener {
            if (nightMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                editor = sharedPreferences.edit()
                editor.putBoolean("night", false)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                editor = sharedPreferences.edit()
                editor.putBoolean("night", true)
            }
            editor.apply()
        }
        val aboutUsButton = findViewById<Button>(R.id.aboutUsBtn)

        aboutUsButton.setOnClickListener {

            val intent = Intent(this, AboutUsActivity::class.java)
            startActivity(intent)
        }
    }

}