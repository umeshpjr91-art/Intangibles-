package com.example.data

import kotlinx.coroutines.flow.Flow

class StudentRepository(private val studentDao: StudentDao) {
    val allStudents: Flow<List<Student>> = studentDao.getAllStudentsFlow()
    val loggedInStudent: Flow<Student?> = studentDao.getLoggedInStudentFlow()

    fun searchStudents(query: String): Flow<List<Student>> {
        val formatQuery = "%$query%"
        return studentDao.searchStudents(formatQuery)
    }

    suspend fun getLoggedInStudentDirect(): Student? {
        return studentDao.getLoggedInStudent()
    }

    suspend fun getStudentByEmail(email: String): Student? {
        return studentDao.getStudentByEmail(email)
    }

    suspend fun registerStudent(student: Student) {
        // Clear previous logged in flags
        studentDao.clearLoggedInStatus()
        
        // Check if student with this email already exists
        val existing = studentDao.getStudentByEmail(student.email)
        if (existing != null) {
            // Update details and set logged in
            val updated = existing.copy(
                name = student.name,
                stream = student.stream,
                phoneNumber = student.phoneNumber,
                instagramId = if (student.instagramId.isNotEmpty()) student.instagramId else existing.instagramId,
                isLoggedInUser = true
            )
            studentDao.updateStudent(updated)
        } else {
            // Register new student and set logged in
            val newStudent = student.copy(isLoggedInUser = true)
            studentDao.insertStudent(newStudent)
        }
    }

    suspend fun updateStudent(student: Student) {
        studentDao.updateStudent(student)
    }

    suspend fun updateInstagramId(id: Int, instagramId: String) {
        studentDao.updateInstagramId(id, instagramId)
    }

    suspend fun logout() {
        studentDao.clearLoggedInStatus()
    }
    
    suspend fun insertRawStudent(student: Student) {
        studentDao.insertStudent(student)
    }
}
