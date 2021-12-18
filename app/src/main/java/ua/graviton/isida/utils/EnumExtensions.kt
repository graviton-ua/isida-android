package ua.graviton.isida.utils

inline fun <reified T : Enum<T>> enumContains(name: String): Boolean {
    return enumValues<T>().any { it.name == name }
}

inline fun <reified T : Enum<T>> enumValueOf(name: String, defaultValue: T): T {
    return try {
        enumValues<T>().first { it.name == name }
    } catch (e: NoSuchElementException) {
        defaultValue
    }
}