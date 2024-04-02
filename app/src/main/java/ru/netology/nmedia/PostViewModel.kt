package ru.netology.nmedia

import androidx.lifecycle.ViewModel

class PostViewModel : ViewModel() {
    private val repository: PostRepository = PostRepositoryMemoryInImpl()
    val data = repository.get()
    fun repost() = repository.repost()
    fun like() = repository.like()
}

