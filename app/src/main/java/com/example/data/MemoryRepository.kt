package com.example.data

import kotlinx.coroutines.flow.Flow

class MemoryRepository(private val memoryDao: MemoryDao) {
    val allMemories: Flow<List<Memory>> = memoryDao.getAllMemoriesFlow()

    suspend fun insertMemory(memory: Memory): Long {
        return memoryDao.insertMemory(memory)
    }

    suspend fun deleteMemory(id: Int) {
        memoryDao.deleteMemory(id)
    }
}
