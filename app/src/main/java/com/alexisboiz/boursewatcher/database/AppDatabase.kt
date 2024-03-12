package com.alexisboiz.boursewatcher.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.alexisboiz.boursewatcher.database.entities.StocksInfoEntity
import com.alexisboiz.boursewatcher.database.entities.UserEntity
import com.alexisboiz.boursewatcher.domain.database.StocksInfoDao
import com.alexisboiz.boursewatcher.domain.database.UserDao


@Database(entities = [UserEntity::class, StocksInfoEntity::class], version = 2)

abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun tradedAssetDao(): StocksInfoDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "CREATE TABLE `stocks_info` (`symbol` TEXT NOT NULL, `name` TEXT, `logoUrl` TEXT, PRIMARY KEY(`symbol`))"
                )
            }
        }


        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, "user.db"
                    ).allowMainThreadQueries()
                        .addMigrations(MIGRATION_1_2)
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }

    }
}