package com.example.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MemoryDao {
    @Query("SELECT * FROM memories ORDER BY isPreset DESC, timestamp DESC")
    fun getAllMemoriesFlow(): Flow<List<Memory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMemory(memory: Memory): Long

    @Query("DELETE FROM memories WHERE id = :id")
    suspend fun deleteMemory(id: Int)
}
