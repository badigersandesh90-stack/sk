package com.example.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface EnquiryDao {
    @Query("SELECT * FROM enquiries ORDER BY timestamp DESC")
    fun getAllEnquiries(): Flow<List<Enquiry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEnquiry(enquiry: Enquiry): Long

    @Update
    suspend fun updateEnquiry(enquiry: Enquiry)

    @Delete
    suspend fun deleteEnquiry(enquiry: Enquiry)
}
