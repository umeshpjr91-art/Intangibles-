package com.example.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.Memory
import com.example.ui.AppViewModel
import com.example.ui.components.BatchGraphic
import com.example.ui.theme.JnvBlue
import com.example.ui.theme.JnvGold
import com.example.ui.theme.JnvSky

@Composable
fun AlbumScreen(
    viewModel: AppViewModel,
    memories: List<Memory>
) {
    var showAddMemoryDialog by remember { mutableStateOf(false) }

    var memoryTitle by remember { mutableStateOf("") }
    var memoryDesc by remember { mutableStateOf("") }
    var memoryCategory by remember { mutableStateOf("Campus") }

    val categories = listOf("Photo", "Video", "Campus", "Group", "Fest", "Other")
    var expandedCat by remember { mutableStateOf(false) }

    var selectedMemoryForDetail by remember { mutableStateOf<Memory?>(null) }

    val context = androidx.compose.ui.platform.LocalContext.current
    val uriHandler = androidx.compose.ui.platform.LocalUriHandler.current

    val openAppDirectly = { key: String ->
        val url = when (key) {
            "instagram_qr" -> "https://www.instagram.com/intangibles_36/"
            "whatsapp_qr" -> "https://chat.whatsapp.com/invite/xi_science_2026_27"
            else -> ""
        }
        if (url.isNotEmpty()) {
            try {
                uriHandler.openUri(url)
            } catch (e: Exception) {
                try {
                    val intent = android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse(url)).apply {
                        addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
                    }
                    context.startActivity(intent)
                } catch (ex: Exception) {
                    // ignore
                }
            }
        }
    }

    var pickedMediaUri by remember { mutableStateOf("") }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            pickedMediaUri = uri.toString()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Header Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Batch Memories",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "Our journey in JNV Koppal (2021-2026)",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Button(
                    onClick = { showAddMemoryDialog = true },
                    modifier = Modifier.testTag("add_memory_button"),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add memory")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Add", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Memories Grid containing the "Add Your Memories" card at the top
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 90.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                // Feature item: "Add Your Memories" card dashboard (spans both columns)
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 4.dp)
                            .testTag("add_your_memories_card"),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.25f)
                        ),
                        border = CardDefaults.outlinedCardBorder().copy(
                            width = 1.dp,
                            brush = Brush.horizontalGradient(listOf(JnvBlue, JnvSky))
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .background(JnvBlue.copy(alpha = 0.15f), CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.UploadFile,
                                        contentDescription = "Upload Memory",
                                        tint = JnvBlue,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                                Column {
                                    Text(
                                        text = "Add Your Memories",
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = "Upload class photos & videos of your JNV days!",
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Button(
                                    onClick = {
                                        memoryCategory = "Photo"
                                        showAddMemoryDialog = true
                                    },
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(38.dp)
                                        .testTag("add_photo_option_btn"),
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp),
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = JnvBlue)
                                ) {
                                    Icon(Icons.Default.CameraAlt, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Add Photo", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                }

                                Button(
                                    onClick = {
                                        memoryCategory = "Video"
                                        showAddMemoryDialog = true
                                    },
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(38.dp)
                                        .testTag("add_video_option_btn"),
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp),
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = JnvGold)
                                ) {
                                    Icon(Icons.Default.Videocam, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Add Video", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSecondaryContainer)
                                }
                            }
                        }
                    }
                }

                // Header separator for actual grid contents
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Text(
                        text = "Archived Moments",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                    )
                }

                if (memories.isEmpty()) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 48.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No memories added yet. Be the first to share one!",
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 13.sp
                            )
                        }
                    }
                } else {
                    items(memories) { memory ->
                        MemoryGridCell(
                            memory = memory,
                            onClick = { selectedMemoryForDetail = memory },
                            onDelete = { viewModel.deleteMemory(memory.id) }
                        )
                    }
                }
            }
        }

        // Add Memory Dialog box
        if (showAddMemoryDialog) {
            AlertDialog(
                onDismissRequest = { 
                    showAddMemoryDialog = false 
                    pickedMediaUri = ""
                },
                title = { Text("Create Batch Memory", fontSize = 18.sp, fontWeight = FontWeight.Bold) },
                text = {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = memoryTitle,
                            onValueChange = { memoryTitle = it },
                            label = { Text("Title") },
                            placeholder = { Text("e.g. Science lab experiments") },
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth().testTag("mem_title_input")
                        )

                        OutlinedTextField(
                            value = memoryDesc,
                            onValueChange = { memoryDesc = it },
                            label = { Text("Description") },
                            placeholder = { Text("What made this moment special?") },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth().height(90.dp).testTag("mem_desc_input")
                        )

                        // Category Dropdown
                        Box(modifier = Modifier.fillMaxWidth()) {
                            TextButton(
                                onClick = { expandedCat = true },
                                modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(10.dp))
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text("Category: $memoryCategory", color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Icon(Icons.Default.Label, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                                }
                            }

                            DropdownMenu(
                                expanded = expandedCat,
                                onDismissRequest = { expandedCat = false }
                            ) {
                                categories.forEach { cat ->
                                    DropdownMenuItem(
                                        text = { Text(cat) },
                                        onClick = {
                                            memoryCategory = cat
                                            expandedCat = false
                                        }
                                    )
                                }
                            }
                        }

                        // Photo / Video picker button
                        if (memoryCategory == "Photo" || memoryCategory == "Video") {
                            Text("Media Attachment", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
                            
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Button(
                                    onClick = {
                                        photoPickerLauncher.launch(
                                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo)
                                        )
                                    },
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = JnvBlue),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Icon(Icons.Default.UploadFile, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Pick File", fontSize = 10.sp)
                                }

                                Button(
                                    onClick = {
                                        pickedMediaUri = if (memoryCategory == "Video") "preset:vlog" else "preset:photo"
                                    },
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = JnvGold),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Simulate Media", fontSize = 10.sp)
                                }
                            }

                            if (pickedMediaUri.isNotEmpty()) {
                                Surface(
                                    color = Color(0xFFE8F5E9),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        modifier = Modifier.padding(10.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF2E7D32), modifier = Modifier.size(16.dp))
                                        Text(
                                            text = if (pickedMediaUri.startsWith("preset:")) "Attached JNV Sim Preset!" else "System Media file selected!",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = Color(0xFF2E7D32)
                                        )
                                    }
                                }
                            }
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (memoryTitle.isNotBlank()) {
                                viewModel.addMemory(
                                    title = memoryTitle,
                                    description = memoryDesc,
                                    category = memoryCategory,
                                    customImageKey = if (pickedMediaUri.isNotEmpty()) pickedMediaUri else memoryCategory
                                )
                                // Clear inputs
                                memoryTitle = ""
                                memoryDesc = ""
                                memoryCategory = "Campus"
                                pickedMediaUri = ""
                                showAddMemoryDialog = false
                            }
                        }
                    ) {
                        Text("Create")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { 
                        showAddMemoryDialog = false 
                        pickedMediaUri = ""
                    }) {
                        Text("Cancel")
                    }
                }
            )
        }

        // Memory Detail Sheet Dialog
        if (selectedMemoryForDetail != null) {
            val mem = selectedMemoryForDetail!!
            
            // Auto-launch directly to WhatsApp/Instagram when opened!
            LaunchedEffect(mem.id) {
                if (mem.customImageKey == "instagram_qr" || mem.customImageKey == "whatsapp_qr") {
                    openAppDirectly(mem.customImageKey)
                }
            }

            AlertDialog(
                onDismissRequest = { selectedMemoryForDetail = null },
                title = null,
                text = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        // Media Display: Play simulator if Video, otherwise static graphic
                        if (mem.category == "Video") {
                            SimulatedVideoPlayer(
                                title = mem.title,
                                mediaUri = if (mem.isPreset) mem.customImageKey else mem.customImageKey,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(220.dp)
                                    .clip(RoundedCornerShape(14.dp))
                            ) {
                                BatchGraphic(
                                    key = if (mem.isPreset) mem.customImageKey else mem.customImageKey,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }

                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = mem.title,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Category: ${mem.category}",
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = mem.date,
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

                        Text(
                            text = mem.description,
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 18.sp
                        )

                        // Launch details for QRs
                        if (mem.customImageKey == "instagram_qr" || mem.customImageKey == "whatsapp_qr") {
                            Button(
                                onClick = { openAppDirectly(mem.customImageKey) },
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (mem.customImageKey == "whatsapp_qr") Color(0xFF25D366) else Color(0xFFE1306C)
                                ),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(
                                    imageVector = if (mem.customImageKey == "whatsapp_qr") Icons.Default.Groups else Icons.Default.CameraAlt,
                                    contentDescription = null,
                                    tint = Color.White
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = if (mem.customImageKey == "whatsapp_qr") "Launch WhatsApp Group" else "Open Instagram Profile",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Text(
                                text = "A launch attempt has also been auto-triggered. Tap button above if not loaded.",
                                fontSize = 9.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                                modifier = Modifier.fillMaxWidth(),
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                },
                confirmButton = {
                    Button(onClick = { selectedMemoryForDetail = null }) {
                        Text("Dismiss")
                    }
                }
            )
        }
    }
}

@Composable
fun MemoryGridCell(
    memory: Memory,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag("memory_cell_${memory.id}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            // Visual Graphic Canvas Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(115.dp)
            ) {
                BatchGraphic(
                    key = if (memory.customImageKey.isNotEmpty()) memory.customImageKey else memory.category,
                    modifier = Modifier.fillMaxSize()
                )

                // Delete Button for user added memories
                if (!memory.isPreset) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(4.dp)
                            .size(28.dp)
                            .background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(14.dp))
                            .clickable { onDelete() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete memory",
                            tint = Color.White,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }

            // Cell Metadata
            Column(
                modifier = Modifier.padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = memory.title,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = memory.description,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun SimulatedVideoPlayer(
    title: String,
    mediaUri: String,
    modifier: Modifier = Modifier
) {
    var isPlaying by remember { mutableStateOf(true) }
    var progress by remember { mutableFloatStateOf(0.18f) }
    var elapsedSeconds by remember { mutableIntStateOf(14) }

    // Increment progress dynamically when playing
    LaunchedEffect(isPlaying) {
        if (isPlaying) {
            while (true) {
                kotlinx.coroutines.delay(1000)
                elapsedSeconds = (elapsedSeconds + 1) % 85
                progress = elapsedSeconds.toFloat() / 85f
            }
        }
    }

    Box(
        modifier = modifier
            .background(Color.Black, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
    ) {
        // Underneath background visual preview
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(JnvBlue.copy(alpha = 0.5f), Color.Black)
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            if (mediaUri.startsWith("content://") || mediaUri.contains("/")) {
                Image(
                    painter = coil.compose.rememberAsyncImagePainter(model = mediaUri),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.6f)
                )
            } else {
                Icon(
                    imageVector = Icons.Default.PlayCircleFilled,
                    contentDescription = null,
                    tint = JnvGold.copy(alpha = 0.7f),
                    modifier = Modifier.size(56.dp)
                )
            }

            // Flashing record indicator if running
            if (isPlaying) {
                Row(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(10.dp)
                        .background(Color.Red, RoundedCornerShape(4.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .background(Color.White, CircleShape)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "LIVE",
                        color = Color.White,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Overlay control buttons
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(Color.Black.copy(alpha = 0.65f))
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            // Seeker bar
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp),
                color = JnvGold,
                trackColor = Color.White.copy(alpha = 0.25f)
            )

            // Timeline & Controls
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { isPlaying = !isPlaying },
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = "Play/Pause",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Text(
                        text = "00:${elapsedSeconds.toString().padStart(2, '0')} / 01:25",
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Text(
                    text = "VLOG PLAYER",
                    color = JnvGold,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Black
                )
            }
        }
    }
}
