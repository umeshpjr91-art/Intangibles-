package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Collections
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.Student
import com.example.ui.AppViewModel
import com.example.ui.theme.JnvBlue
import com.example.ui.theme.JnvGold
import com.example.ui.theme.JnvSky

sealed class BottomTab(
    val route: String,
    val title: String,
    val selectedIcon: androidx.compose.ui.graphics.vector.ImageVector,
    val unselectedIcon: androidx.compose.ui.graphics.vector.ImageVector
) {
    object Home : BottomTab("home", "Home", Icons.Default.Home, Icons.Outlined.Home)
    object Search : BottomTab("search", "Search", Icons.Default.Search, Icons.Outlined.Search)
    object Album : BottomTab("album", "Album", Icons.Default.Collections, Icons.Outlined.Collections)
    object Account : BottomTab("account", "Account", Icons.Default.AccountCircle, Icons.Outlined.AccountCircle)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppContainer(
    viewModel: AppViewModel,
    loggedInStudent: Student?,
    students: List<Student>,
    searchResults: List<Student>,
    searchQuery: String,
    memories: List<com.example.data.Memory>
) {
    var curTab by remember { mutableStateOf<BottomTab>(BottomTab.Home) }
    var showMessenger by remember { mutableStateOf(false) }
    var showCommunity by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            Surface(
                tonalElevation = 6.dp,
                shadowElevation = 4.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(JnvBlue, JnvBlue.copy(alpha = 0.9f))
                            )
                        )
                        .windowInsetsPadding(WindowInsets.statusBars)
                        .padding(vertical = 12.dp, horizontal = 16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Wrap header and chat trigger in Box for right-corner absolute alignment
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            // Big center custom font header info
                            Text(
                                text = "INTANGIBLES",
                                color = JnvGold,
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Black,
                                letterSpacing = 3.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.testTag("app_brand_title")
                            )
                            
                            Text(
                                text = "PM SHRI JNV KOPPAL (2021-26)",
                                color = Color.White.copy(alpha = 0.85f),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp,
                                textAlign = TextAlign.Center
                            )
                        }

                        // Right-aligned chat button in the above right corner
                        Box(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(end = 4.dp)
                        ) {
                            IconButton(
                                onClick = { showMessenger = true },
                                modifier = Modifier.testTag("messenger_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Email,
                                    contentDescription = "Messenger",
                                    tint = JnvGold,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                        }
                    }
                }
            }
        },
        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .testTag("app_bottom_nav"),
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                tonalElevation = 8.dp
            ) {
                val tabs = listOf(BottomTab.Home, BottomTab.Search, BottomTab.Album, BottomTab.Account)
                tabs.forEach { tab ->
                    val isSelected = curTab == tab
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { curTab = tab },
                        label = { Text(tab.title, fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
                        icon = {
                            Icon(
                                imageVector = if (isSelected) tab.selectedIcon else tab.unselectedIcon,
                                contentDescription = tab.title,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = JnvBlue,
                            selectedTextColor = JnvBlue,
                            indicatorColor = JnvGold.copy(alpha = 0.3f),
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }
        },
        contentWindowInsets = WindowInsets.navigationBars
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (showMessenger) {
                MessengerScreen(
                    viewModel = viewModel,
                    students = students,
                    senderStudent = loggedInStudent,
                    onBack = { showMessenger = false }
                )
            } else if (showCommunity) {
                CommunityScreen(
                    viewModel = viewModel,
                    loggedInStudent = loggedInStudent,
                    onBack = { showCommunity = false }
                )
            } else {
                when (curTab) {
                    BottomTab.Home -> HomeScreen(
                        viewModel = viewModel,
                        students = students,
                        onOpenCommunity = { showCommunity = true }
                    )
                    BottomTab.Search -> SearchScreen(
                        viewModel = viewModel,
                        searchResults = searchResults,
                        searchQuery = searchQuery
                    )
                    BottomTab.Album -> AlbumScreen(
                        viewModel = viewModel,
                        memories = memories
                    )
                    BottomTab.Account -> AccountScreen(
                        viewModel = viewModel,
                        student = loggedInStudent
                    )
                }
            }
        }
    }
}
