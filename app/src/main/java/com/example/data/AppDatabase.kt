package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [Product::class, Enquiry::class, StoreConfig::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun enquiryDao(): EnquiryDao
    abstract fun storeConfigDao(): StoreConfigDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "sk_computers_db"
                ).addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        INSTANCE?.let { database ->
                            scope.launch(Dispatchers.IO) {
                                database.productDao().insertAll(SampleData.getInitialProducts())
                                database.storeConfigDao().insertOrUpdate(SampleData.getDefaultStoreConfig())
                            }
                        }
                    }
                }).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
