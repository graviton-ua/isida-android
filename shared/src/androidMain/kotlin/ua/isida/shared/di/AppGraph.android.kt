package ua.isida.shared.di

import android.app.Application
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.ContentResolver
import android.content.Context
import dev.zacsweers.metro.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import ua.isida.shared.BluetoothStateService
import ua.isida.base.PlatformConfig
import ua.isida.domain.bluetooth.DeviceConnectionManager
import ua.isida.util.AppCoroutineDispatchers

@DependencyGraph(AppScope::class)
interface AndroidAppGraph : AppGraph {

    @DependencyGraph.Factory
    fun interface Factory {
        fun create(
            @Provides application: Application,
            @Provides config: PlatformConfig,
        ): AndroidAppGraph
    }

    val connectionManager: DeviceConnectionManager

    fun inject(target: BluetoothStateService)


    @Provides @Named("APPLICATION_CONTEXT")
    fun provideApplicationContext(application: Application): Context = application

    @Provides @SingleIn(AppScope::class)
    fun provideAppScope(): CoroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    @Provides
    fun provideContentResolver(@Named("APPLICATION_CONTEXT") context: Context): ContentResolver = context.contentResolver

    @Provides
    fun provideBluetoothAdapter(@Named("APPLICATION_CONTEXT") context: Context): BluetoothAdapter? {
        val manager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        return manager.adapter
    }

    @Provides @SingleIn(AppScope::class)
    fun provideCoroutineDispatchers(): AppCoroutineDispatchers = AppCoroutineDispatchers(
        io = Dispatchers.IO,
        computation = Dispatchers.Default,
        main = Dispatchers.Main
    )
}