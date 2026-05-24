package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.AppViewModel
import com.example.ui.screens.LoginScreen
import com.example.ui.screens.MainAppContainer
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                AppPortal()
            }
        }
    }
}

@Composable
fun AppPortal() {
    val viewModel: AppViewModel = viewModel()
    
    // Core state flows from our reactive Room DB
    val loggedInStudent by viewModel.loggedInStudent.collectAsStateWithLifecycle()
    val students by viewModel.students.collectAsStateWithLifecycle()
    val searchResults by viewModel.searchResults.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val memories by viewModel.memories.collectAsStateWithLifecycle()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        // Direct state routing depending on active registration
        val currentStudent = loggedInStudent
        if (currentStudent == null) {
            LoginScreen(
                viewModel = viewModel,
                onLoginSuccess = {
                    // Handled automatically via Flow emissions in VM
                }
            )
        } else {
            MainAppContainer(
                viewModel = viewModel,
                loggedInStudent = currentStudent,
                students = students,
                searchResults = searchResults,
                searchQuery = searchQuery,
                memories = memories
            )
        }
    }
}
