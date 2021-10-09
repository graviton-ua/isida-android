package ua.graviton.isida.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ua.graviton.isida.data.db.AppDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomDatabaseModule {
    @Provides @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        val builder = Room.databaseBuilder(context, AppDatabase::class.java, "graviton_isida.db")
            .fallbackToDestructiveMigration()
        return builder.build()
    }
}


@Module
@InstallIn(SingletonComponent::class)
object DatabaseDaoModule {
    @Provides
    fun provideDeviceDataDao(db: AppDatabase) = db.deviceDataDao()
}