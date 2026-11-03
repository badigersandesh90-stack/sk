package com.example.ui

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.Enquiry
import com.example.data.StoreConfig
import com.example.ui.theme.*

data class ServiceItem(
    val id: String,
    val title: String,
    val icon: ImageVector,
    val turnaround: String,
    val startingPrice: String,
    val description: String,
    val highlights: List<String>
)

@Composable
fun ServicesScreen(
    storeConfig: StoreConfig,
    onSubmitServiceBooking: (Enquiry, () -> Unit) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var selectedServiceForBooking by remember { mutableStateOf<ServiceItem?>(null) }
    var showSuccessToast by remember { mutableStateOf(false) }

    val serviceList = remember {
        listOf(
            ServiceItem(
                id = "repair",
                title = "Computer & Laptop Repair",
                icon = Icons.Default.LaptopMac,
                turnaround = "Same Day / 24 Hours",
                startingPrice = "₹350 onwards",
                description = "Advanced chip-level motherboard troubleshooting, cracked screen replacement, battery replacement, hinge rebuilding, and dead laptop recovery.",
                highlights = listOf(
                    "Chip-level BGA rework and IC replacement",
                    "Original IPS & OLED laptop screens with 6-month warranty",
                    "Broken hinge & body fabrication",
                    "Keyboard & trackpad replacement",
                    "Liquid spill damage ultrasonic bath recovery"
                )
            ),
            ServiceItem(
                id = "software",
                title = "Software & OS Installation",
                icon = Icons.Default.SystemUpdateAlt,
                turnaround = "1 - 2 Hours",
                startingPrice = "₹299 onwards",
                description = "Genuine Windows 11/10 installation, driver optimization, Microsoft Office suite, commercial antivirus security, and critical data recovery.",
                highlights = listOf(
                    "Genuine Windows 11 / 10 installation with digital license",
                    "Full system driver update & BIOS optimization",
                    "Malware, ransomware & virus removal",
                    "Corrupted hard disk partition & photo data recovery",
                    "CAD, Graphic Design & Office software setups"
                )
            ),
            ServiceItem(
                id = "maintenance",
                title = "Computer Servicing & Maintenance",
                icon = Icons.Default.CleaningServices,
                turnaround = "Same Day",
                startingPrice = "₹450 onwards",
                description = "Deep chemical dust removal, Arctic MX-4 high-grade thermal paste re-pasting, cooling fan overhaul to stop overheating, thermal throttling, and noise.",
                highlights = listOf(
                    "Complete motherboard & heatsink ultrasonic dust cleaning",
                    "Premium Arctic MX-4 / Thermal Grizzly re-pasting",
                    "Cooling fan lubrication & bearing replacement",
                    "SMPS power supply voltage testing",
                    "Drop in CPU/GPU core temperatures by 15°C to 25°C"
                )
            ),
            ServiceItem(
                id = "upgrades",
                title = "Hardware Upgrades & Custom PC Building",
                icon = Icons.Default.Memory,
                turnaround = "30 - 60 Mins",
                startingPrice = "Free Installation on purchase",
                description = "Supercharge slow PCs and laptops with NVMe M.2 SSDs, RAM expansion, graphics cards, and custom gaming/workstation PC assembly.",
                highlights = listOf(
                    "HDD to NVMe SSD migration with 100% OS cloning (no data loss)",
                    "DDR4 & DDR5 RAM dual-channel boost",
                    "Custom gaming rig & 4K editing workstation assembly",
                    "Dedicated GPU installation (NVIDIA RTX / AMD Radeon)",
                    "Modular SMPS power supply upgrade"
                )
            ),
            ServiceItem(
                id = "printers",
                title = "Printer & Accessory Support",
                icon = Icons.Default.Print,
                turnaround = "Same Day",
                startingPrice = "₹250 onwards",
                description = "Complete laser printer toner refilling, ink tank unblocking, wireless network printer configuration, and peripheral repair.",
                highlights = listOf(
                    "HP, Canon, Epson laser cartridge toner refilling",
                    "Ink tank printhead unclogging & deep flush",
                    "Paper jam, roller wear & gear replacements",
                    "Office WiFi & Network printer sharing setup",
                    "Barcode scanner & receipt printer installation"
                )
            )
        )
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(ObsidianBlack)
            .padding(horizontal = 16.dp)
            .testTag("services_screen"),
        contentPadding = PaddingValues(top = 16.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column {
                Text(
                    text = "PROFESSIONAL IT SOLUTIONS",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = NeonCyan,
                    letterSpacing = 1.2.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Expert Repair & Hardware Services",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "All repairs performed by certified technicians using state-of-the-art diagnostic equipment. Transparent pricing and warranty on parts.",
                    fontSize = 13.sp,
                    color = TextSecondary,
                    lineHeight = 18.sp
                )
            }
        }

        items(serviceList, key = { it.id }) { service ->
            ServiceCard(
                service = service,
                onBookService = { selectedServiceForBooking = it },
                onEnquireWhatsApp = {
                    openWhatsApp(
                        context,
                        storeConfig.whatsappNumber,
                        "Hello SK COMPUTERS, I want to inquire about: ${service.title} (${service.startingPrice})."
                    )
                }
            )
        }
    }

    // Service Booking Dialog
    selectedServiceForBooking?.let { service ->
        ServiceBookingDialog(
            service = service,
            storeConfig = storeConfig,
            onDismiss = { selectedServiceForBooking = null },
            onSubmit = { enquiry ->
                onSubmitServiceBooking(enquiry) {
                    selectedServiceForBooking = null
                }
            }
        )
    }
}

