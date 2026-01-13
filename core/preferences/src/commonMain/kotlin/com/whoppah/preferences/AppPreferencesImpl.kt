package com.whoppah.preferences

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.coroutines.getBooleanFlow
import com.russhwolf.settings.coroutines.getLongOrNullFlow
import com.russhwolf.settings.coroutines.getStringOrNullFlow
import com.whoppah.util.AppCoroutineDispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlin.time.Clock
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@OptIn(ExperimentalSettingsApi::class)
internal class AppPreferencesImpl(
    private val settings: ObservableSettings,
    dispatchers: AppCoroutineDispatchers,
) : AppPreferences {
//    private val settings: ObservableSettings by settings
//    private val flowSettings by lazy { settings.value.toFlowSettings(dispatchers.io) }


    companion object {
        private const val KEY_SHOW_ON_BOARDING = "show_on_boarding"
        private const val KEY_SHOW_CREATE_AD_ON_BOARDING = "show_create_ad_on_boarding"
        private const val CAN_ASK_NOTIFICATIONS_PERMISSION = "can_ask_notifications_permission"
        private const val LAST_RATE_APP_TIME_STAMP = "last_rate_app_time"
        private const val FIRST_APP_LAUNCH_TIME_STAMP = "first_app_launch_time"
        private const val ADVERTISER_ID = "advertiser_id"
        private const val SEARCH_BY_IMAGE_TOOLTIP_SHOWN_TIMES = "tooltip_search_by_image_shown_times"
    }


    override var showOnBoarding: Boolean
        get() = settings.getBoolean(KEY_SHOW_ON_BOARDING, true)
        set(value) = settings.putBoolean(KEY_SHOW_ON_BOARDING, value)

    override var showCreateAdOnBoarding: Boolean
        get() = settings.getBoolean(KEY_SHOW_CREATE_AD_ON_BOARDING, true)
        set(value) = settings.putBoolean(KEY_SHOW_CREATE_AD_ON_BOARDING, value)


    override var canAskNotificationsPermission: Boolean
        get() = settings.getBoolean(CAN_ASK_NOTIFICATIONS_PERMISSION, true)
        set(value) = settings.putBoolean(CAN_ASK_NOTIFICATIONS_PERMISSION, value)

    override fun observeCanAskNotificationsPermission(): Flow<Boolean> = settings.getBooleanFlow(CAN_ASK_NOTIFICATIONS_PERMISSION, true)


    override var firstAppLaunchTimeStamp: Long?
        get() = settings.getLongOrNull(FIRST_APP_LAUNCH_TIME_STAMP)
        set(value) {
            val current = settings.getLongOrNull(FIRST_APP_LAUNCH_TIME_STAMP)
            if (current == null) settings.putLong(FIRST_APP_LAUNCH_TIME_STAMP, value ?: Clock.System.now().toEpochMilliseconds())
        }

    override var lastRateAppTimestamp: Long?
        get() = settings.getLongOrNull(LAST_RATE_APP_TIME_STAMP)
        set(value) = settings.putLong(LAST_RATE_APP_TIME_STAMP, value ?: Clock.System.now().toEpochMilliseconds())

    override fun canAskRateApp(): Boolean {
        return showRateApp(firstAppLaunchTimeStamp ?: return false, lastRateAppTimestamp)
    }

    override fun observeCanAskRateApp(): Flow<Boolean> = combine(
        settings.getLongOrNullFlow(FIRST_APP_LAUNCH_TIME_STAMP),
        settings.getLongOrNullFlow(LAST_RATE_APP_TIME_STAMP),
    ) { firstLaunch, lastRate -> showRateApp(firstLaunch ?: return@combine false, lastRate) }

    private fun showRateApp(firstAppLaunch: Long, lastRateAppTime: Long?): Boolean {
        return when (lastRateAppTime) {
            // If lastRateApp not set it means we never show RateApp dialog yet,
            // so we need to check when app were installed and show dialog according to it
            null -> {
                val dd = (Clock.System.now().toEpochMilliseconds() - firstAppLaunch).toDuration(DurationUnit.MILLISECONDS)
                dd.inWholeDays >= 7
            }

            // If lastRateApp was previously set it means we can show RateApp dialog depends on when it was last time done
            else -> {
                val dd = (Clock.System.now().toEpochMilliseconds() - lastRateAppTime).toDuration(DurationUnit.MILLISECONDS)
                dd.inWholeDays >= 7 * 4
            }
        }
    }


    override var advertiserId: String?
        get() = settings.getStringOrNull(ADVERTISER_ID)
        set(value) = value?.let { settings.putString(ADVERTISER_ID, it) } ?: settings.remove(ADVERTISER_ID)

    override fun observeAdvertiserId(): Flow<String?> = settings.getStringOrNullFlow(ADVERTISER_ID).onStart { emit(advertiserId) }


    override var searchByImageTooltipShownTimes: Int
        get() = settings.getInt(SEARCH_BY_IMAGE_TOOLTIP_SHOWN_TIMES, 0)
        set(value) = settings.putInt(SEARCH_BY_IMAGE_TOOLTIP_SHOWN_TIMES, value)
}