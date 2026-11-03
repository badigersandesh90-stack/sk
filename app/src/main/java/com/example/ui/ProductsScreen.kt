package com.example.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.Product
import com.example.ui.theme.*

@Composable
fun ProductsScreen(
    products: List<Product>,
    selectedCategory: String,
    searchQuery: String,
    conditionFilter: String,
    onCategorySelected: (String) -> Unit,
    onSearchChanged: (String) -> Unit,
    onConditionChanged: (String) -> Unit,
    onEnquireWhatsApp: (Product) -> Unit,
    onViewDetails: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    val categories = listOf(
        "All",
        "Laptops & Desktops",
        "Refurbished",
        "Accessories",
        "Monitors & Printers",
        "Hardware Upgrades"
    )

    val conditions = listOf("All", "Brand New", "Refurbished")

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ObsidianBlack)
            .testTag("products_screen")
    ) {
        // Search & Filter Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(DeepNavySurface)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Search OutlinedTextField
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchChanged,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("product_search_input"),
                placeholder = { Text("Search laptops, specs, RAM, accessories...", fontSize = 13.sp, color = TextMuted) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = NeonCyan
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { onSearchChanged("") }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear search",
                                tint = TextMuted
                            )
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = NeonCyan,
                    unfocusedBorderColor = NavySurfaceBorder,
                    focusedContainerColor = NavySurfaceCard,
                    unfocusedContainerColor = NavySurfaceCard,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary
                )
            )

            // Category Chips Row
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(categories) { category ->
                    val isSelected = selectedCategory == category
                    FilterChip(
                        selected = isSelected,
                        onClick = { onCategorySelected(category) },
                        label = {
                            Text(
                                text = category,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = ElectricBlue,
                            selectedLabelColor = TextPrimary,
                            containerColor = NavySurfaceCard,
                            labelColor = TextSecondary
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            borderColor = if (isSelected) NeonCyan else NavySurfaceBorder,
                            enabled = true,
                            selected = isSelected
                        ),
                        shape = RoundedCornerShape(8.dp)
                    )
                }
            }

            // Condition Filter Segmented Buttons (All / Brand New / Refurbished)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                conditions.forEach { condition ->
                    val isSelected = conditionFilter == condition
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .height(34.dp),
                        shape = RoundedCornerShape(8.dp),
                        color = if (isSelected) {
                            if (condition == "Refurbished") AccentOrange.copy(alpha = 0.2f) else ElectricBlue.copy(alpha = 0.2f)
                        } else NavySurfaceCard,
                        border = BorderStroke(
                            1.dp,
                            if (isSelected) {
                                if (condition == "Refurbished") AccentOrange else NeonCyan
                            } else NavySurfaceBorder
                        ),
                        onClick = { onConditionChanged(condition) }
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = condition,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) {
                                    if (condition == "Refurbished") AccentOrange else NeonCyan
                                } else TextMuted
                            )
                        }
                    }
                }
            }
        }

        // Product Count & List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Showing ${products.size} products",
                        fontSize = 12.sp,
                        color = TextMuted,
                        fontWeight = FontWeight.Medium
                    )

                    if (conditionFilter == "Refurbished") {
                        Text(
                            text = "🛡️ 6-Month Warranty Included",
                            fontSize = 11.sp,
                            color = AccentOrange,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            if (products.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 60.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "No products found matching your filter.",
                                color = TextSecondary,
                                fontSize = 14.sp
                            )
                            Button(
                                onClick = {
                                    onCategorySelected("All")
                                    onSearchChanged("")
                                    onConditionChanged("All")
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue)
                            ) {
                                Text("Reset Filters")
                            }
                        }
                    }
                }
            } else {
                items(products, key = { it.id }) { product ->
                    ProductCard(
                        product = product,
                        onEnquireWhatsApp = onEnquireWhatsApp,
                        onViewDetails = onViewDetails
                    )
                }
            }
        }
    }
}
