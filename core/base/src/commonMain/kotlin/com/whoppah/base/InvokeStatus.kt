package com.whoppah.base

sealed class InvokeStatus {
    data object Started : InvokeStatus()
    data object Success : InvokeStatus()
    data class Error(val throwable: Throwable) : InvokeStatus()
}