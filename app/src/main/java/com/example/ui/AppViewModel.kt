package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class AppViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getDatabase(application)
    private val studentRepository = StudentRepository(database.studentDao())
    private val memoryRepository = MemoryRepository(database.memoryDao())
    private val communityPostDao = database.communityPostDao()
    private val directMessageDao = database.directMessageDao()

    // Community posts flow
    val communityPosts: StateFlow<List<CommunityPost>> = communityPostDao.getAllPostsFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Logged in user stream
    val loggedInStudent: StateFlow<Student?> = studentRepository.loggedInStudent
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    // All registered directory flow
    val students: StateFlow<List<Student>> = studentRepository.allStudents
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Memories flow
    val memories: StateFlow<List<Memory>> = memoryRepository.allMemories
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Search logic
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    val searchResults: StateFlow<List<Student>> = _searchQuery
        .debounce(300)
        .flatMapLatest { query ->
            if (query.isBlank()) {
                studentRepository.allStudents
            } else {
                studentRepository.searchStudents(query)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        viewModelScope.launch(Dispatchers.IO) {
            // Check and seed students if empty
            val currentStudents = studentRepository.allStudents.first()
            if (currentStudents.isEmpty()) {
                seedStudents()
            }
            
            // Check and seed memories if empty
            val currentMemories = memoryRepository.allMemories.first()
            if (currentMemories.isEmpty()) {
                seedMemories()
            }

            // Check and seed community posts if empty
            val currentPosts = communityPostDao.getAllPostsFlow().first()
            if (currentPosts.isEmpty()) {
                seedCommunityPosts()
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun loginOrRegister(name: String, stream: String, phone: String, email: String, instagramId: String = "", onSuccess: () -> Unit = {}) {
        viewModelScope.launch(Dispatchers.IO) {
            val student = Student(
                name = name.trim(),
                stream = stream.trim(),
                phoneNumber = phone.trim(),
                email = email.trim(),
                instagramId = instagramId.trim(),
                isLoggedInUser = true
            )
            studentRepository.registerStudent(student)
            launch(Dispatchers.Main) {
                onSuccess()
            }
        }
    }

    fun updateInstagramId(studentId: Int, instagramId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            studentRepository.updateInstagramId(studentId, instagramId.trim())
        }
    }

    fun updateMyDetails(id: Int, name: String, stream: String, phone: String, email: String, instagramId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val updated = Student(
                id = id,
                name = name.trim(),
                stream = stream.trim(),
                phoneNumber = phone.trim(),
                email = email.trim(),
                instagramId = instagramId.trim(),
                isLoggedInUser = true
            )
            studentRepository.updateStudent(updated)
        }
    }

    fun addMemory(title: String, description: String, category: String, customImageKey: String = "custom_memory_brush") {
        viewModelScope.launch(Dispatchers.IO) {
            val memory = Memory(
                title = title.trim(),
                description = description.trim(),
                date = "Added now",
                category = category,
                isPreset = false,
                customImageKey = customImageKey
            )
            memoryRepository.insertMemory(memory)
        }
    }

    fun deleteMemory(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            memoryRepository.deleteMemory(id)
        }
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            studentRepository.logout()
        }
    }

    private suspend fun seedStudents() {
        val list = listOf(
            Student(
                name = "Umesh Poojar",
                stream = "Science",
                phoneNumber = "9876543210",
                email = "umeshpoojar6919@gmail.com",
                instagramId = "umesh_poojar",
                isLoggedInUser = false
            ),
            Student(
                name = "Sudhakar S",
                stream = "Science",
                phoneNumber = "9123456789",
                email = "sudhakar.s@example.com",
                instagramId = "sudhakar_s_clicks",
                isLoggedInUser = false
            ),
            Student(
                name = "Basavaraj M",
                stream = "Arts",
                phoneNumber = "8765432109",
                email = "basava.m@example.com",
                instagramId = "basava_intangibles",
                isLoggedInUser = false
            ),
            Student(
                name = "Priyanka G",
                stream = "Arts",
                phoneNumber = "7654321098",
                email = "priyanka.g@example.com",
                instagramId = "priya_g_music",
                isLoggedInUser = false
            ),
            Student(
                name = "Shruthi K",
                stream = "Science",
                phoneNumber = "9845123476",
                email = "shruthi.k@example.com",
                instagramId = "shruthi_k_art",
                isLoggedInUser = false
            )
        )
        for (std in list) {
            studentRepository.insertRawStudent(std)
        }
    }

    private suspend fun seedMemories() {
        val list = listOf(
            Memory(
                title = "Batch 2021-26 Group Photo",
                description = "The official group photo of the Intangibles batch of PM SHRI JNV Koppal, sitting in uniform in front of the school's red brick buildings. Representing unity, memories, and 5 years of together path.",
                date = "April 2026",
                category = "Group",
                isPreset = true,
                customImageKey = "group_photo"
            ),
            Memory(
                title = "PM SHRI JNV Koppal Entrance",
                description = "The magnificent yellow and red entrance gate arch of Jawahar Navodaya Vidyalaya, Kuknoor, Koppal District (Karnataka). Opening the doorway to a world of absolute growth and lifelong values.",
                date = "June 2021",
                category = "Gate",
                isPreset = true,
                customImageKey = "school_gate"
            ),
            Memory(
                title = "Navodaya Vidyalaya Samiti Pride",
                description = "The corporate symbol representing the national family of Jawahar Navodaya Vidyalayas, centered with books of knowledge, with the motto 'Pragyanam Brahma'.",
                date = "Established 1986",
                category = "Logo",
                isPreset = true,
                customImageKey = "navodaya_logo"
            ),
            Memory(
                title = "XI Science Class Study Circle",
                description = "The official WhatsApp community and notes distribution QR code labeled 'XI SCIENCE-2026-27'. Connecting classmates for assignments, discussions, and exam tips.",
                date = "November 2026",
                category = "WhatsApp",
                isPreset = true,
                customImageKey = "whatsapp_qr"
            ),
            Memory(
                title = "Our Instagram Hub: @INTANGIBLES_36",
                description = "Scan this Instagram badge to follow our official batch page and group chat handle @INTANGIBLES_36 on Instagram. We keep editing and posting batch memories!",
                date = "March 2026",
                category = "Instagram",
                isPreset = true,
                customImageKey = "instagram_qr"
            )
        )
        for (mem in list) {
            memoryRepository.insertMemory(mem)
        }
    }

    private suspend fun seedCommunityPosts() {
        val posts = listOf(
            CommunityPost(
                authorName = "Sudhakar S",
                content = "What's up science mates! 🔬 Just catalogued our latest project on physics electromagnetic induction under the notes search. Let me know if you need any corrections!",
                likesCount = 8,
                hasLiked = false
            ),
            CommunityPost(
                authorName = "Priyanka G",
                content = "Hey everyone! Don't forget, we have the XI Science batch photography exhibition meet tomorrow near the PM SHRI JNV entrance arch! Do register your portal handles and update your Instagram info here. 📸",
                likesCount = 12,
                hasLiked = false
            ),
            CommunityPost(
                authorName = "Basavaraj M",
                content = "Our 36th batch Intangibles pride is unmatched! 🎓 Let's keep adding our vlogs, class pictures, and video recordings to the Batch Memories section. Cheers to the memories we are building together!",
                likesCount = 15,
                hasLiked = false
            )
        )
        for (post in posts) {
            communityPostDao.insertPost(post)
        }
    }

    fun addCommunityPost(content: String, mediaUri: String = "") {
        viewModelScope.launch(Dispatchers.IO) {
            val author = loggedInStudent.value?.name ?: "Umesh Poojar"
            communityPostDao.insertPost(
                CommunityPost(
                    authorName = author,
                    content = content,
                    mediaUri = mediaUri
                )
            )
        }
    }

    fun toggleLikePost(post: CommunityPost) {
        viewModelScope.launch(Dispatchers.IO) {
            val updated = post.copy(
                likesCount = if (post.hasLiked) post.likesCount - 1 else post.likesCount + 1,
                hasLiked = !post.hasLiked
            )
            communityPostDao.updatePost(updated)
        }
    }

    fun getDirectMessages(otherUser: String): Flow<List<DirectMessage>> {
        val currentUser = loggedInStudent.value?.name ?: "Umesh Poojar"
        return directMessageDao.getChatMessagesFlow(currentUser, otherUser)
    }

    fun sendDirectMessage(receiverName: String, content: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val sender = loggedInStudent.value?.name ?: "Umesh Poojar"
            // Insert user's message
            directMessageDao.insertMessage(
                DirectMessage(senderName = sender, receiverName = receiverName, content = content)
            )

            // Dynamic interactive response generator
            val replies = listOf(
                "Ayy buddy! What's up? Are we meeting up for the XI Science batch group study at the library?",
                "That sounds so great! Intangibles Batch 36 is truly unforgettable.",
                "Hey! I am editing some batch memories right now. Did you see the new custom photo/video uploads?",
                "Haha indeed! Jawahar Navodaya Vidyalaya Koppal days are the absolute best.",
                "Let's chat more on our in-app Intangibles Community board too! I just posted an update.",
                "Got your message! I'm sharing the class study materials today. Check them out under notes!"
            )
            val randomReply = replies.random()

            kotlinx.coroutines.delay(1200) // Realistic simulated typing/delivering time
            directMessageDao.insertMessage(
                DirectMessage(senderName = receiverName, receiverName = sender, content = randomReply)
            )
        }
    }
}
