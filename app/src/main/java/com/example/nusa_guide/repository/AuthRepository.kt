package com.example.nusa_guide.repository

import android.util.Log
import com.example.nusa_guide.api.ApiService
import com.example.nusa_guide.api.EmailRequest
import com.example.nusa_guide.api.response.AuthResult
import com.example.nusa_guide.api.response.UserResult
import com.example.nusa_guide.data.DataStoreManager
import com.example.nusa_guide.model.LoginModel
import com.example.nusa_guide.model.RegisterModel

class AuthRepository(
    private val apiService: ApiService,
    private val dataStoreManager: DataStoreManager
) {

    suspend fun login(loginModel: LoginModel): AuthResult {
        return try {
            val response = apiService.login(loginModel)
            if (response.status) {
                val token = response.token.removePrefix("Bearer ")
                dataStoreManager.saveBearerToken(token)
                AuthResult.Success(response.message)
            } else {
                AuthResult.Error(response.message)
            }
        } catch (e: Exception) {
            Log.e("AuthRepository", "Login failed", e)
            AuthResult.Error("Login failed. Please try again.")
        }
    }

    suspend fun register(registerModel: RegisterModel): AuthResult {
        return try {
            val response = apiService.register(registerModel)
            if (response.status) {
                AuthResult.Success(response.message)
            } else {
                AuthResult.Error(response.message)
            }
        } catch (e: Exception) {
            AuthResult.Error("Register failed. Please try again.")
        }
    }

    suspend fun sendOtp(email: String): AuthResult {
        return try {
            Log.d("AuthRepository", "Sending OTP to email: $email")
            val response = apiService.sendOtp(EmailRequest(email))
            if (response.status) {
                AuthResult.Success(response.message)
            } else {
                AuthResult.Error(response.message)
            }
        } catch (e: Exception) {
            Log.e("AuthRepository", "Send OTP failed", e)
            AuthResult.Error("Send OTP failed. Please try again.")
        }
    }

    suspend fun getBearerToken(): String? {
        return dataStoreManager.getBearerToken()
    }

    suspend fun getUser(): UserResult {
        return try {
            val token = "Bearer " + dataStoreManager.getBearerToken()
            val response = apiService.getUser(token)
            if (response.message == "Data user Berhasil Diambil!") {
                UserResult.Success(response.data[0])
            } else {
                UserResult.Error(response.message)
            }
        } catch (e: Exception) {
            Log.e("AuthRepository", "Get user failed", e)
            UserResult.Error("Get user failed. Please try again.")
        }
    }

    suspend fun logout(): AuthResult {
        return try {
            val token = "Bearer " + dataStoreManager.getBearerToken()
            val response = apiService.logout(token)
            if (response.success) {
                dataStoreManager.clearBearerToken()
                AuthResult.Success(response.message)
            } else {
                AuthResult.Error(response.message)
            }
        } catch (e: Exception) {
            Log.e("AuthRepository", "Logout failed", e)
            AuthResult.Error("Logout failed. Please try again.")
        }
    }
}