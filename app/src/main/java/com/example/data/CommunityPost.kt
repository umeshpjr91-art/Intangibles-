package com.example.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "community_posts")
data class CommunityPost(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val authorName: String,
    val content: String,
    val timestamp: Long = System.currentTimeMillis(),
    val likesCount: Int = 0,
    val hasLiked: Boolean = false,
    val mediaUri: String = "" // Optional image resource or pick path
)

@Dao
interface CommunityPostDao {
    @Query("SELECT * FROM community_posts ORDER BY timestamp DESC")
    fun getAllPostsFlow(): Flow<List<CommunityPost>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(post: CommunityPost): Long

    @Update
    suspend fun updatePost(post: CommunityPost)

    @Query("DELETE FROM community_posts WHERE id = :id")
    suspend fun deletePost(id: Int)
}
