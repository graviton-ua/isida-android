package com.whoppah.extensions

fun String?.toNullIfEmpty(): String? = if (isNullOrEmpty()) null else this
fun String?.toNullIfBlank(): String? = if (isNullOrBlank()) null else this