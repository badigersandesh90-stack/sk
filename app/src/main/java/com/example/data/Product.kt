package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val category: String, // "Laptops & Desktops", "Refurbished", "Accessories", "Monitors & Printers", "Hardware Upgrades"
    val condition: String, // "Brand New", "Refurbished - Grade A", "Certified Pre-Owned"
    val price: Double,
    val originalPrice: Double,
    val specifications: String, // Multi-line specs
    val stockStatus: String, // "In Stock", "Limited Stock", "Pre-order"
    val imageUrl: String = "",
    val warranty: String = "1 Year Warranty",
    val isFeatured: Boolean = false
)
