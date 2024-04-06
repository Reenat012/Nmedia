package ru.netology.nmedia.viewmodel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryMemoryInImpl

class PostViewModel : ViewModel() {
    private val repository: PostRepository = PostRepositoryMemoryInImpl()
    val data = repository.getAll()
    fun repost(id: Long) = repository.repost(id)
    fun likeById(id: Long) = repository.likeById(id)
}

