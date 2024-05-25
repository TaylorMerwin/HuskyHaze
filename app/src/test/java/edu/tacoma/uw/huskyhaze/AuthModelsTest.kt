package edu.tacoma.uw.huskyhaze

import edu.tacoma.uw.huskyhaze.models.LoginRequest
import edu.tacoma.uw.huskyhaze.models.RegisterRequest
import edu.tacoma.uw.huskyhaze.models.RegisterResponse
import edu.tacoma.uw.huskyhaze.models.UserInfo
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test

class AuthModelsTest {
    @Test
    fun testLoginRequest() {
        val loginRequest = LoginRequest("test@uw.edu", "password")
        assertEquals("test@uw.edu", loginRequest.email)
        assertEquals("password", loginRequest.password)
    }

    @Test
    fun testLoginRequestWrongEmail() {
        val loginRequest = LoginRequest("test@uw.edu", "password")
        assertFalse(loginRequest.email == "test1@uw.edu")
    }

    @Test
    fun testLoginRequestWrongPassword() {
        val loginRequest = LoginRequest("test@uw.edu", "password")
        assertFalse(loginRequest.password == "Password")
    }

    @Test
    fun testRegisterRequest() {
        val registerRequest = RegisterRequest("test@uw.edu", "Test", "password")
        assertEquals("test@uw.edu", registerRequest.email)
        assertEquals("Test", registerRequest.name)
        assertEquals("password", registerRequest.password)
    }

    @Test
    fun testRegisterResponse() {
        val registerResponse = RegisterResponse("success")
        assertEquals("success", registerResponse.result)
    }

    @Test
    fun testRegisterResponseInvalid() {
        val registerResponse = RegisterResponse("success")
        assertFalse(registerResponse.result == "failed")
    }

    @Test
    fun testUserInfo() {
        val userInfo = UserInfo("success", 123, "test@uw.edu", "Test")
        assertEquals("success", userInfo.result)
        assertEquals(123, userInfo.userId)
        assertEquals("test@uw.edu", userInfo.email)
        assertEquals("Test", userInfo.name)
    }

    @Test
    fun testUserInfoInvalid() {
        val userInfo = UserInfo("failed", 123, "test@uw.edu", "Test")
        assertFalse(userInfo.result == "success")
        assertFalse(userInfo.userId == 1233)
        assertFalse(userInfo.email == "Test@uw.edu")
        assertFalse(userInfo.name == "test")
    }
}