@Composable
fun ServiceCard(
    service: ServiceItem,
    onBookService: (ServiceItem) -> Unit,
    onEnquireWhatsApp: (ServiceItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("service_card_${service.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = NavySurfaceCard),
        border = BorderStroke(1.dp, NavySurfaceBorder)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(CircleShape)
                        .background(ElectricBlue.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = service.icon,
                        contentDescription = service.title,
                        tint = NeonCyan,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = service.title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "⏱️ ${service.turnaround}",
                            fontSize = 11.sp,
                            color = TextMuted
                        )
                        Text(
                            text = "•",
                            fontSize = 11.sp,
                            color = TextMuted
                        )
                        Text(
                            text = service.startingPrice,
                            fontSize = 11.sp,
                            color = AccentGreen,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = service.description,
                fontSize = 13.sp,
                color = TextSecondary,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Highlights
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = ObsidianBlack.copy(alpha = 0.5f),
                border = BorderStroke(1.dp, NavySurfaceBorder)
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    service.highlights.forEach { highlight ->
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Text("✓", color = NeonCyan, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            Text(
                                text = highlight,
                                fontSize = 12.sp,
                                color = TextSecondary,
                                lineHeight = 16.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = { onBookService(service) },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("book_service_btn_${service.id}"),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.2.dp, NeonCyan),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = NeonCyan)
                ) {
                    Text("Book / Quote", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }

                Button(
                    onClick = { onEnquireWhatsApp(service) },
                    modifier = Modifier
                        .weight(1.2f)
                        .testTag("whatsapp_service_btn_${service.id}"),
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

@Composable
fun ServiceBookingDialog(
    service: ServiceItem,
    storeConfig: StoreConfig,
    onDismiss: () -> Unit,
    onSubmit: (Enquiry) -> Unit
) {
    val context = LocalContext.current
    var customerName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var deviceModel by remember { mutableStateOf("") }
    var issueDescription by remember { mutableStateOf("") }
    var sendViaWhatsAppToo by remember { mutableStateOf(true) }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = DeepNavySurface,
        titleContentColor = TextPrimary,
        title = {
            Column {
                Text(
                    text = "BOOK SERVICE / REQUEST QUOTE",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = NeonCyan,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = service.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedTextField(
                    value = customerName,
                    onValueChange = { customerName = it },
                    label = { Text("Your Full Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("booking_name_input"),
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
                    label = { Text("Contact Number / WhatsApp") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("booking_phone_input"),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary,
                        focusedBorderColor = NeonCyan,
                        unfocusedBorderColor = NavySurfaceBorder
                    )
                )

                OutlinedTextField(
                    value = deviceModel,
                    onValueChange = { deviceModel = it },
                    label = { Text("Device Model (e.g. Dell Inspiron 15, HP Pavilion)") },
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
                    value = issueDescription,
                    onValueChange = { issueDescription = it },
                    label = { Text("Describe the Issue / Requirements") },
                    maxLines = 3,
                    modifier = Modifier.fillMaxWidth().testTag("booking_issue_input"),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary,
                        focusedBorderColor = NeonCyan,
                        unfocusedBorderColor = NavySurfaceBorder
                    )
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Checkbox(
                        checked = sendViaWhatsAppToo,
                        onCheckedChange = { sendViaWhatsAppToo = it },
                        colors = CheckboxDefaults.colors(checkedColor = AccentGreen)
                    )
                    Text(
                        text = "Also send quote request directly to SK Computers WhatsApp",
                        fontSize = 11.sp,
                        color = TextSecondary
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (customerName.isNotBlank() && phone.isNotBlank()) {
                        val enquiry = Enquiry(
                            customerName = customerName.trim(),
                            phone = phone.trim(),
                            enquiryType = "Service Booking",
                            relatedItem = "${service.title} ($deviceModel)",
                            message = issueDescription.trim()
                        )
                        onSubmit(enquiry)

                        if (sendViaWhatsAppToo) {
                            val msg = "Hello SK COMPUTERS,\nService Booking Request:\nService: ${service.title}\nName: ${customerName.trim()}\nPhone: ${phone.trim()}\nDevice: ${deviceModel.trim()}\nIssue: ${issueDescription.trim()}"
                            openWhatsApp(context, storeConfig.whatsappNumber, msg)
                        }
                    }
                },
                modifier = Modifier.testTag("submit_booking_btn"),
                colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue),
                enabled = customerName.isNotBlank() && phone.isNotBlank()
            ) {
                Text("Confirm Booking")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = TextMuted)
            }
        }
    )
}
