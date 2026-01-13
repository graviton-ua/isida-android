package com.whoppah.preferences

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.SharedPreferencesSettings
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Named
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn

actual interface PreferencesPlatformComponent {

    @Provides @SingleIn(AppScope::class)
    fun provideSharedPreferences(
        @Named("APPLICATION_CONTEXT") context: Context,
    ): AppSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    @Provides @SingleIn(AppScope::class)
    fun provideSettings(delegate: AppSharedPreferences): ObservableSettings = SharedPreferencesSettings(delegate)
}

typealias AppSharedPreferences = SharedPreferences
