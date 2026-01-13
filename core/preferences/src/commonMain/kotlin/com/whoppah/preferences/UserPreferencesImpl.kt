package com.whoppah.preferences

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.coroutines.getStringOrNullFlow
import com.whoppah.extensions.toNullIfBlank
import com.whoppah.util.AppCoroutineDispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlin.io.encoding.Base64
import kotlin.time.Clock
import kotlin.time.Duration.Companion.minutes

@OptIn(ExperimentalSettingsApi::class)
internal class UserPreferencesImpl(
    private val settings: ObservableSettings,
    private val dispatchers: AppCoroutineDispatchers,
) : UserPreferences {
//    private val settings: ObservableSettings by settings
//    private val flowSettings by lazy { settings.value.toFlowSettings(dispatchers.io) }

    companion object {
        private const val AUTH_TOKEN_V3 = "auth_token_v3"
        private const val USER_ID_V2 = "user_id_v2"
        private const val LAST_UPDATE_USER_TIME_STAMP = "last_update_time"
        private const val LAST_EXPIRED_CHECK_TIME_STAMP = "last_expired_check_time"
        private const val PDP_LAST_COUNTRY_SELECTED = "pdp_last_country_selected"
        private const val PDP_LAST_ADDRESS_ID_SELECTED = "pdp_last_address_id_selected"
        private const val RECENT_SEARCHES = "recent_searches"

        // Properties to be deleted
        @Deprecated("Deprecated since 2.15.3")
        private const val USER = "user"

        @Deprecated("Deprecated since 2.15.10")
        private const val USER_ID = "user_id"

        @Deprecated("Deprecated since 2.15.10")
        private const val AUTH_TOKEN_V2 = "auth_token_v2"

        @Deprecated("Deprecated since 2.20.3")
        private const val LAST_COUNTRY_SELECTED = "last_country_selected"
    }


    private var lastUpdateUserTimestamp: Long?
        get() = settings.getLongOrNull(LAST_UPDATE_USER_TIME_STAMP)
        set(value) = value?.let { settings.putLong(LAST_UPDATE_USER_TIME_STAMP, it) } ?: settings.remove(LAST_UPDATE_USER_TIME_STAMP)

    override var userId: String?
        get() = settings.getStringOrNull(USER_ID_V2)
        set(value) {
            value?.let { settings.putString(USER_ID_V2, it) } ?: settings.remove(USER_ID_V2)
            lastUpdateUserTimestamp = Clock.System.now().toEpochMilliseconds()
        }

    override fun observeUserId(): Flow<String?> = settings.getStringOrNullFlow(USER_ID_V2).onStart { emit(userId) }

    override fun isUserExpired(): Boolean {
        val ago = (Clock.System.now() - 30.minutes).toEpochMilliseconds()
        return if (userId == null) true else (lastUpdateUserTimestamp ?: 0) < ago
    }


    override var lastExpiredCheckTimestamp: Long?
        get() = settings.getLongOrNull(LAST_EXPIRED_CHECK_TIME_STAMP)
        set(value) = value?.let { settings.putLong(LAST_EXPIRED_CHECK_TIME_STAMP, it) } ?: settings.remove(LAST_EXPIRED_CHECK_TIME_STAMP)

    override fun canCheckExpiredProducts(): Boolean {
        val lastTime = lastExpiredCheckTimestamp ?: return true
        return (Clock.System.now().toEpochMilliseconds() - lastTime) > 5 * 60 * 60 * 1000L    // 5 Hours
    }


    override var authToken: AuthToken.TokenV3?
        get() = settings.getStringOrNull(AUTH_TOKEN_V3)?.let { AuthToken.TokenV3(it) }
        set(token) = token?.let { settings.putString(AUTH_TOKEN_V3, it.token) } ?: settings.remove(AUTH_TOKEN_V3)

    override fun observeAuthorized(): Flow<Boolean> =
        settings.getStringOrNullFlow(AUTH_TOKEN_V3).map { !it.isNullOrBlank() }.onStart { emit(isAuthorized()) }

    override fun isAuthorized(): Boolean = authToken != null


    override var lastCountrySelected: String?
        get() = settings.getStringOrNull(PDP_LAST_COUNTRY_SELECTED)
        set(value) = value?.let { settings.putString(PDP_LAST_COUNTRY_SELECTED, it) } ?: settings.remove(PDP_LAST_COUNTRY_SELECTED)

    override fun observeLastCountrySelected(): Flow<String?> = settings.getStringOrNullFlow(PDP_LAST_COUNTRY_SELECTED)

    override var lastAddressIdSelected: String?
        get() = settings.getStringOrNull(PDP_LAST_ADDRESS_ID_SELECTED)
        set(value) = value?.let { settings.putString(PDP_LAST_ADDRESS_ID_SELECTED, it) } ?: settings.remove(PDP_LAST_ADDRESS_ID_SELECTED)

    override fun observeLastAddressIdSelected(): Flow<String?> = settings.getStringOrNullFlow(PDP_LAST_ADDRESS_ID_SELECTED)


    override fun observeRecentSearches(): Flow<List<String>> =
        settings.getStringOrNullFlow(RECENT_SEARCHES).map { it?.decodeStringToListOfStrings()?.filterNot { it.isBlank() } ?: emptyList() }

    override fun addRecentSearchQuery(query: String) {
        if (query.isBlank()) return
        val current: List<String> = settings.getStringOrNull(RECENT_SEARCHES)?.decodeStringToListOfStrings() ?: emptyList()
        val new = (listOfNotNull(query.toNullIfBlank()) + current).take(20).filterNot { it.isBlank() }
        settings.putString(RECENT_SEARCHES, new.encodeListToString())
    }


    override fun clear() {
        authToken = null
        userId = null
        lastUpdateUserTimestamp = null
        lastExpiredCheckTimestamp = null

        settings.remove(USER)
        settings.remove(USER_ID)
        settings.remove(AUTH_TOKEN_V2)
        settings.remove(LAST_COUNTRY_SELECTED)
    }


    private fun List<String>.encodeListToString(): String {
        return this.joinToString(separator = " ") { value -> Base64.encode(value.encodeToByteArray()) }
    }

    private fun String.decodeStringToListOfStrings(): List<String> {
        if (this.isBlank()) return emptyList()
        return this.split(" ").map { encodedValue -> Base64.decode(encodedValue).decodeToString() }
    }
}