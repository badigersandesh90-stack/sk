package com.example.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.Product
import com.example.data.StoreConfig
import com.example.ui.theme.*

@Composable
fun HomeScreen(
    featuredProducts: List<Product>,
    storeConfig: StoreConfig,
    onNavigate: (Screen) -> Unit,
    onEnquireWhatsApp: (Product) -> Unit,
    onViewProductDetails: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(ObsidianBlack)
            .testTag("home_screen"),
        contentPadding = PaddingValues(bottom = 28.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // 1. Hero Banner with Image & Branding
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
            ) {
                // Background image
                Image(
                    painter = painterResource(id = R.drawable.sk_hero_banner_1793452031369),
                    contentDescription = "SK COMPUTERS Showroom Banner",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // High-tech dark gradient overlay for text readability
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    ObsidianBlack.copy(alpha = 0.75f),
                                    ObsidianBlack
                                )
                            )
                        )
                )

                // Overlay Content
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = ElectricBlue.copy(alpha = 0.25f),
                        border = BorderStroke(1.dp, NeonCyan.copy(alpha = 0.6f))
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(AccentGreen)
                            )
                            Text(
                                text = "PREMIUM COMPUTER & LAPTOP HUB",
                                color = NeonCyan,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "SK COMPUTERS",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Black,
                        color = TextPrimary,
                        letterSpacing = 1.sp
                    )

                    Text(
                        text = "Your Trusted Tech Partner for Sales, Certified Refurbished Laptops & Chip-Level Repairs",
                        fontSize = 12.sp,
                        color = TextSecondary,
                        lineHeight = 16.sp,
                        maxLines = 2
                    )
                }
            }
        }

        // 2. Attractive Call-to-Action Buttons
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Shop Products Button
                    Button(
                        onClick = { onNavigate(Screen.PRODUCTS) },
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp)
                            .testTag("shop_products_btn"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ElectricBlue,
                            contentColor = TextPrimary
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Laptop,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Shop Products", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }

                    // Our Services Button
                    OutlinedButton(
                        onClick = { onNavigate(Screen.SERVICES) },
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp)
                            .testTag("our_services_btn"),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.5.dp, NeonCyan),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = NeonCyan
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Build,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Our Services", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                }

                // Contact Us Button
                Button(
                    onClick = { onNavigate(Screen.CONTACT) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("contact_us_btn"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = NavySurfaceElevated,
                        contentColor = TextPrimary
                    ),
                    border = BorderStroke(1.dp, NavySurfaceBorder)
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        tint = NeonCyan
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Contact Us & Directions", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = TextMuted
                    )
                }
            }
        }

        // 3. Why Choose SK COMPUTERS (Key Selling Points)
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = "THE SK ADVANTAGE",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = NeonCyan,
                    letterSpacing = 1.2.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Why Customers Trust SK COMPUTERS",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    AdvantageCard(
                        icon = Icons.Default.VerifiedUser,
                        title = "100% Genuine",
                        subtitle = "Brand sealed items",
                        modifier = Modifier.weight(1f)
                    )
                    AdvantageCard(
                        icon = Icons.Default.Memory,
                        title = "Chip-Level",
                        subtitle = "BGA & board repair",
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    AdvantageCard(
                        icon = Icons.Default.Handshake,
                        title = "Refurbished Hub",
                        subtitle = "Warranty on every unit",
                        modifier = Modifier.weight(1f)
                    )
                    AdvantageCard(
                        icon = Icons.AutoMirrored.Filled.Chat,
                        title = "Instant Quote",
                        subtitle = "Fast WhatsApp support",
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // 4. AI Deep Thinking Tech Advisor Feature Banner
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clickable { onNavigate(Screen.AI_ADVISOR) }
                    .testTag("ai_advisor_banner"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = NavySurfaceCard),
                border = BorderStroke(1.2.dp, NeonCyan.copy(alpha = 0.6f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(ElectricBlue, NeonCyan)
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Psychology,
                            contentDescription = "AI Thinking Advisor",
                            tint = ObsidianBlack,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = "SK AI RIG BUILDER",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = TextPrimary
                            )
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = NeonCyan.copy(alpha = 0.2f)
                            ) {
                                Text(
                                    text = "THINKING MODE",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = NeonCyan,
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Get architectural hardware recommendations, PC build planning & chip-level diagnostic checks powered by Gemini.",
                            fontSize = 11.sp,
                            color = TextSecondary,
                            lineHeight = 15.sp
                        )
                    }

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Open AI Advisor",
                        tint = NeonCyan,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        // 5. Featured Products Section
        item {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "HANDPICKED DEALS",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = NeonCyan,
                            letterSpacing = 1.2.sp
                        )
                        Text(
                            text = "Featured Hardware & Laptops",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    }

                    TextButton(onClick = { onNavigate(Screen.PRODUCTS) }) {
                        Text("View All", color = ElectricBlueLight, fontSize = 13.sp)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    items(featuredProducts, key = { it.id }) { product ->
                        Box(modifier = Modifier.width(280.dp)) {
                            ProductCard(
                                product = product,
                                onEnquireWhatsApp = onEnquireWhatsApp,
                                onViewDetails = onViewProductDetails
                            )
                        }
                    }
                }
            }
        }

        // 6. Direct Google Maps Location Section (from user link)
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = NavySurfaceCard),
                border = BorderStroke(1.dp, NavySurfaceBorder)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(ElectricBlue.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Storefront,
                                contentDescription = null,
                                tint = NeonCyan,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Column {
                            Text(
                                text = "VISIT OUR SHOP",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = NeonCyan,
                                letterSpacing = 1.sp
                            )
                            Text(
                                text = storeConfig.storeName,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = storeConfig.address,
                        fontSize = 13.sp,
                        color = TextSecondary,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "🕒 ${storeConfig.businessHoursWeekday}",
                        fontSize = 12.sp,
                        color = TextMuted
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = { openMaps(context, storeConfig.mapsUrl) },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("home_directions_btn"),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = ElectricBlue,
                                contentColor = TextPrimary
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Directions,
                                contentDescription = "Directions",
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Get Directions", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }

                        Button(
                            onClick = {
                                openWhatsApp(
                                    context,
                                    storeConfig.whatsappNumber,
                                    "Hello SK COMPUTERS, I want to inquire about your products and services."
                                )
                            },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("home_whatsapp_btn"),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = AccentGreen,
                                contentColor = ObsidianBlack
                            )
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Chat,
                                contentDescription = "WhatsApp",
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("WhatsApp", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AdvantageCard(
    icon: ImageVector,
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = NavySurfaceCard,
        border = BorderStroke(1.dp, NavySurfaceBorder)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(ElectricBlue.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = NeonCyan,
                    modifier = Modifier.size(18.dp)
                )
            }
            Column {
                Text(
                    text = title,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = subtitle,
                    fontSize = 10.sp,
                    color = TextMuted,
                    maxLines = 1
                )
            }
        }
    }
}
