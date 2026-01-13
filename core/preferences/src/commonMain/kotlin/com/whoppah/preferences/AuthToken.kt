package com.whoppah.preferences

sealed class AuthToken(
    val prefix: String,
    open val token: String,
) {
    data class TokenV3(override val token: String) : AuthToken(prefix = "Bearer", token = token)

    fun header(): String = "$prefix $token"
}