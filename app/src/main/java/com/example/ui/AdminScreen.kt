package com.example.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.Enquiry
import com.example.data.Product
import com.example.data.StoreConfig
import com.example.ui.theme.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AdminScreen(
    products: List<Product>,
    enquiries: List<Enquiry>,
    storeConfig: StoreConfig,
    onAddProduct: (Product) -> Unit,
    onUpdateProduct: (Product) -> Unit,
    onDeleteProduct: (Product) -> Unit,
    onUpdateEnquiryStatus: (Enquiry, String) -> Unit,
    onDeleteEnquiry: (Enquiry) -> Unit,
    onUpdateStoreConfig: (StoreConfig) -> Unit,
    onExitAdmin: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var selectedTab by remember { mutableIntStateOf(0) } // 0: Inventory, 1: Enquiries, 2: Store Info

    var showAddProductDialog by remember { mutableStateOf(false) }
    var productToEdit by remember { mutableStateOf<Product?>(null) }
    var productToDelete by remember { mutableStateOf<Product?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ObsidianBlack)
            .testTag("admin_screen")
    ) {
        // Admin Top Bar
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = DeepNavySurface,
            border = BorderStroke(0.dp, Color.Transparent)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(NeonCyan.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.AdminPanelSettings,
                                contentDescription = null,
                                tint = NeonCyan,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Column {
                            Text(
                                text = "ADMIN MANAGEMENT PORTAL",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = NeonCyan,
                                letterSpacing = 1.sp
                            )
                            Text(
                                text = "Store Operations & Live Inventory",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                        }
                    }

                    IconButton(onClick = onExitAdmin) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Exit Admin",
                            tint = TextMuted
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Admin Tabs
                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = NavySurfaceCard,
                    contentColor = NeonCyan,
                    indicator = {}
                ) {
                    Tab(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        text = { Text("Products (${products.size})", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
                    )
                    Tab(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        text = { Text("Enquiries (${enquiries.size})", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
                    )
                    Tab(
                        selected = selectedTab == 2,
                        onClick = { selectedTab = 2 },
                        text = { Text("Store Info", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
                    )
                }
            }
        }

        // Tab Content
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            when (selectedTab) {
                0 -> {
                    // Products Management Tab
                    LazyColumn(
                        contentPadding = PaddingValues(top = 16.dp, bottom = 80.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        item {
                            Button(
                                onClick = { showAddProductDialog = true },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp)
                                    .testTag("admin_add_product_btn"),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = ElectricBlue,
                                    contentColor = TextPrimary
                                )
                            ) {
                                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Add New Product / Hardware", fontWeight = FontWeight.Bold)
                            }
                        }

                        items(products, key = { it.id }) { product ->
                            AdminProductRow(
                                product = product,
                                onEdit = { productToEdit = product },
                                onDelete = { productToDelete = product }
                            )
                        }
                    }
                }

                1 -> {
                    // Enquiries Management Tab
                    LazyColumn(
                        contentPadding = PaddingValues(top = 16.dp, bottom = 32.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        if (enquiries.isEmpty()) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 40.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "No customer enquiries or service bookings yet.",
                                        color = TextMuted,
                                        fontSize = 13.sp
                                    )
                                }
                            }
                        } else {
                            items(enquiries, key = { it.id }) { enquiry ->
                                AdminEnquiryRow(
                                    enquiry = enquiry,
                                    onUpdateStatus = { newStatus -> onUpdateEnquiryStatus(enquiry, newStatus) },
                                    onDelete = { onDeleteEnquiry(enquiry) }
                                )
                            }
                        }
                    }
                }

                2 -> {
                    // Store Info & Settings Tab (Editable as requested)
                    AdminStoreSettingsTab(
                        storeConfig = storeConfig,
                        onSave = { updated ->
                            onUpdateStoreConfig(updated)
                            Toast.makeText(context, "Store configuration updated!", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }

    // Add Product Dialog
    if (showAddProductDialog) {
        ProductEditDialog(
            product = null,
            onDismiss = { showAddProductDialog = false },
            onSave = { newProduct ->
                onAddProduct(newProduct)
                showAddProductDialog = false
                Toast.makeText(context, "Product added to catalog!", Toast.LENGTH_SHORT).show()
            }
        )
    }

    // Edit Product Dialog
    productToEdit?.let { product ->
        ProductEditDialog(
            product = product,
            onDismiss = { productToEdit = null },
            onSave = { updatedProduct ->
                onUpdateProduct(updatedProduct)
                productToEdit = null
                Toast.makeText(context, "Product updated!", Toast.LENGTH_SHORT).show()
            }
        )
    }

    // Delete Confirmation
    productToDelete?.let { product ->
        AlertDialog(
            onDismissRequest = { productToDelete = null },
            containerColor = DeepNavySurface,
            title = { Text("Delete Product?", color = TextPrimary) },
            text = { Text("Are you sure you want to remove '${product.title}' from the store?", color = TextSecondary) },
            confirmButton = {
                Button(
                    onClick = {
                        onDeleteProduct(product)
                        productToDelete = null
                        Toast.makeText(context, "Product deleted", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = AccentRed)
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { productToDelete = null }) {
                    Text("Cancel", color = TextMuted)
                }
            }
        )
    }
}

@Composable
fun AdminProductRow(
    product: Product,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = NavySurfaceCard),
        border = BorderStroke(1.dp, NavySurfaceBorder)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = product.category.uppercase(),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = NeonCyan
                )
                Text(
                    text = product.title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(2.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = formatCurrency(product.price),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = NeonCyan
                    )
                    Text(
                        text = "• ${product.stockStatus}",
                        fontSize = 11.sp,
                        color = if (product.stockStatus.contains("In Stock")) AccentGreen else AccentOrange
                    )
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit", tint = ElectricBlueLight)
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = AccentRed)
                }
            }
        }
    }
}

