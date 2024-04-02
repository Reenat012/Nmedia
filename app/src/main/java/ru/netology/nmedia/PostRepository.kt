package ru.netology.nmedia

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface PostRepository {
    fun get(): LiveData<Post>
    fun like()
    fun repost()
}

class PostRepositoryMemoryInImpl : PostRepository {
    private var post = Post(
        id = 1,
        author = "Нетология. Университет интернет-профессий будущего",
        content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
        published = "21 мая в 18:36",
        likedByMe = false,
        likes = 999,
        reposts = 999,
        views = 3_123_123
    )
    private val data = MutableLiveData(post)
    private var countLikes = post.likes
    private var countReposts = post.reposts
    override fun get(): LiveData<Post> = data
    override fun like() {
        post = post.copy(
            likedByMe = !post.likedByMe,
            likes = if (!post.likedByMe) countLikes++ else countLikes--
        )
        data.value = post.copy()
    }
    override fun repost() {
        countReposts++
        post = post.copy(reposts = countReposts)
        data.value = post
    }
}