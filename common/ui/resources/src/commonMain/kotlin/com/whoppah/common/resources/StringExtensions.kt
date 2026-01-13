package com.whoppah.common.resources

/**
 * ^                 # start-of-string
 * (?=.*[0-9])       # a digit must occur at least once
 * (?=.*[a-z])       # a lower case letter must occur at least once
 * (?=.*[A-Z])       # an upper case letter must occur at least once
 * (?=.*[@#$%^&+=])  # a special character must occur at least once
 * (?=\S+$)          # no whitespace allowed in the entire string
 * .{8,}             # anything, at least eight places though
 * $                 # end-of-string
 */
private val generalPassPattern = """^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\S+$).{8,}$""".toRegex()

private val passPattern = """^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\S+$).{8,}$""".toRegex()
private val hasDigitPattern = """(.*[0-9].*)""".toRegex()
private val hasLowerCasePattern = """(.*[a-z].*)""".toRegex()
private val hasUpperCasePattern = """(.*[A-Z].*)""".toRegex()
private val emailPattern =
    """(?:[a-zA-Z0-9!#$%\&‘*+/=?\^_`{|}~-]+(?:\.[a-zA-Z0-9!#$%\&'*+/=?\^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])""".toRegex()

private val containsWhoppahPattern = """(?i).*whoppah.*""".toRegex()
private val containsEmailPattern = """^[^\s@]+@[^\s@]+\.[^\s@]+$""".toRegex()

//private val urlPattern = """^(https?://)?([\da-z\.-]+)\.([a-z\.]{2,6})([\/\w \.-]*)*\/?${'$'}""".toRegex()
//private val urlPattern = """^(https?://)?[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]""".toRegex()
private val urlPattern =
    """^(https?:\/\/)?([\w\Q${'$'}-_+!*'(),%\E]+\.)+(\w{2,63})(:\d{1,4})?([\w\Q/${'$'}-_+!*'(),%\E]+\.?[\w])*\/?${'$'}""".toRegex()

// We don't need username validation anymore
fun CharSequence?.isValidUserName(): Boolean = this?.let { !containsWhoppahPattern.containsMatchIn(it) && !containsEmailPattern.matches(it) } == true
fun CharSequence?.isValidEmail(): Boolean = this?.let { emailPattern.matches(this) } == true

fun CharSequence?.containWhoppah(): Boolean = this?.let { containsWhoppahPattern.containsMatchIn(it) } == true
fun CharSequence?.containEmail(): Boolean = this?.let { containsEmailPattern.matches(it) } == true

fun CharSequence?.isValidPass(): Boolean = this?.let { passPattern.matches(this) } == true
fun CharSequence?.passHas8Symbols(): Boolean = this?.let { this.length >= 8 } == true
fun CharSequence?.passHasDigitSymbol(): Boolean = this?.let { hasDigitPattern.matches(this) } == true
fun CharSequence?.passHasLowerCaseSymbol(): Boolean = this?.let { hasLowerCasePattern.matches(this) } == true
fun CharSequence?.passHasUpperCaseSymbol(): Boolean = this?.let { hasUpperCasePattern.matches(this) } == true
fun CharSequence?.passHasAnyCaseSymbol(): Boolean = this?.let { hasUpperCasePattern.matches(this) || hasLowerCasePattern.matches(this) } == true

fun CharSequence.isValidUrl(): Boolean = urlPattern.matches(this) == true

fun CharSequence?.toNullIfBlank(): CharSequence? = this?.let { if (isNullOrBlank()) null else this }

fun CharSequence?.maskPartially(prefixCount: Int = 0, suffixCount: Int = 0): CharSequence? = this?.let {
    when {
        it.length > (prefixCount + suffixCount) -> {
            val builder = StringBuilder(it)
            for (i in prefixCount until it.length - suffixCount) builder[i] = '*'
            builder.toString()
        }

        else -> it
    }
}

/**
 * Appends an asterisk with a preceding space to the end of the string.
 *
 * e.g. `hello`.appendAsterisk() returns "hello *"
 */
fun String.appendAsterisk() = "$this *"