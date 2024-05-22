package edu.tacoma.uw.huskyhaze.models

// Data model for login request
data class LoginRequest(
    val email: String,
    val password: String // Note: Sending plain-text password to server
)

// Data model for login response
data class LoginResponse(
    val result: String,
    val userId: Int
)

// Data model for user info
data class UserInfo(
    val email: String,
    val name: String,
)