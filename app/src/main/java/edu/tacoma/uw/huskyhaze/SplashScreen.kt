/**
 * Team 3 - TCSS 450 - Spring 2024
 */
package edu.tacoma.uw.huskyhaze

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen)

        val gifImageView = findViewById<ImageView>(R.id.gifImageView)
        val sharedPreferences: SharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE)
        val isDarkMode = sharedPreferences.getBoolean("night", false)

        if (isDarkMode) {
            findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.main).setBackgroundColor(getColor(R.color.md_theme_scrim))

            Glide.with(this)
                .asGif()
                .load(R.drawable.haze_gif_center_dark)
                .into(gifImageView)
        } else {
            Glide.with(this)
                .asGif()
                .load(R.drawable.haze_gif_center_light)
                .into(gifImageView)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.hide()

        // Start the animation
        val imageView = findViewById<ImageView>(R.id.iv_image)
        val animation = AnimationUtils.loadAnimation(this, R.anim.scale_animation)
        imageView.startAnimation(animation)

        android.os.Handler().postDelayed({
            val intent = Intent(this@SplashScreen, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}