@Composable
fun AdminEnquiryRow(
    enquiry: Enquiry,
    onUpdateStatus: (String) -> Unit,
    onDelete: () -> Unit
) {
    val context = LocalContext.current
    val dateStr = SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault()).format(Date(enquiry.timestamp))

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = NavySurfaceCard),
        border = BorderStroke(1.dp, NavySurfaceBorder)
    ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = when (enquiry.status) {
                        "Completed" -> AccentGreen.copy(alpha = 0.2f)
                        "Contacted" -> ElectricBlue.copy(alpha = 0.2f)
                        else -> AccentOrange.copy(alpha = 0.2f)
                    }
                ) {
                    Text(
                        text = "${enquiry.enquiryType} • ${enquiry.status}",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = when (enquiry.status) {
                            "Completed" -> AccentGreen
                            "Contacted" -> ElectricBlueLight
                            else -> AccentOrange
                        }
                    )
                }

                Text(text = dateStr, fontSize = 10.sp, color = TextMuted)
            }

            Text(
                text = "From: ${enquiry.customerName} (${enquiry.phone})",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = TextPrimary
            )

            if (enquiry.relatedItem.isNotBlank()) {
                Text(
                    text = "Subject: ${enquiry.relatedItem}",
                    fontSize = 12.sp,
                    color = NeonCyan
                )
            }

            Surface(
                shape = RoundedCornerShape(8.dp),
                color = ObsidianBlack,
                border = BorderStroke(1.dp, NavySurfaceBorder)
            ) {
                Text(
                    text = enquiry.message,
                    modifier = Modifier.padding(10.dp),
                    fontSize = 12.sp,
                    color = TextSecondary
                )
            }

            // Contact Actions & Status Change
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilledTonalButton(
                        onClick = { callPhone(context, enquiry.phone) },
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Icon(Icons.Default.Phone, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Call", fontSize = 11.sp)
                    }

                    FilledTonalButton(
                        onClick = {
                            openWhatsApp(
                                context,
                                enquiry.phone,
                                "Hello ${enquiry.customerName}, regarding your inquiry at SK COMPUTERS..."
                            )
                        },
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                        shape = RoundedCornerShape(6.dp),
                        colors = ButtonDefaults.filledTonalButtonColors(containerColor = AccentGreen.copy(alpha = 0.2f), contentColor = AccentGreen)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.Chat, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("WhatsApp", fontSize = 11.sp)
                    }
                }

                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    IconButton(onClick = {
                        val nextStatus = when (enquiry.status) {
                            "New" -> "Contacted"
                            "Contacted" -> "Completed"
                            else -> "New"
                        }
                        onUpdateStatus(nextStatus)
                    }) {
                        Icon(Icons.Default.Check, contentDescription = "Toggle Status", tint = NeonCyan)
                    }
                    IconButton(onClick = onDelete) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = AccentRed)
                    }
                }
            }
        }
    }
}

