package com.idi.activity.login

import androidx.annotation.Keep

@Keep
data class LoginResponse(
    val success: Boolean,
    val message: String,
    val token: String,
    val user_data: List<UserData>
)
@Keep
data class UserData(
    val active: Int,
    val avatar: String,
    val created_at: String,
    val email: String,
    val email_verified_at: String,
    val id: Int,
    val mobile: String,
    val name: String,
    val updated_at: String
)