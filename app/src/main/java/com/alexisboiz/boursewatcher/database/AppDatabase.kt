package com.alexisboiz.boursewatcher.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.alexisboiz.boursewatcher.database.entities.TradedAssetEntity
import com.alexisboiz.boursewatcher.database.entities.UserEntity
import com.alexisboiz.boursewatcher.domain.database.TradedAssetDao
import com.alexisboiz.boursewatcher.domain.database.UserDao


@Database(entities = [UserEntity::class, TradedAssetEntity::class], version = 2)

abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun tradedAssetDao(): TradedAssetDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "CREATE TABLE `tradedassets` (`symbol` TEXT NOT NULL, `purshasedPrice` REAL NOT NULL, `quantity` REAL NOT NULL, `purshasedDate` TEXT NOT NULL, PRIMARY KEY(`symbol`))"
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