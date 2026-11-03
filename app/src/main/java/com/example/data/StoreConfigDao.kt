package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface StoreConfigDao {
    @Query("SELECT * FROM store_config WHERE id = 1 LIMIT 1")
    fun getConfig(): Flow<StoreConfig?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(config: StoreConfig)
}
