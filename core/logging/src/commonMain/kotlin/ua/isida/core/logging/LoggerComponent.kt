package ua.isida.core.logging

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.IntoSet
import dev.zacsweers.metro.Provides
import ua.isida.appinitializers.AppInitializer

expect interface LoggerPlatformComponent

@ContributesTo(AppScope::class)
interface LoggerComponent : LoggerPlatformComponent {

    @Provides @IntoSet
    fun provideCrashReportingInitializer(impl: CrashReportingInitializer): AppInitializer = impl

    @Provides @IntoSet
    fun provideKermitInitializer(impl: KermitInitializer): AppInitializer = impl

    @Provides
    fun provideFileLogManager(impl: FileLogManager): FileLogManager = impl
}