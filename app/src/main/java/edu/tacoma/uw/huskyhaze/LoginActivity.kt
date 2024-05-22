package edu.tacoma.uw.huskyhaze

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import edu.tacoma.uw.huskyhaze.models.LoginRequest
import edu.tacoma.uw.huskyhaze.network.UserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val registerTextView = findViewById<TextView>(R.id.registerTextView)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            //TODO: Validate email and password

            val loginRequest = LoginRequest(email, password)
            // Send login request using Retrofit
            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    val userService = UserService.create()
                    val response = userService.login(loginRequest)
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            val loginResponse = response.body()
                            if (loginResponse?.result == "success") {

                                val userId = loginResponse.userId

                                // Get user info from user_info.php
                                val userInfoResponse = userService.getUserInfo(userId) // Assuming you create a getUserInfo method in UserService
                                if (userInfoResponse.isSuccessful) {
                                    val userInfo = userInfoResponse.body()
                                    val userEmail = userInfo?.email
                                    val userName = userInfo?.name

                                    // Store user ID, email, and name in SharedPreferences
                                    val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                                    sharedPreferences.edit().putInt("user_id", userId)
                                        .putString("user_email", userEmail)
                                        .putString("user_name", userName)
                                        .apply()
                                }
                                else {
                                    Toast.makeText(this@LoginActivity, "Error: ${userInfoResponse.message()}", Toast.LENGTH_SHORT).show()
                                }
                                Toast.makeText(this@LoginActivity, "Login successful", Toast.LENGTH_SHORT).show()
                                // Login successful, navigate to MainActivity
                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                intent.putExtra("user_id", userId) //Pass user ID to MainActivity
                                startActivity(intent)
                                finish() // Close LoginActivity
                            } else {
                                Toast.makeText(this@LoginActivity, loginResponse?.result ?: "Unknown error", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(this@LoginActivity, "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@LoginActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }
}