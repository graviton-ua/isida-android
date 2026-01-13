package com.whoppah.preferences

import kotlinx.coroutines.flow.Flow

interface UserPreferences {
    /**
     *  Id of authorized user
     */
    var userId: String?
    fun observeUserId(): Flow<String?>
    fun isUserExpired(): Boolean

    /**
     *  Timestamp when expired products were checked last time in millis
     */
    var lastExpiredCheckTimestamp: Long?
    fun canCheckExpiredProducts(): Boolean

    /**
     *  AuthToken of our User for http requests
     */
    var authToken: AuthToken.TokenV3?
    fun observeAuthorized(): Flow<Boolean>
    fun isAuthorized(): Boolean

    // Used on ProductDetails screen
    var lastCountrySelected: String?
    fun observeLastCountrySelected(): Flow<String?>

    // Used on ProductDetails screen
    var lastAddressIdSelected: String?
    fun observeLastAddressIdSelected(): Flow<String?>

    fun observeRecentSearches(): Flow<List<String>>
    fun addRecentSearchQuery(query: String)

    fun clear()
}