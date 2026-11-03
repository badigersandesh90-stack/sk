package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "store_config")
data class StoreConfig(
    @PrimaryKey
    val id: Int = 1,
    val storeName: String = "SK COMPUTERS",
    val tagline: String = "Sales, Refurbished Laptops, Chip-Level Repairs & Accessories",
    val address: String = "SK COMPUTERS, Main Road, Computer Hardware Market",
    val mapsUrl: String = "https://maps.app.goo.gl/Ygc7e9q9QFsorige9",
    val phone: String = "+91 98450 12345",
    val whatsappNumber: String = "+91 98450 12345",
    val email: String = "sandeshbadiger36@gmail.com",
    val businessHoursWeekday: String = "Monday – Saturday: 9:30 AM – 8:30 PM",
    val businessHoursWeekend: String = "Sunday: 10:00 AM – 2:00 PM",
    val noticeBanner: String = "Authorized Multi-Brand Laptop Repair & Refurbished Computer Specialist"
)
