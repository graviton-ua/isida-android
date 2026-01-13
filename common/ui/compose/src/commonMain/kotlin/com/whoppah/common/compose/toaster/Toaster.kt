package com.whoppah.common.compose.toaster

// The clean interface for ViewModels (or any other class) to use.
// This fulfills the "Toaster interface" requirement.
interface Toaster {

    enum class ToastType {
        DEFAULT, SUCCESS, ERROR
    }

    enum class Duration { SHORT, LONG }

    fun showToast(
        message: String,
        duration: Duration = Duration.SHORT,
        actionLabel: String? = null,
        onAction: (() -> Unit)? = null,
        type: ToastType = ToastType.DEFAULT,
    )

    fun showSuccess(
        message: String,
        duration: Duration = Duration.SHORT,
        actionLabel: String? = null,
        onAction: (() -> Unit)? = null,
    ) = showToast(message, duration, actionLabel, onAction, type = ToastType.SUCCESS)

    fun showError(
        message: String,
        duration: Duration = Duration.SHORT,
        actionLabel: String? = null,
        onAction: (() -> Unit)? = null,
    ) = showToast(message, duration, actionLabel, onAction, type = ToastType.ERROR)
}