package com.smartinventory.app.di

import android.content.Context
import com.smartinventory.app.data.repository.ItemRepository
import com.smartinventory.app.data.repository.ItemRepositoryImpl
import com.smartinventory.app.utils.NetworkMonitor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

/**
 * @Author: ifechukwu.udorji
 * @Date: 4/9/2025
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNetworkMonitor(@ApplicationContext context: Context): NetworkMonitor {
        return NetworkMonitor(context)
    }

    @Provides
    @Singleton
    fun provideItemRepository(impl: ItemRepositoryImpl): ItemRepository = impl

    @Provides
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO
}