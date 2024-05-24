package edu.tacoma.uw.huskyhaze

import android.content.Intent
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

        Glide.with(this)
            .asGif()
            .load(R.drawable.haze_gif_center_light)
            .into(gifImageView)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.hide()

        val gifAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_out)
        gifImageView.startAnimation(gifAnimation)
        // Start the animation
        val imageView = findViewById<ImageView>(R.id.iv_image)
        val animation = AnimationUtils.loadAnimation(this, R.anim.scale_animation)
        imageView.startAnimation(animation)

        android.os.Handler().postDelayed({
            val intent = Intent(this@SplashScreen, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}
