package com.example.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "direct_messages")
data class DirectMessage(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val senderName: String,
    val receiverName: String,
    val content: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Dao
interface DirectMessageDao {
    @Query("SELECT * FROM direct_messages WHERE (senderName = :user1 AND receiverName = :user2) OR (senderName = :user2 AND receiverName = :user1) ORDER BY timestamp ASC")
    fun getChatMessagesFlow(user1: String, user2: String): Flow<List<DirectMessage>>

    @Query("SELECT * FROM direct_messages ORDER BY timestamp DESC")
    fun getAllDirectMessagesFlow(): Flow<List<DirectMessage>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: DirectMessage): Long

    @Query("DELETE FROM direct_messages")
    suspend fun clearAllMessages()
}
