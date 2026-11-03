package com.example.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.Product
import com.example.ui.theme.*
import java.net.URLEncoder
import java.text.NumberFormat
import java.util.Locale

// Utility to launch WhatsApp with pre-filled message
fun openWhatsApp(context: Context, phoneNumber: String, message: String) {
    try {
        val cleanNumber = phoneNumber.replace(Regex("[^0-9+]"), "").removePrefix("+")
        val encodedMessage = URLEncoder.encode(message, "UTF-8")
        val uri = Uri.parse("https://api.whatsapp.com/send?phone=$cleanNumber&text=$encodedMessage")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "Could not open WhatsApp: ${e.message}", Toast.LENGTH_SHORT).show()
    }
}

// Utility to open Phone Dialer
fun callPhone(context: Context, phoneNumber: String) {
    try {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${phoneNumber.trim()}"))
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "Could not dial phone: ${e.message}", Toast.LENGTH_SHORT).show()
    }
}

// Utility to open Google Maps URL
fun openMaps(context: Context, mapsUrl: String) {
    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(mapsUrl))
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "Could not open Maps: ${e.message}", Toast.LENGTH_SHORT).show()
    }
}

// Format INR currency
fun formatCurrency(amount: Double): String {
    val format = NumberFormat.getCurrencyInstance(Locale("en", "IN"))
    return format.format(amount).replace("₹", "₹ ")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SkTopAppBar(
    currentScreen: Screen,
    onNavigate: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier.testTag("sk_top_bar"),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = ObsidianBlack,
            titleContentColor = TextPrimary
        ),
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(ElectricBlue, NeonCyan)
                            )
                        )
                        .border(1.dp, NeonCyan, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "SK",
                        fontWeight = FontWeight.Black,
                        fontSize = 15.sp,
                        color = ObsidianBlack
                    )
                }
                Column {
                    Text(
                        text = "SK COMPUTERS",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 18.sp,
                        letterSpacing = 1.sp,
                        color = TextPrimary
                    )
                    Text(
                        text = "Sales • Refurbished • Chip-Level Repair",
                        fontSize = 11.sp,
                        color = NeonCyan,
                        maxLines = 1
                    )
                }
            }
        },
        actions = {
            // Admin Toggle Button
            FilledTonalButton(
                onClick = {
                    if (currentScreen == Screen.ADMIN) {
                        onNavigate(Screen.HOME)
                    } else {
                        onNavigate(Screen.ADMIN)
                    }
                },
                modifier = Modifier
                    .padding(end = 8.dp)
                    .testTag("admin_toggle_button"),
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = if (currentScreen == Screen.ADMIN) NeonCyan else DeepNavySurface,
                    contentColor = if (currentScreen == Screen.ADMIN) ObsidianBlack else TextSecondary
                ),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Icon(
                    imageVector = if (currentScreen == Screen.ADMIN) Icons.Default.Close else Icons.Default.AdminPanelSettings,
                    contentDescription = "Admin",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = if (currentScreen == Screen.ADMIN) "Exit" else "Admin",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    )
}

@Composable
fun SkBottomNavBar(
    currentScreen: Screen,
    onNavigate: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier.testTag("bottom_nav_bar"),
        containerColor = DeepNavySurface,
        tonalElevation = 8.dp
    ) {
        val items = listOf(
            Triple(Screen.HOME, Icons.Default.Home, "Home"),
            Triple(Screen.PRODUCTS, Icons.Default.Laptop, "Products"),
            Triple(Screen.SERVICES, Icons.Default.Build, "Services"),
            Triple(Screen.AI_ADVISOR, Icons.Default.Psychology, "AI Advisor"),
            Triple(Screen.CONTACT, Icons.Default.LocationOn, "Contact")
        )

        items.forEach { (screen, icon, label) ->
            val selected = currentScreen == screen
            NavigationBarItem(
                selected = selected,
                onClick = { onNavigate(screen) },
                icon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = label,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(
                        text = label,
                        fontSize = 11.sp,
                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = ObsidianBlack,
                    selectedTextColor = NeonCyan,
                    indicatorColor = NeonCyan,
                    unselectedIconColor = TextMuted,
                    unselectedTextColor = TextMuted
                )
            )
        }
    }
}

