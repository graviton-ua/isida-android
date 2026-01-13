package com.whoppah.preferences

import kotlinx.coroutines.flow.Flow

interface AppPreferences {
    // SHOW_ON_BOARDING default true
    var showOnBoarding: Boolean
    var showCreateAdOnBoarding: Boolean

    // CAN_ASK_NOTIFICATIONS_PERMISSION default true
    var canAskNotificationsPermission: Boolean
    fun observeCanAskNotificationsPermission(): Flow<Boolean>

    // FIRST_APP_LAUNCH_TIME_STAMP default null
    var firstAppLaunchTimeStamp: Long?

    // LAST_RATE_APP_TIME_STAMP default null
    var lastRateAppTimestamp: Long?
    fun canAskRateApp(): Boolean
    fun observeCanAskRateApp(): Flow<Boolean>


    var advertiserId: String?
    fun observeAdvertiserId(): Flow<String?>

    var searchByImageTooltipShownTimes: Int
}