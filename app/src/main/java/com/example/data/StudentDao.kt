package com.example.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentDao {
    @Query("SELECT * FROM students ORDER BY name ASC")
    fun getAllStudentsFlow(): Flow<List<Student>>

    @Query("SELECT * FROM students WHERE name LIKE :searchQuery OR stream LIKE :searchQuery ORDER BY name ASC")
    fun searchStudents(searchQuery: String): Flow<List<Student>>

    @Query("SELECT * FROM students WHERE isLoggedInUser = 1 LIMIT 1")
    fun getLoggedInStudentFlow(): Flow<Student?>

    @Query("SELECT * FROM students WHERE isLoggedInUser = 1 LIMIT 1")
    suspend fun getLoggedInStudent(): Student?

    @Query("SELECT * FROM students WHERE email = :email LIMIT 1")
    suspend fun getStudentByEmail(email: String): Student?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudent(student: Student): Long

    @Update
    suspend fun updateStudent(student: Student)

    @Query("UPDATE students SET instagramId = :instagramId WHERE id = :id")
    suspend fun updateInstagramId(id: Int, instagramId: String)

    @Query("UPDATE students SET isLoggedInUser = 0")
    suspend fun clearLoggedInStatus()
}
