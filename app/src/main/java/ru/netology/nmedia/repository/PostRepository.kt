package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import ru.netology.nmedia.Post

interface PostRepository {
    fun repost(id: Long)
    fun getAll(): LiveData<List<Post>>
    fun likeById(id: Long)
}

