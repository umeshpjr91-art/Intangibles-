package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "memories")
data class Memory(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val date: String,
    val category: String, // "Group", "Gate", "Logo", "Instagram", "WhatsApp", "Custom"
    val isPreset: Boolean = false,
    val customImageKey: String = "", // Used to select predefined visual representation or custom color themes
    val timestamp: Long = System.currentTimeMillis()
)
