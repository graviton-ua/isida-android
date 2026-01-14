package ua.graviton.isida.shared.di

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import com.whoppah.base.PlatformConfig
import com.whoppah.util.AppCoroutineDispatchers
import dev.zacsweers.metro.*
import kotlinx.coroutines.Dispatchers

@DependencyGraph(AppScope::class)
interface AndroidAppGraph : AppGraph {

    @DependencyGraph.Factory
    fun interface Factory {
        fun create(
            @Provides application: Application,
            @Provides config: PlatformConfig,
        ): AndroidAppGraph
    }


    //fun inject(target: SpecialFirebaseMessagingService)


    @Provides @Named("APPLICATION_CONTEXT")
    fun provideApplicationContext(application: Application): Context = application

    @Provides
    fun provideContentResolver(@Named("APPLICATION_CONTEXT") context: Context): ContentResolver = context.contentResolver

    @Provides @SingleIn(AppScope::class)
    fun provideCoroutineDispatchers(): AppCoroutineDispatchers = AppCoroutineDispatchers(
        io = Dispatchers.IO,
        computation = Dispatchers.Default,
        main = Dispatchers.Main
    )

    // @Provides @SingleIn(AppScope::class)
    // fun provideCredentialManager(
    //     @Named("APPLICATION_CONTEXT") ctx: Context,
    // ): CredentialManager = CredentialManager.create(ctx)
}