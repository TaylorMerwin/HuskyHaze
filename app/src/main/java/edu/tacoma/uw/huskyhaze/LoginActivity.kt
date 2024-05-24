package edu.tacoma.uw.huskyhaze

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
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

                                val userName = loginResponse.name
                                val userEmail = loginResponse.email
                                val userId = loginResponse.userId

                                Log.i("LoginActivity", "User ID: $userId, User Name: $userName, User Email: $userEmail")

                                // Store user ID and name in SharedPreferences
                                val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                                sharedPreferences.edit().putInt("user_id", userId)
                                    .putString("user_email", userEmail)
                                    .putString("user_name", userName)
                                    .apply()

                                // Start MainActivity and finish LoginActivity
                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                               // intent.putExtra("user_id", userId)
                                startActivity(intent)
                                finish()
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

        registerTextView.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }
}