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

class SettingsActivity : AppCompatActivity() {

    private lateinit var switcher: Switch
    private var nightMode = false
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

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
            val fragment = AboutFragment()

            val intent = Intent(this, AboutUsActivity::class.java)
            startActivity(intent)

            // Begin the transaction
            supportFragmentManager.beginTransaction().apply {
                setCustomAnimations(
                    R.anim.slide_in_up,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.slide_out_down
                )
                replace(R.id.fragment_container, fragment)
                // Add the transaction to the back stack to be able to navigate back
                addToBackStack(null)
                // Commit the transaction
                commit()
            }
        }
    }

    private fun getScreenWidth(): Int {
        return resources.displayMetrics.widthPixels
    }
}