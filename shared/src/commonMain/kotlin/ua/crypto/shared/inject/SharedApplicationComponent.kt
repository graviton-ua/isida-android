package ua.crypto.shared.inject

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import ua.crypto.core.util.AppCoroutineDispatchers
import ua.crypto.data.db.SqlDatabaseComponent
import ua.crypto.data.repos.di.RepositoriesComponent
import ua.crypto.data.web.KtorComponent
import ua.crypto.shared.appinitializers.AppInitializers
import ua.crypto.shared.serviceinitializers.SyncServiceInitializers
import ua.crypto.shared.serviceinitializers.TraderServiceInitializers

expect interface SharedPlatformApplicationComponent

interface SharedApplicationComponent :
    SharedPlatformApplicationComponent,
    RepositoriesComponent,
    KtorComponent,
    SqlDatabaseComponent {

    val initializers: AppInitializers
    //val suspendedInitializers: AppSuspendedInitializers

    val traderServices: TraderServiceInitializers
    val syncServices: SyncServiceInitializers

    val dispatchers: AppCoroutineDispatchers

    val appScope: CoroutineScope
    //val deepLinker: DeepLinker

    @OptIn(ExperimentalCoroutinesApi::class)
    @SingleIn(AppScope::class)
    @Provides
    fun provideCoroutineDispatchers(): AppCoroutineDispatchers = AppCoroutineDispatchers(
        io = Dispatchers.IO,
        databaseWrite = Dispatchers.IO.limitedParallelism(1),
        databaseRead = Dispatchers.IO.limitedParallelism(4),
        computation = Dispatchers.Default,
        main = Dispatchers.Main,
    )

    @SingleIn(AppScope::class)
    @Provides
    fun provideApplicationCoroutineScope(
        dispatchers: AppCoroutineDispatchers,
    ): CoroutineScope = CoroutineScope(dispatchers.main + SupervisorJob())
}
