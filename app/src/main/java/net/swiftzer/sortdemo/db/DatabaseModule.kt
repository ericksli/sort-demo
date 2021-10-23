package net.swiftzer.sortdemo.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.asExecutor
import net.swiftzer.sortdemo.IoDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "demo.db")
            .fallbackToDestructiveMigration()
            .setQueryExecutor(ioDispatcher.asExecutor())
            .setTransactionExecutor(ioDispatcher.asExecutor())
            .build()
}
