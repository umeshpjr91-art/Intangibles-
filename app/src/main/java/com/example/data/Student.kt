package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class Student(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val stream: String,
    val phoneNumber: String,
    val email: String,
    val instagramId: String = "",
    val isLoggedInUser: Boolean = false,
    val registrationTime: Long = System.currentTimeMillis()
)