@Composable
fun AdminStoreSettingsTab(
    storeConfig: StoreConfig,
    onSave: (StoreConfig) -> Unit
) {
    var storeName by remember { mutableStateOf(storeConfig.storeName) }
    var address by remember { mutableStateOf(storeConfig.address) }
    var mapsUrl by remember { mutableStateOf(storeConfig.mapsUrl) }
    var phone by remember { mutableStateOf(storeConfig.phone) }
    var whatsappNumber by remember { mutableStateOf(storeConfig.whatsappNumber) }
    var email by remember { mutableStateOf(storeConfig.email) }
    var businessHoursWeekday by remember { mutableStateOf(storeConfig.businessHoursWeekday) }
    var businessHoursWeekend by remember { mutableStateOf(storeConfig.businessHoursWeekend) }

    LazyColumn(
        contentPadding = PaddingValues(top = 16.dp, bottom = 40.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Edit Store Profile & Contact Details",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Text(
                text = "Updates shop address, Google Maps location, phone numbers, and timings across the entire app.",
                fontSize = 12.sp,
                color = TextMuted
            )
        }

        item {
            OutlinedTextField(
                value = storeName,
                onValueChange = { storeName = it },
                label = { Text("Shop Name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary)
            )
        }

        item {
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Shop Phone / Calling Hotline") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary)
            )
        }

        item {
            OutlinedTextField(
                value = whatsappNumber,
                onValueChange = { whatsappNumber = it },
                label = { Text("WhatsApp Number") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary)
            )
        }

        item {
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email Address") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary)
            )
        }

        item {
            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                label = { Text("Physical Shop Address") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 3,
                colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary)
            )
        }

        item {
            OutlinedTextField(
                value = mapsUrl,
                onValueChange = { mapsUrl = it },
                label = { Text("Google Maps Location URL") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary)
            )
        }

        item {
            OutlinedTextField(
                value = businessHoursWeekday,
                onValueChange = { businessHoursWeekday = it },
                label = { Text("Weekday Business Hours") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary)
            )
        }

        item {
            OutlinedTextField(
                value = businessHoursWeekend,
                onValueChange = { businessHoursWeekend = it },
                label = { Text("Weekend Business Hours") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary)
            )
        }

        item {
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = {
                    onSave(
                        storeConfig.copy(
                            storeName = storeName.trim(),
                            address = address.trim(),
                            mapsUrl = mapsUrl.trim(),
                            phone = phone.trim(),
                            whatsappNumber = whatsappNumber.trim(),
                            email = email.trim(),
                            businessHoursWeekday = businessHoursWeekday.trim(),
                            businessHoursWeekend = businessHoursWeekend.trim()
                        )
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("save_store_settings_btn"),
                colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue)
            ) {
                Icon(Icons.Default.Save, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Save Store Settings", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun ProductEditDialog(
    product: Product?,
    onDismiss: () -> Unit,
    onSave: (Product) -> Unit
) {
    var title by remember { mutableStateOf(product?.title ?: "") }
    var category by remember { mutableStateOf(product?.category ?: "Laptops & Desktops") }
    var condition by remember { mutableStateOf(product?.condition ?: "Brand New") }
    var priceText by remember { mutableStateOf(product?.price?.toInt()?.toString() ?: "") }
    var originalPriceText by remember { mutableStateOf(product?.originalPrice?.toInt()?.toString() ?: "") }
    var specifications by remember { mutableStateOf(product?.specifications ?: "") }
    var stockStatus by remember { mutableStateOf(product?.stockStatus ?: "In Stock") }
    var warranty by remember { mutableStateOf(product?.warranty ?: "1 Year Warranty") }
    var isFeatured by remember { mutableStateOf(product?.isFeatured ?: false) }

    val categories = listOf("Laptops & Desktops", "Refurbished", "Accessories", "Monitors & Printers", "Hardware Upgrades")
    val conditions = listOf("Brand New", "Refurbished - Grade A", "Certified Pre-Owned")
    val stockOptions = listOf("In Stock", "Limited Stock", "Pre-order")

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = DeepNavySurface,
        title = {
            Text(
                text = if (product == null) "Add New Product" else "Edit Product",
                color = TextPrimary,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Product Title *") },
                        modifier = Modifier.fillMaxWidth().testTag("admin_product_title"),
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary)
                    )
                }

                item {
                    Text("Category:", fontSize = 11.sp, color = TextMuted)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        categories.take(3).forEach { cat ->
                            FilterChip(
                                selected = category == cat,
                                onClick = { category = cat },
                                label = { Text(cat.take(12), fontSize = 10.sp) },
                                colors = FilterChipDefaults.filterChipColors(selectedContainerColor = ElectricBlue)
                            )
                        }
                    }
                }

                item {
                    Text("Condition:", fontSize = 11.sp, color = TextMuted)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        conditions.forEach { cond ->
                            FilterChip(
                                selected = condition == cond,
                                onClick = { condition = cond },
                                label = { Text(cond.take(14), fontSize = 10.sp) },
                                colors = FilterChipDefaults.filterChipColors(selectedContainerColor = ElectricBlue)
                            )
                        }
                    }
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = priceText,
                            onValueChange = { priceText = it },
                            label = { Text("Price (₹) *") },
                            modifier = Modifier.weight(1f).testTag("admin_product_price"),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary)
                        )
                        OutlinedTextField(
                            value = originalPriceText,
                            onValueChange = { originalPriceText = it },
                            label = { Text("MRP (₹)") },
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary)
                        )
                    }
                }

                item {
                    Text("Stock Availability:", fontSize = 11.sp, color = TextMuted)
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        stockOptions.forEach { opt ->
                            FilterChip(
                                selected = stockStatus == opt,
                                onClick = { stockStatus = opt },
                                label = { Text(opt, fontSize = 11.sp) },
                                colors = FilterChipDefaults.filterChipColors(selectedContainerColor = ElectricBlue)
                            )
                        }
                    }
                }

                item {
                    OutlinedTextField(
                        value = warranty,
                        onValueChange = { warranty = it },
                        label = { Text("Warranty (e.g. 1 Year Brand / 6 Months Shop)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary)
                    )
                }

                item {
                    OutlinedTextField(
                        value = specifications,
                        onValueChange = { specifications = it },
                        label = { Text("Technical Specifications (bullet points)") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3,
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary)
                    )
                }

                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Checkbox(
                            checked = isFeatured,
                            onCheckedChange = { isFeatured = it },
                            colors = CheckboxDefaults.colors(checkedColor = NeonCyan)
                        )
                        Text("Show in Home Featured Showcase", fontSize = 12.sp, color = TextSecondary)
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val price = priceText.toDoubleOrNull() ?: 0.0
                    val original = originalPriceText.toDoubleOrNull() ?: price
                    if (title.isNotBlank() && price > 0) {
                        val p = (product ?: Product(title = "", category = "", condition = "", price = 0.0, originalPrice = 0.0, specifications = "", stockStatus = "")).copy(
                            title = title.trim(),
                            category = category,
                            condition = condition,
                            price = price,
                            originalPrice = original,
                            specifications = specifications.trim(),
                            stockStatus = stockStatus,
                            warranty = warranty.trim(),
                            isFeatured = isFeatured
                        )
                        onSave(p)
                    }
                },
                modifier = Modifier.testTag("admin_save_product_btn"),
                colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue),
                enabled = title.isNotBlank() && priceText.toDoubleOrNull() != null
            ) {
                Text("Save Product")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = TextMuted)
            }
        }
    )
}
