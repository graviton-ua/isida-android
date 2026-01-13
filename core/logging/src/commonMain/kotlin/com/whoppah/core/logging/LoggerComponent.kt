package com.whoppah.core.logging

import com.whoppah.appinitializers.AppInitializer
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.IntoSet
import dev.zacsweers.metro.Provides

expect interface LoggerPlatformComponent

@ContributesTo(AppScope::class)
interface LoggerComponent : LoggerPlatformComponent {

    @Provides @IntoSet
    fun provideCrashReportingInitializer(impl: CrashReportingInitializer): AppInitializer = impl

    @Provides @IntoSet
    fun provideKermitInitializer(impl: KermitInitializer): AppInitializer = impl
}