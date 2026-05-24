package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.JnvBlue
import com.example.ui.theme.JnvGold
import com.example.ui.theme.JnvSky

@Composable
fun BatchGraphic(key: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        if (key.startsWith("content://") || key.startsWith("http") || key.contains("/")) {
            Image(
                painter = coil.compose.rememberAsyncImagePainter(model = key),
                contentDescription = "User custom memory photo",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } else if (key == "preset:photo") {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Brush.verticalGradient(listOf(Color(0xFFFFFAF0), Color(0xFFFFD54F)))),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = null,
                        tint = JnvBlue,
                        modifier = Modifier.size(36.dp)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "JNV Photo Upload",
                        fontSize = 10.sp,
                        color = JnvBlue,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        } else if (key == "preset:vlog") {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Brush.verticalGradient(listOf(Color(0xFFE0F7FA), Color(0xFF26C6DA)))),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Videocam,
                        contentDescription = null,
                        tint = JnvBlue,
                        modifier = Modifier.size(36.dp)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "XI Science Vlog",
                        fontSize = 10.sp,
                        color = JnvBlue,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        } else {
            when (key) {
                "group_photo" -> GroupPhotoGraphic(Modifier.fillMaxSize())
                "school_gate" -> {
                    Image(
                        painter = painterResource(id = R.drawable.img_jnv_gate),
                        contentDescription = "PM SHRI JNV Koppal Entrance Gate",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                "navodaya_logo" -> {
                    Image(
                        painter = painterResource(id = R.drawable.img_login_bg),
                        contentDescription = "Navodaya Vidyalaya Samiti Pride Logo",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                "instagram_qr" -> {
                    Image(
                        painter = painterResource(id = R.drawable.img_instagram_qr),
                        contentDescription = "Our Batch Instagram QR Code @INTANGIBLES_36",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                "whatsapp_qr" -> {
                    Image(
                        painter = painterResource(id = R.drawable.img_whatsapp_qr),
                        contentDescription = "XI Science WhatsApp Group Link QR Code",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                else -> CustomMemoryPlaceholder(key, Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun GroupPhotoGraphic(modifier: Modifier = Modifier) {
    val skyColor = Color(0xFFE3F2FD)
    val buildingColor = Color(0xFFD32F2F) // Red brick academic block
    val pillarColor = Color(0xFFFFEB3B)   // Yellow elements
    val uniformBlue = Color(0xFF1E88E5)
    val uniformWhite = Color(0xFFFFFFFF)

    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // 1. Draw sky background
        drawRect(color = skyColor, size = size)

        // 2. Draw academic background brick building
        drawRect(
            color = buildingColor,
            topLeft = Offset(0f, h * 0.2f),
            size = Size(w, h * 0.6f)
        )

        // Draw brick building pillars
        val pillarWidth = w * 0.08f
        for (i in 0..5) {
            drawRect(
                color = pillarColor,
                topLeft = Offset(i * (w / 5.2f), h * 0.2f),
                size = Size(pillarWidth, h * 0.6f)
            )
        }

        // Draw windows on building
        val winW = w * 0.06f
        val winH = h * 0.08f
        for (row in 0..1) {
            val startY = h * 0.25f + row * (h * 0.15f)
            for (col in 0..6) {
                drawRoundRect(
                    color = Color(0xFF1565C0),
                    topLeft = Offset(col * (w / 7.2f) + w * 0.04f, startY),
                    size = Size(winW, winH),
                    cornerRadius = CornerRadius(4f, 4f)
                )
            }
        }

        // 3. Draw paved foreground floor
        drawRect(
            color = Color(0xFFCFD8DC),
            topLeft = Offset(0f, h * 0.75f),
            size = Size(w, h * 0.25f)
        )

        // Draw pavement grid lines
        val stroke = Stroke(width = 2f)
        for (i in 1..8) {
            drawLine(
                color = Color(0xFF90A4AE),
                start = Offset(i * (w / 9f), h * 0.75f),
                end = Offset(i * (w / 9f) - w * 0.1f, h),
                strokeWidth = 2f
            )
        }

        // 4. Draw students outlines in three rows
        // Represent student bodies with custom circles and paths
        val studentCount = 12
        val personRadius = w * 0.025f

        // Row 3 (Back) - standing boys (blue pants, white shirts)
        for (i in 0 until studentCount) {
            val studentX = w * 0.12f + i * (w * 0.07f)
            val studentY = h * 0.65f
            // Head
            drawCircle(color = Color(0xFFFFCC80), radius = personRadius, center = Offset(studentX, studentY))
            // Shirt (White torso)
            drawRect(color = uniformWhite, topLeft = Offset(studentX - personRadius, studentY + personRadius), size = Size(personRadius * 2, personRadius * 2.5f))
            // Pants (Blue)
            drawRect(color = uniformBlue, topLeft = Offset(studentX - personRadius, studentY + personRadius * 3.5f), size = Size(personRadius * 2, personRadius * 1.5f))
        }

        // Row 2 (Middle) - standing girls (white & blue salwar)
        for (i in 0 until studentCount - 1) {
            val studentX = w * 0.15f + i * (w * 0.07f)
            val studentY = h * 0.70f
            // Head
            drawCircle(color = Color(0xFFFFCC80), radius = personRadius * 0.9f, center = Offset(studentX, studentY))
            // White Dupatta/Kameez
            drawRect(color = uniformWhite, topLeft = Offset(studentX - personRadius * 0.9f, studentY + personRadius * 0.9f), size = Size(personRadius * 1.8f, personRadius * 2.2f))
            // Blue Salwar
            drawRect(color = uniformBlue, topLeft = Offset(studentX - personRadius * 0.9f, studentY + personRadius * 3.1f), size = Size(personRadius * 1.8f, personRadius * 1.2f))
            // Green Lanyard
            drawRect(color = Color(0xFF4CAF50), topLeft = Offset(studentX - 2f, studentY + personRadius * 0.9f), size = Size(4f, personRadius * 1.8f))
        }

        // Row 1 (Front) - sitting students
        for (i in 0 until studentCount - 2) {
            val studentX = w * 0.18f + i * (w * 0.07f)
            val studentY = h * 0.76f
            // Head
            drawCircle(color = Color(0xFFFFCC80), radius = personRadius * 0.85f, center = Offset(studentX, studentY))
            // Shirt
            drawRect(color = if (i % 2 == 0) uniformWhite else uniformWhite, topLeft = Offset(studentX - personRadius * 0.85f, studentY + personRadius * 0.85f), size = Size(personRadius * 1.7f, personRadius * 1.8f))
            // Blue bottom folded
            drawRoundRect(color = uniformBlue, topLeft = Offset(studentX - personRadius * 0.85f, studentY + personRadius * 2.65f), size = Size(personRadius * 1.7f, personRadius * 1.2f), cornerRadius = CornerRadius(4f, 4f))
            // Lanyard
            drawRect(color = Color(0xFF4CAF50), topLeft = Offset(studentX - 2f, studentY + personRadius * 0.85f), size = Size(4f, personRadius * 1.5f))
        }
    }
}

@Composable
fun SchoolGateGraphic(modifier: Modifier = Modifier) {
    val skyColor = Color(0xFFE1F5FE)
    val redWall = Color(0xFFD32F2F)
    val yellowPillar = Color(0xFFFFC107)
    val gateIron = Color(0xFF212121)
    val foliageGreen = Color(0xFF2E7D32)

    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // 1. Sky
        drawRect(color = skyColor, size = size)

        // 2. Distant tree canopy (foliage)
        drawCircle(color = foliageGreen, radius = h * 0.35f, center = Offset(w * 0.15f, h * 0.35f))
        drawCircle(color = foliageGreen, radius = h * 0.45f, center = Offset(w * 0.85f, h * 0.35f))
        drawCircle(color = Color(0xFF1B5E20), radius = h * 0.3f, center = Offset(w * 0.5f, h * 0.3f))

        // 3. Left Red Wall structure
        drawRect(
            color = redWall,
            topLeft = Offset(0f, h * 0.25f),
            size = Size(w * 0.38f, h * 0.75f)
        )
        // Red wall branding sign base
        drawRect(
            color = Color(0xFF0D47A1),
            topLeft = Offset(w * 0.05f, h * 0.33f),
            size = Size(w * 0.28f, h * 0.25f)
        )

        // 4. Main gate Yellow Pillar beams
        // Left main pillar
        drawRect(
            color = yellowPillar,
            topLeft = Offset(w * 0.38f, h * 0.15f),
            size = Size(w * 0.15f, h * 0.85f)
        )
        // Upper cross JNV beam (holds the board name)
        drawRect(
            color = yellowPillar,
            topLeft = Offset(w * 0.38f, h * 0.15f),
            size = Size(w * 0.62f, h * 0.18f)
        )
        // Right Main Pillar (holds JNV Board)
        drawRect(
            color = yellowPillar,
            topLeft = Offset(w * 0.80f, h * 0.15f),
            size = Size(w * 0.1f, h * 0.85f)
        )

        // 5. Red Circle on Left yellow pillar
        drawCircle(
            color = redWall,
            radius = w * 0.035f,
            center = Offset(w * 0.455f, h * 0.24f)
        )

        // 6. Draw Iron gate at the bottom (between pillars)
        val gateLeft = w * 0.53f
        val gateRight = w * 0.80f
        val gateTop = h * 0.52f
        val gateBottom = h * 0.98f

        // Gate frame
        drawRect(
            color = gateIron,
            topLeft = Offset(gateLeft, gateTop),
            size = Size(gateRight - gateLeft, gateBottom - gateTop),
            style = Stroke(width = 4f)
        )

        // Gate grills vertical lines
        val grillCount = 10
        val grillGap = (gateRight - gateLeft) / grillCount
        for (i in 0..grillCount) {
            drawLine(
                color = gateIron,
                start = Offset(gateLeft + i * grillGap, gateTop),
                end = Offset(gateLeft + i * grillGap, gateBottom),
                strokeWidth = 3f
            )
        }
        // Gate horizontal middle bars
        drawLine(
            color = gateIron,
            start = Offset(gateLeft, gateTop + (gateBottom - gateTop) * 0.3f),
            end = Offset(gateRight, gateTop + (gateBottom - gateTop) * 0.3f),
            strokeWidth = 4f
        )
        drawLine(
            color = gateIron,
            start = Offset(gateLeft, gateTop + (gateBottom - gateTop) * 0.7f),
            end = Offset(gateRight, gateTop + (gateBottom - gateTop) * 0.7f),
            strokeWidth = 4f
        )

        // 7. Ground / Road
        drawRect(
            color = Color(0xFFA7FFEB), // cyan path representation
            topLeft = Offset(w * 0.38f, h * 0.92f),
            size = Size(w * 0.62f, h * 0.08f)
        )
        drawRect(
            color = Color(0xFF78909C), // Slate grey driveway
            topLeft = Offset(w * 0.38f, h * 0.95f),
            size = Size(w * 0.62f, h * 0.05f)
        )
    }

    // Text Overlay
    Box(
        modifier = modifier.padding(12.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.BottomStart)
        ) {
            Text(
                text = "PM SHRI JNV KOPPAL",
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .background(Color(0xFF0D47A1).copy(alpha = 0.8f), RoundedCornerShape(4.dp))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            )
        }
    }
}

@Composable
fun NavodayaLogoGraphic(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Draw soft grey circular background representing academic shield
        drawRect(color = Color(0xFFECEFF1), size = size)

        // Outer circular rings representing the logo medallion
        drawCircle(
            color = Color(0xFF0D47A1),
            radius = h * 0.35f,
            center = Offset(w * 0.5f, h * 0.45f),
            style = Stroke(width = 6f)
        )
        drawCircle(
            color = Color(0xFFFFB300),
            radius = h * 0.38f,
            center = Offset(w * 0.5f, h * 0.45f),
            style = Stroke(width = 4f)
        )

        // Draw Laurel leaves (representing wreaths at bottom)
        val pathLeft = Path().apply {
            moveTo(w * 0.15f, h * 0.55f)
            quadraticTo(w * 0.2f, h * 0.85f, w * 0.5f, h * 0.9f)
            quadraticTo(w * 0.35f, h * 0.82f, w * 0.22f, h * 0.62f)
            close()
        }
        drawPath(pathLeft, color = Color(0xFF2E7D32))

        val pathRight = Path().apply {
            moveTo(w * 0.85f, h * 0.55f)
            quadraticTo(w * 0.8f, h * 0.85f, w * 0.5f, h * 0.9f)
            quadraticTo(w * 0.65f, h * 0.82f, w * 0.78f, h * 0.62f)
            close()
        }
        drawPath(pathRight, color = Color(0xFF2E7D32))

        // Center stylized glowing sun / orange flame
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Color(0xFFFF9100), Color(0xFFFFCC80), Color.Transparent),
                center = Offset(w * 0.5f, h * 0.42f),
                radius = w * 0.15f
            ),
            radius = w * 0.15f,
            center = Offset(w * 0.5f, h * 0.42f)
        )

        // Draw Books (two pages stacked open)
        val bookPath = Path().apply {
            // Left page
            moveTo(w * 0.5f, h * 0.56f)
            lineTo(w * 0.32f, h * 0.5f)
            lineTo(w * 0.32f, h * 0.62f)
            lineTo(w * 0.5f, h * 0.68f)
            close()
            // Right page
            moveTo(w * 0.5f, h * 0.56f)
            lineTo(w * 0.68f, h * 0.5f)
            lineTo(w * 0.68f, h * 0.62f)
            lineTo(w * 0.5f, h * 0.68f)
            close()
        }
        drawPath(bookPath, color = Color.White)
        drawPath(bookPath, color = Color(0xFF0D47A1), style = Stroke(width = 3f))

        // Student figures (Circles for head, orange shapes for physical bodies learning)
        // Left student
        drawCircle(color = Color(0xFF0D47A1), radius = w * 0.03f, center = Offset(w * 0.42f, h * 0.35f))
        // Right student
        drawCircle(color = Color(0xFF0D47A1), radius = w * 0.03f, center = Offset(w * 0.58f, h * 0.35f))

        // Left body arc
        drawRoundRect(
            color = Color(0xFFD32F2F),
            topLeft = Offset(w * 0.36f, h * 0.39f),
            size = Size(w * 0.12f, h * 0.08f),
            cornerRadius = CornerRadius(10f, 10f)
        )
        // Right body arc
        drawRoundRect(
            color = Color(0xFFD32F2F),
            topLeft = Offset(w * 0.52f, h * 0.39f),
            size = Size(w * 0.12f, h * 0.08f),
            cornerRadius = CornerRadius(10f, 10f)
        )
    }

    Box(
        modifier = modifier.padding(8.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "नवोदय विद्यालय समिति",
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = JnvBlue,
                textAlign = TextAlign.Center
            )
            Text(
                text = "PRAGYANAM BRAHMA",
                fontSize = 8.sp,
                fontWeight = FontWeight.W500,
                color = JnvGold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun InstagramQrGraphic(modifier: Modifier = Modifier) {
    val igGrad = Brush.verticalGradient(
        colors = listOf(Color(0xFF3F51B5), Color(0xFFE91E63), Color(0xFFFFEB3B))
    )

    Box(
        modifier = modifier
            .background(igGrad)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(Color.White, RoundedCornerShape(16.dp))
                .padding(14.dp)
                .fillMaxWidth(0.9f)
        ) {
            Text(
                text = "INSTAGRAM QR",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0D47A1),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 6.dp)
            )

            // Simulated QR code vector lines
            Canvas(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color(0xFFF5F5F5), RoundedCornerShape(8.dp))
                    .padding(8.dp)
            ) {
                val sizeVal = size.width
                val s = sizeVal / 6

                // Drawer corners (QR locator markers)
                val qrMarkerColor = Color(0xFF3F51B5)
                
                // Top-Left locator
                drawRect(qrMarkerColor, topLeft = Offset(0f, 0f), size = Size(s * 2, s * 2))
                drawRect(Color.White, topLeft = Offset(s * 0.4f, s * 0.4f), size = Size(s * 1.2f, s * 1.2f))
                drawRect(qrMarkerColor, topLeft = Offset(s * 0.6f, s * 0.6f), size = Size(s * 0.8f, s * 0.8f))

                // Top-Right locator
                drawRect(qrMarkerColor, topLeft = Offset(sizeVal - s * 2, 0f), size = Size(s * 2, s * 2))
                drawRect(Color.White, topLeft = Offset(sizeVal - s * 1.6f, s * 0.4f), size = Size(s * 1.2f, s * 1.2f))
                drawRect(qrMarkerColor, topLeft = Offset(sizeVal - s * 1.4f, s * 0.6f), size = Size(s * 0.8f, s * 0.8f))

                // Bottom-Left locator
                drawRect(qrMarkerColor, topLeft = Offset(0f, sizeVal - s * 2), size = Size(s * 2, s * 2))
                drawRect(Color.White, topLeft = Offset(s * 0.4f, sizeVal - s * 1.6f), size = Size(s * 1.2f, s * 1.2f))
                drawRect(qrMarkerColor, topLeft = Offset(s * 0.6f, sizeVal - s * 1.4f), size = Size(s * 0.8f, s * 0.8f))

                // Center Instagram logo icon representation
                drawRoundRect(
                    color = Color(0xFFE91E63),
                    topLeft = Offset(sizeVal/2 - s*0.8f, sizeVal/2 - s*0.8f),
                    size = Size(s*1.6f, s*1.6f),
                    cornerRadius = CornerRadius(10f, 10f)
                )
                drawCircle(
                    color = Color.White,
                    radius = s * 0.4f,
                    center = Offset(sizeVal/2, sizeVal/2)
                )

                // Simulated QR dots
                drawCircle(qrMarkerColor, radius = s*0.2f, center = Offset(s*3, s))
                drawCircle(qrMarkerColor, radius = s*0.2f, center = Offset(s*4, s*1.5f))
                drawCircle(qrMarkerColor, radius = s*0.2f, center = Offset(s, s*3))
                drawCircle(qrMarkerColor, radius = s*0.2f, center = Offset(s*1.5f, s*4))
                drawCircle(qrMarkerColor, radius = s*0.2f, center = Offset(s*4.5f, s*3))
                drawCircle(qrMarkerColor, radius = s*0.2f, center = Offset(s*3, s*4.5f))
                drawCircle(qrMarkerColor, radius = s*0.2f, center = Offset(s*4, s*4))
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "@INTANGIBLES_36",
                fontSize = 14.sp,
                fontWeight = FontWeight.Black,
                color = Color(0xFF3F51B5),
                letterSpacing = 1.sp
            )
        }
    }
}

@Composable
fun WhatsappQrGraphic(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(Color(0xFF1E262C))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(Color(0xFF101920), RoundedCornerShape(16.dp))
                .padding(14.dp)
                .fillMaxWidth(0.9f)
        ) {
            Text(
                text = "XI SCIENCE 2026-27",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF25D366), // Whatsapp green
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 2.dp)
            )
            Text(
                text = "WhatsApp Group",
                fontSize = 9.sp,
                color = Color.LightGray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 6.dp)
            )

            // Simulated WhatsApp QR Code representation
            Canvas(
                modifier = Modifier
                    .size(90.dp)
                    .background(Color.White, RoundedCornerShape(8.dp))
                    .padding(8.dp)
            ) {
                val sizeVal = size.width
                val s = sizeVal / 6

                // QR locator markers
                val qrMarkerColor = Color(0xFF101920)
                
                // Top-Left locator
                drawRect(qrMarkerColor, topLeft = Offset(0f, 0f), size = Size(s * 1.8f, s * 1.8f))
                drawRect(Color.White, topLeft = Offset(s * 0.3f, s * 0.3f), size = Size(s * 1.2f, s * 1.2f))
                drawRect(qrMarkerColor, topLeft = Offset(s * 0.5f, s * 0.5f), size = Size(s * 0.8f, s * 0.8f))

                // Top-Right locator
                drawRect(qrMarkerColor, topLeft = Offset(sizeVal - s * 1.8f, 0f), size = Size(s * 1.8f, s * 1.8f))
                drawRect(Color.White, topLeft = Offset(sizeVal - s * 1.5f, s * 0.3f), size = Size(s * 1.2f, s * 1.2f))
                drawRect(qrMarkerColor, topLeft = Offset(sizeVal - s * 1.3f, s * 0.5f), size = Size(s * 0.8f, s * 0.8f))

                // Bottom-Left locator
                drawRect(qrMarkerColor, topLeft = Offset(0f, sizeVal - s * 1.8f), size = Size(s * 1.8f, s * 1.8f))
                drawRect(Color.White, topLeft = Offset(s * 0.3f, sizeVal - s * 1.5f), size = Size(s * 1.2f, s * 1.2f))
                drawRect(qrMarkerColor, topLeft = Offset(s * 0.5f, sizeVal - s * 1.3f), size = Size(s * 0.8f, s * 0.8f))

                // Center WhatsApp Green bubble
                drawCircle(
                    color = Color(0xFF25D366),
                    radius = s * 0.8f,
                    center = Offset(sizeVal/2, sizeVal/2)
                )

                // Simulating Phone handle inside green bubble
                drawCircle(
                    color = Color.White,
                    radius = s * 0.35f,
                    center = Offset(sizeVal/2, sizeVal/2)
                )

                // Simulated QR dots
                val dotColor = Color(0xFF212121)
                drawCircle(dotColor, radius = s*0.25f, center = Offset(s*3, s*1.2f))
                drawCircle(dotColor, radius = s*0.25f, center = Offset(s*4.5f, s*2))
                drawCircle(dotColor, radius = s*0.25f, center = Offset(s*1.2f, s*3))
                drawCircle(dotColor, radius = s*0.25f, center = Offset(s*2, s*4.2f))
                drawCircle(dotColor, radius = s*0.25f, center = Offset(s*4.2f, s*3.2f))
                drawCircle(dotColor, radius = s*0.25f, center = Offset(s*3.2f, s*4.5f))
            }

            Spacer(modifier = Modifier.height(10.dp))

            Icon(
                imageVector = Icons.Default.Group,
                contentDescription = "WhatsApp Group Link",
                tint = Color(0xFF25D366),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun CustomMemoryPlaceholder(category: String, modifier: Modifier = Modifier) {
    val gradColors = when (category) {
        "Campus" -> listOf(Color(0xFFE0F7FA), Color(0xFF80DEEA))
        "Intel" -> listOf(Color(0xFFEDE7F6), Color(0xFFB39DDB))
        "Fest" -> listOf(Color(0xFFFFF3E0), Color(0xFFFFCC80))
        else -> listOf(Color(0xFFECEFF1), Color(0xFFCFD8DC))
    }

    Box(
        modifier = modifier
            .background(Brush.verticalGradient(gradColors))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = when (category) {
                    "Campus" -> Icons.Default.School
                    "Intel" -> Icons.Default.MenuBook
                    "Fest" -> Icons.Default.EmojiEvents
                    else -> Icons.Default.PhotoCamera
                },
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Batch memory",
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
