/**
 * Team 3 - TCSS 450 - Spring 2024
 */
package edu.tacoma.uw.huskyhaze.models

// Data model for login request
data class LoginRequest(
    val email: String,
    val password: String // Note: Sending plain-text password to server
)

// Data model for register request
data class RegisterRequest(
    val email: String,
    val name: String,
    val password: String
)

data class RegisterResponse(
    val result: String
)

// Data model for user info
data class UserInfo(
    val result: String,
    val userId: Int,
    val email: String,
    val name: String,
)