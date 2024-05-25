/**
 * Team 3 - TCSS 450 - Spring 2024
 */
package edu.tacoma.uw.huskyhaze

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import edu.tacoma.uw.huskyhaze.models.RegisterRequest
import edu.tacoma.uw.huskyhaze.network.UserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * The RegisterActivity class is responsible for handling user registration.
 * Users can enter their email, username, and password to register for the app.
 * If registration is successful, the user is navigated to the LoginActivity.
 */
class RegisterActivity : AppCompatActivity() {

    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState The Bundle containing the activity's previously saved state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val nameEditText = findViewById<EditText>(R.id.nameEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val registerButton = findViewById<Button>(R.id.registerButton)
        val loginTextView = findViewById<TextView>(R.id.loginTextView)

        registerButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val name = nameEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (!isValidNameLength(name)) {
                Toast.makeText(this, "Username must be in between 1 and 10 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(!isValidPasswordLength(password)) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(!isValidEmailFormat(email)) {
                Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            val registerRequest = RegisterRequest(email, name, password)

            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    val userService = UserService.create()
                    val response = userService.register(registerRequest)

                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            val registerResponse = response.body()
                            if (registerResponse?.result == "success") {
                                // Registration successful, navigate to LoginActivity
                                // Toast message to inform user of successful registration
                                Toast.makeText(this@RegisterActivity, "Registration successful", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                                finish()
                            } else {
                                Toast.makeText(this@RegisterActivity, registerResponse?.result ?: "Unknown error", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(this@RegisterActivity, "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@RegisterActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        loginTextView.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }


    }

    /**
     * Checks if the username is between 1 and 10 characters.
     * @param userName The username to check.
     * @return True if the username is between 1 and 10 characters, false otherwise.
     */
    private fun isValidNameLength(userName: String): Boolean {
        return userName.length in 1..10
    }

    /**
     * Checks if the password is at least 6 characters.
     * @param password The password to check.
     * @return True if the password is at least 6 characters, false otherwise.
     */
    private fun isValidPasswordLength(password: String): Boolean {
        return password.length >= 6
    }

    /**
     * Checks if the email is in a valid format.
     * @param email The email to check.
     * @return True if the email is in a valid format, false otherwise.
     */
    private fun isValidEmailFormat(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
