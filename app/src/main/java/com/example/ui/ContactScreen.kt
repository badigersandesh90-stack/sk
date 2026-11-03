package com.example.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.Enquiry
import com.example.data.StoreConfig
import com.example.ui.theme.*

@Composable
fun ContactScreen(
    storeConfig: StoreConfig,
    onSubmitEnquiry: (Enquiry, () -> Unit) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    var customerName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var isSubmitted by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(ObsidianBlack)
            .padding(horizontal = 16.dp)
            .testTag("contact_screen"),
        contentPadding = PaddingValues(top = 16.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column {
                Text(
                    text = "GET IN TOUCH",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = NeonCyan,
                    letterSpacing = 1.2.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Contact SK COMPUTERS",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Visit our showroom, request instant pricing quotes on WhatsApp, or book a technician call-out.",
                    fontSize = 13.sp,
                    color = TextSecondary,
                    lineHeight = 18.sp
                )
            }
        }

        // 1. Direct Action Quick Buttons Row
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = { callPhone(context, storeConfig.phone) },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .testTag("call_now_button"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ElectricBlue,
                        contentColor = TextPrimary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = "Call Now",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Call Now", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }

                Button(
                    onClick = {
                        openWhatsApp(
                            context,
                            storeConfig.whatsappNumber,
                            "Hello SK COMPUTERS, I want to inquire about computers and repair services."
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .testTag("whatsapp_now_button"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AccentGreen,
                        contentColor = ObsidianBlack
                    )
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Chat,
                        contentDescription = "WhatsApp",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("WhatsApp", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
            }
        }

        // 2. Google Maps Location Section (Uses user's provided link)
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("maps_location_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = NavySurfaceCard),
                border = BorderStroke(1.2.dp, NeonCyan.copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(NeonCyan.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = "Location",
                                tint = NeonCyan,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Column {
                            Text(
                                text = "STORE LOCATION",
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

                    Spacer(modifier = Modifier.height(12.dp))

                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = ObsidianBlack,
                        border = BorderStroke(1.dp, NavySurfaceBorder)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.Top,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Place,
                                    contentDescription = null,
                                    tint = AccentOrange,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = storeConfig.address,
                                    fontSize = 13.sp,
                                    color = TextSecondary,
                                    lineHeight = 18.sp
                                )
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Link,
                                    contentDescription = null,
                                    tint = ElectricBlueLight,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = "Google Maps: maps.app.goo.gl/Ygc7e9q9QFsorige9",
                                    fontSize = 11.sp,
                                    color = ElectricBlueLight,
                                    maxLines = 1
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = { openMaps(context, storeConfig.mapsUrl) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("directions_button"),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ElectricBlue,
                            contentColor = TextPrimary
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Directions,
                            contentDescription = "Get Directions",
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Get Directions to Shop (Google Maps)", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                }
            }
        }

        // 3. Contact Information & Hours Details
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = NavySurfaceCard),
                border = BorderStroke(1.dp, NavySurfaceBorder)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text(
                        text = "BUSINESS INFORMATION",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = NeonCyan,
                        letterSpacing = 1.sp
                    )

                    ContactInfoRow(
                        icon = Icons.Default.AccessTime,
                        label = "Business Hours",
                        value = "${storeConfig.businessHoursWeekday}\n${storeConfig.businessHoursWeekend}"
                    )

                    HorizontalDivider(color = NavySurfaceBorder)

                    ContactInfoRow(
                        icon = Icons.Default.Phone,
                        label = "Phone & Hotline",
                        value = storeConfig.phone,
                        actionLabel = "Call",
                        onAction = { callPhone(context, storeConfig.phone) }
                    )

                    HorizontalDivider(color = NavySurfaceBorder)

                    ContactInfoRow(
                        icon = Icons.Default.Email,
                        label = "Email Address",
                        value = storeConfig.email,
                        actionLabel = "Email",
                        onAction = {
                            try {
                                val intent = Intent(Intent.ACTION_SENDTO).apply {
                                    data = Uri.parse("mailto:${storeConfig.email}")
                                }
                                context.startActivity(intent)
                            } catch (_: Exception) {}
                        }
                    )
                }
            }
        }

        // 4. Send Direct Message / Inquiry Form
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = NavySurfaceCard),
                border = BorderStroke(1.dp, NavySurfaceBorder)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "SEND US A MESSAGE",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = NeonCyan,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "Have questions about a laptop, spare part, or repair? Drop your message below.",
                        fontSize = 12.sp,
                        color = TextSecondary
                    )

                    if (isSubmitted) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = AccentGreen.copy(alpha = 0.15f),
                            border = BorderStroke(1.dp, AccentGreen.copy(alpha = 0.4f))
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = AccentGreen
                                )
                                Text(
                                    text = "Thank you! Your inquiry was received. We will respond promptly.",
                                    fontSize = 12.sp,
                                    color = TextPrimary
                                )
                            }
                        }
                    }

                    OutlinedTextField(
                        value = customerName,
                        onValueChange = { customerName = it },
                        label = { Text("Your Name *") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth().testTag("contact_name_input"),
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary,
                            focusedBorderColor = NeonCyan,
                            unfocusedBorderColor = NavySurfaceBorder
                        )
                    )

                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text("Phone Number / WhatsApp *") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth().testTag("contact_phone_input"),
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary,
                            focusedBorderColor = NeonCyan,
                            unfocusedBorderColor = NavySurfaceBorder
                        )
                    )

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email (Optional)") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary,
                            focusedBorderColor = NeonCyan,
                            unfocusedBorderColor = NavySurfaceBorder
                        )
                    )

                    OutlinedTextField(
                        value = message,
                        onValueChange = { message = it },
                        label = { Text("Message / What are you looking for? *") },
                        maxLines = 4,
                        modifier = Modifier.fillMaxWidth().testTag("contact_message_input"),
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary,
                            focusedBorderColor = NeonCyan,
                            unfocusedBorderColor = NavySurfaceBorder
                        )
                    )

                    Button(
                        onClick = {
                            if (customerName.isNotBlank() && phone.isNotBlank() && message.isNotBlank()) {
                                val enquiry = Enquiry(
                                    customerName = customerName.trim(),
                                    phone = phone.trim(),
                                    email = email.trim(),
                                    enquiryType = "General Contact",
                                    message = message.trim()
                                )
                                onSubmitEnquiry(enquiry) {
                                    isSubmitted = true
                                    customerName = ""
                                    phone = ""
                                    email = ""
                                    message = ""
                                    Toast.makeText(context, "Enquiry submitted to SK COMPUTERS!", Toast.LENGTH_SHORT).show()
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("submit_contact_btn"),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ElectricBlue,
                            contentColor = TextPrimary
                        ),
                        enabled = customerName.isNotBlank() && phone.isNotBlank() && message.isNotBlank()
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Send Inquiry", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun ContactInfoRow(
    icon: ImageVector,
    label: String,
    value: String,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = NeonCyan,
                modifier = Modifier.size(20.dp)
            )
            Column {
                Text(
                    text = label,
                    fontSize = 11.sp,
                    color = TextMuted
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = value,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextPrimary,
                    lineHeight = 18.sp
                )
            }
        }

        if (actionLabel != null && onAction != null) {
            FilledTonalButton(
                onClick = onAction,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = DeepNavySurface,
                    contentColor = NeonCyan
                ),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text(actionLabel, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
