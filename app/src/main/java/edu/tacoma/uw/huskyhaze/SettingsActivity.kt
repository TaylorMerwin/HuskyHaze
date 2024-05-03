package edu.tacoma.uw.huskyhaze

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Switch
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

        val backButton = findViewById<ImageButton>(R.id.backButton)

        backButton.setOnClickListener {
            val translateAnimator = ObjectAnimator.ofFloat(
                backButton,
                "translationX",
                0f, (getScreenWidth().toFloat()-backButton.width.toFloat()-25)
            )
            translateAnimator.duration = 1000
            val rotateAnimator = ObjectAnimator.ofFloat(backButton, "rotation", 0f, 540f)
            rotateAnimator.duration = 1000
            val animatorSet = AnimatorSet()
            animatorSet.playTogether(translateAnimator, rotateAnimator)
            animatorSet.start()
            animatorSet.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {
                    TODO("Not yet implemented")
                }
                override fun onAnimationEnd(animation: Animator) {
                    val intent = Intent(this@SettingsActivity, MainActivity::class.java)
                    startActivity(intent)
                }
                override fun onAnimationCancel(animation: Animator) {
                    TODO("Not yet implemented")
                }
                override fun onAnimationRepeat(animation: Animator) {
                    TODO("Not yet implemented")
                }
            })
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
        }

        supportActionBar?.hide()
        switcher = findViewById(R.id.dark_light_switch)

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
    }

    private fun getScreenWidth(): Int {
        return resources.displayMetrics.widthPixels
    }
}