@Composable
fun ProductCard(
    product: Product,
    onEnquireWhatsApp: (Product) -> Unit,
    onViewDetails: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    val isRefurbished = product.condition.contains("Refurbished", ignoreCase = true) ||
            product.condition.contains("Pre-Owned", ignoreCase = true)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onViewDetails(product) }
            .testTag("product_card_${product.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = NavySurfaceCard),
        border = BorderStroke(1.dp, if (isRefurbished) AccentOrange.copy(alpha = 0.5f) else NavySurfaceBorder)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Badges row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Condition Badge
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = if (isRefurbished) AccentOrange.copy(alpha = 0.15f) else ElectricBlue.copy(alpha = 0.15f),
                    border = BorderStroke(
                        0.8.dp,
                        if (isRefurbished) AccentOrange.copy(alpha = 0.6f) else ElectricBlueLight.copy(alpha = 0.6f)
                    )
                ) {
                    Text(
                        text = product.condition,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                        color = if (isRefurbished) AccentOrange else ElectricBlueLight,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Stock Badge
                val inStock = product.stockStatus.contains("In Stock", ignoreCase = true)
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = if (inStock) AccentGreen.copy(alpha = 0.15f) else AccentRed.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = product.stockStatus,
                        modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp),
                        color = if (inStock) AccentGreen else AccentRed,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Category & Title
            Text(
                text = product.category.uppercase(),
                fontSize = 10.sp,
                letterSpacing = 1.sp,
                fontWeight = FontWeight.Bold,
                color = NeonCyan
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = product.title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Short Specs snippet (first 2 lines)
            val previewSpecs = product.specifications.lines().take(2).joinToString("\n")
            Text(
                text = previewSpecs,
                fontSize = 12.sp,
                color = TextSecondary,
                lineHeight = 16.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Price Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    if (product.originalPrice > product.price) {
                        Text(
                            text = formatCurrency(product.originalPrice),
                            fontSize = 12.sp,
                            color = TextMuted,
                            textDecoration = TextDecoration.LineThrough
                        )
                    }
                    Text(
                        text = formatCurrency(product.price),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = if (isRefurbished) AccentOrange else NeonCyan
                    )
                }

                val discount = if (product.originalPrice > product.price) {
                    (((product.originalPrice - product.price) / product.originalPrice) * 100).toInt()
                } else 0
                if (discount > 0) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = AccentGreen.copy(alpha = 0.2f)
                    ) {
                        Text(
                            text = "$discount% OFF",
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            color = AccentGreen,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Actions row: WhatsApp Enquiry & Details
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = { onViewDetails(product) },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("details_btn_${product.id}"),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, NavySurfaceBorder),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = TextSecondary),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    Text("Specs", fontSize = 12.sp)
                }

                Button(
                    onClick = { onEnquireWhatsApp(product) },
                    modifier = Modifier
                        .weight(1.3f)
                        .testTag("whatsapp_btn_${product.id}"),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AccentGreen,
                        contentColor = ObsidianBlack
                    ),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Chat,
                        contentDescription = "WhatsApp",
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("WhatsApp", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun ProductDetailDialog(
    product: Product,
    onDismiss: () -> Unit,
    onEnquireWhatsApp: (Product) -> Unit
) {
    val context = LocalContext.current
    val isRefurbished = product.condition.contains("Refurbished", ignoreCase = true) ||
            product.condition.contains("Pre-Owned", ignoreCase = true)

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = DeepNavySurface,
        titleContentColor = TextPrimary,
        textContentColor = TextSecondary,
        title = {
            Column {
                Text(
                    text = product.category.uppercase(),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = NeonCyan,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = product.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = formatCurrency(product.price),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Black,
                        color = if (isRefurbished) AccentOrange else NeonCyan
                    )
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = ChipBackground,
                        border = BorderStroke(0.8.dp, ChipBorder)
                    ) {
                        Text(
                            text = product.condition,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            color = TextPrimary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = NavySurfaceCard,
                    border = BorderStroke(1.dp, NavySurfaceBorder)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Verified,
                            contentDescription = "Warranty",
                            tint = AccentGreen,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "Warranty: ${product.warranty}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextPrimary
                        )
                    }
                }

                Text(
                    text = "Full Specifications:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = TextPrimary
                )

                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = ObsidianBlack,
                    border = BorderStroke(1.dp, NavySurfaceBorder)
                ) {
                    Text(
                        text = product.specifications,
                        modifier = Modifier.padding(12.dp),
                        fontSize = 12.sp,
                        color = TextSecondary,
                        lineHeight = 18.sp
                    )
                }

                Text(
                    text = "• Genuine physical inspection available at SK COMPUTERS shop.\n• 100% genuine components & ready on-spot testing.",
                    fontSize = 11.sp,
                    color = TextMuted,
                    lineHeight = 15.sp
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onDismiss()
                    onEnquireWhatsApp(product)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = AccentGreen,
                    contentColor = ObsidianBlack
                )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Chat,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text("WhatsApp Enquiry", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Close", color = TextMuted)
            }
        }
    )
}
