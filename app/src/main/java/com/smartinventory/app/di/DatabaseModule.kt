package com.smartinventory.app.di

import android.content.Context
import androidx.room.Room
import com.smartinventory.app.data.local.AppDatabase
import com.smartinventory.app.data.local.dao.ActivityLogDao
import com.smartinventory.app.data.local.dao.ItemDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @Author: ifechukwu.udorji
 * @Date: 4/9/2025
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "inventory_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideItemDao(database: AppDatabase): ItemDao = database.itemDao()

    @Provides
    fun provideActivityLogDao(database: AppDatabase): ActivityLogDao = database.activityLogDao()
}