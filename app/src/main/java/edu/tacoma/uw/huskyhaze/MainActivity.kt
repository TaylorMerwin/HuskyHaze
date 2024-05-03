package edu.tacoma.uw.huskyhaze

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val weatherButton = findViewById<Button>(R.id.weatherButton)
        val newsButton = findViewById<Button>(R.id.newsButton)
        val settingsButton = findViewById<ImageButton>(R.id.settingsButton)

        weatherButton.setOnClickListener {
            val intent = Intent(this, WeatherActivity::class.java)
            startActivity(intent)
        }

        newsButton.setOnClickListener {
            val intent = Intent(this, NewsActivity::class.java)
            startActivity(intent)
        }

        settingsButton.setOnClickListener {
            val translateAnimator = ObjectAnimator.ofFloat(
                settingsButton,
                "translationX",
                0f, -(getScreenWidth().toFloat()-settingsButton.width.toFloat()-50)
            )
            translateAnimator.duration = 1000
            val rotateAnimator = ObjectAnimator.ofFloat(settingsButton, "rotation", 0f, -540f)
            rotateAnimator.duration = 1000
            val animatorSet = AnimatorSet()
            animatorSet.playTogether(translateAnimator, rotateAnimator)
            animatorSet.start()
            animatorSet.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {
                    TODO("Not yet implemented")
                }
                override fun onAnimationEnd(animation: Animator) {
                    val intent = Intent(this@MainActivity, SettingsActivity::class.java)
                    startActivity(intent)
                }
                override fun onAnimationCancel(animation: Animator) {
                    TODO("Not yet implemented")
                }
                override fun onAnimationRepeat(animation: Animator) {
                    TODO("Not yet implemented")
                }
            })
        }
    }
    private fun getScreenWidth(): Int {
        return resources.displayMetrics.widthPixels
    }
}