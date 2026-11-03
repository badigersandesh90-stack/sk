package com.example.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.StoreConfig
import com.example.ui.theme.*

@Composable
fun AiAdvisorScreen(
    aiState: AiUiState,
    storeConfig: StoreConfig,
    onAskAi: (String) -> Unit,
    onResetAi: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var queryText by remember { mutableStateOf("") }
    var showThoughts by remember { mutableStateOf(true) }

    val presetQueries = remember {
        listOf(
            "Build gaming PC under ₹65,000 with RTX graphics",
            "Laptop turns on, fans spin, but black screen (no display)",
            "Compare refurbished Dell Latitude 7490 vs new budget laptop",
            "Upgrade 500GB HDD to 1TB NVMe SSD without data loss",
            "Laptop overheating & shutting down after 30 mins gaming"
        )
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(ObsidianBlack)
            .padding(horizontal = 16.dp)
            .testTag("ai_advisor_screen"),
        contentPadding = PaddingValues(top = 16.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header with Model Badge
        item {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
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
                            contentDescription = "AI Advisor",
                            tint = ObsidianBlack,
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    Column {
                        Text(
                            text = "SK AI HARDWARE ADVISOR",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = NeonCyan,
                            letterSpacing = 1.2.sp
                        )
                        Text(
                            text = "PC Rig Builder & Diagnostic Lab",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Black,
                            color = TextPrimary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // High Thinking Mode Badge
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = DeepNavySurface,
                    border = BorderStroke(1.dp, NavySurfaceBorder)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = NeonCyan,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "Model: gemini-4-argon • Thinking Level: HIGH",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = NeonCyan
                        )
                    }
                }
            }
        }

        // Query Input Section
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = NavySurfaceCard),
                border = BorderStroke(1.dp, NavySurfaceBorder)
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "Ask any complex hardware, upgrade, or repair query:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextSecondary
                    )

                    OutlinedTextField(
                        value = queryText,
                        onValueChange = { queryText = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("ai_query_input"),
                        placeholder = {
                            Text(
                                "Describe your issue, budget, or upgrade requirement...",
                                fontSize = 13.sp,
                                color = TextMuted
                            )
                        },
                        minLines = 3,
                        maxLines = 5,
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary,
                            focusedBorderColor = NeonCyan,
                            unfocusedBorderColor = NavySurfaceBorder,
                            focusedContainerColor = DeepNavySurface,
                            unfocusedContainerColor = DeepNavySurface
                        )
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Deep hardware bottleneck analysis enabled",
                            fontSize = 10.sp,
                            color = TextMuted
                        )

                        Button(
                            onClick = {
                                if (queryText.isNotBlank()) {
                                    onAskAi(queryText.trim())
                                }
                            },
                            modifier = Modifier.testTag("submit_ai_query_btn"),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = ElectricBlue,
                                contentColor = TextPrimary
                            ),
                            enabled = queryText.isNotBlank() && aiState !is AiUiState.Thinking
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Send,
                                contentDescription = "Consult",
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Consult Engineer", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    }
                }
            }
        }

        // Quick Suggestion Chips
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Quick Inquiries:",
                    fontSize = 12.sp,
                    color = TextMuted,
                    fontWeight = FontWeight.SemiBold
                )

                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(presetQueries) { preset ->
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = DeepNavySurface,
                            border = BorderStroke(1.dp, NavySurfaceBorder),
                            modifier = Modifier.clickable {
                                queryText = preset
                                onAskAi(preset)
                            }
                        ) {
                            Text(
                                text = preset,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
                                fontSize = 11.sp,
                                color = TextSecondary,
                                maxLines = 1
                            )
                        }
                    }
                }
            }
        }

        // AI Response or Thinking Indicator
        when (aiState) {
            is AiUiState.Idle -> {
                item {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = NavySurfaceCard.copy(alpha = 0.5f),
                        border = BorderStroke(1.dp, NavySurfaceBorder)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Lightbulb,
                                contentDescription = null,
                                tint = NeonCyan,
                                modifier = Modifier.size(28.dp)
                            )
                            Text(
                                text = "Ask anything about computer builds, repairs, or refurbished models.",
                                fontSize = 12.sp,
                                color = TextMuted,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                        }
                    }
                }
            }

            is AiUiState.Thinking -> {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth().testTag("ai_thinking_state"),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = NavySurfaceCard),
                        border = BorderStroke(1.dp, NeonCyan.copy(alpha = 0.6f))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            CircularProgressIndicator(
                                color = NeonCyan,
                                modifier = Modifier.size(36.dp)
                            )
                            Text(
                                text = "gemini-4-argon is thinking with HIGH reasoning depth...",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = NeonCyan
                            )
                            Text(
                                text = "Evaluating component bottlenecks, thermal headroom, BIOS compatibility, and SK Computers inventory...",
                                fontSize = 11.sp,
                                color = TextMuted,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                        }
                    }
                }
            }

            is AiUiState.Success -> {
                val response = aiState.response
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth().testTag("ai_response_card"),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = NavySurfaceCard),
                        border = BorderStroke(1.2.dp, NeonCyan)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = AccentGreen,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Text(
                                        text = "Diagnostic & Solution Verified",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = AccentGreen
                                    )
                                }

                                Text(
                                    text = response.modelUsed,
                                    fontSize = 9.sp,
                                    color = TextMuted,
                                    maxLines = 1
                                )
                            }

                            // Collapsible Thinking Process disclosure
                            if (response.reasoningThoughts.isNotBlank()) {
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = DeepNavySurface,
                                    border = BorderStroke(1.dp, NavySurfaceBorder),
                                    modifier = Modifier.clickable { showThoughts = !showThoughts }
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Psychology,
                                                    contentDescription = null,
                                                    tint = NeonCyan,
                                                    modifier = Modifier.size(16.dp)
                                                )
                                                Text(
                                                    text = "Chain-of-Thought & Hardware Reasoning",
                                                    fontSize = 11.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = NeonCyan
                                                )
                                            }

                                            Icon(
                                                imageVector = if (showThoughts) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                                contentDescription = null,
                                                tint = TextMuted
                                            )
                                        }

                                        AnimatedVisibility(visible = showThoughts) {
                                            Column(modifier = Modifier.padding(top = 8.dp)) {
                                                HorizontalDivider(color = NavySurfaceBorder)
                                                Spacer(modifier = Modifier.height(6.dp))
                                                Text(
                                                    text = response.reasoningThoughts,
                                                    fontSize = 11.sp,
                                                    color = TextMuted,
                                                    lineHeight = 16.sp
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            // Main Technical Advisory Body
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = ObsidianBlack,
                                border = BorderStroke(1.dp, NavySurfaceBorder)
                            ) {
                                Text(
                                    text = response.replyText,
                                    modifier = Modifier.padding(14.dp),
                                    fontSize = 13.sp,
                                    color = TextPrimary,
                                    lineHeight = 19.sp
                                )
                            }

                            // Actions: WhatsApp Inquire with Advice & Copy
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                OutlinedButton(
                                    onClick = {
                                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                        val clip = ClipData.newPlainText("SK Diagnostic", response.replyText)
                                        clipboard.setPrimaryClip(clip)
                                        Toast.makeText(context, "Copied to clipboard!", Toast.LENGTH_SHORT).show()
                                    },
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(8.dp),
                                    border = BorderStroke(1.dp, NavySurfaceBorder),
                                    colors = ButtonDefaults.outlinedButtonColors(contentColor = TextSecondary)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ContentCopy,
                                        contentDescription = "Copy",
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Copy", fontSize = 12.sp)
                                }

                                Button(
                                    onClick = {
                                        val preview = response.replyText.lines().take(4).joinToString(" ")
                                        val msg = "Hello SK COMPUTERS,\nI consulted your AI Hardware Advisor for query:\n\"$queryText\"\n\nHere is the recommended configuration/diagnostic:\n$preview\n\nPlease let me know pricing and stock availability."
                                        openWhatsApp(context, storeConfig.whatsappNumber, msg)
                                    },
                                    modifier = Modifier.weight(1.5f),
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = AccentGreen,
                                        contentColor = ObsidianBlack
                                    )
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.Chat,
                                        contentDescription = "WhatsApp",
                                        modifier = Modifier.size(15.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Inquire on WhatsApp", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }

            is AiUiState.Error -> {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = AccentRed.copy(alpha = 0.15f)),
                        border = BorderStroke(1.dp, AccentRed.copy(alpha = 0.5f))
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Analysis Error",
                                fontWeight = FontWeight.Bold,
                                color = AccentRed,
                                fontSize = 14.sp
                            )
                            Text(
                                text = aiState.message,
                                color = TextPrimary,
                                fontSize = 12.sp
                            )
                            Button(
                                onClick = onResetAi,
                                colors = ButtonDefaults.buttonColors(containerColor = DeepNavySurface)
                            ) {
                                Text("Try Again")
                            }
                        }
                    }
                }
            }
        }
    }
}
