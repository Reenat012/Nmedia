package ru.netology.nmedia.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryMemoryInImpl

private val empty = Post(
    id = 0,
    content = "",
    author = "",
    likedByMe = false,
    published = ""
)

class PostViewModel : ViewModel() {
    private val repository: PostRepository = PostRepositoryMemoryInImpl()
    val data = repository.getAll()
    val edited = MutableLiveData(empty) //хранит состояние редактированного поста
    fun save() {
        //если в edited что то есть вызываем метод save у repository
        edited.value?.let {
            repository.save(it)
        }
        edited.value = empty
    }

    fun changeContent(text: String) {
        edited.value?.let {
            if (it.content != text.trim()) { //проверяем не равен ли существующий текст вновь введенному (trim - без учета пробелом)
                edited.value = it.copy(content = text)
            }
        }

    }

    fun repost(id: Long) = repository.repost(id)
    fun likeById(id: Long) = repository.likeById(id)
    fun removeById(id: Long) = repository.removeById(id)
}

