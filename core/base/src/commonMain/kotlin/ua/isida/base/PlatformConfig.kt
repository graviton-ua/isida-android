package ua.isida.base

data class PlatformConfig(
    val isDebug: Boolean,
    val isQaBuild: Boolean,

    val crashReporting: Boolean,

    val platformInfo: PlatformInfo,
)