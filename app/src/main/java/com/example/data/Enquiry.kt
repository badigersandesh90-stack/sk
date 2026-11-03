package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "enquiries")
data class Enquiry(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val customerName: String,
    val phone: String,
    val email: String = "",
    val enquiryType: String, // "Product Inquiry", "Service Booking", "Custom PC Build", "General Contact"
    val relatedItem: String = "", // Product name or Service name
    val message: String,
    val preferredDate: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val status: String = "New" // "New", "Contacted", "Completed"
)
