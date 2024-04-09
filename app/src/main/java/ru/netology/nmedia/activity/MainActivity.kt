package ru.netology.nmedia.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import ru.netology.nmedia.PostCardLayout
import ru.netology.nmedia.R
import ru.netology.nmedia.viewmodel.PostViewModel
import ru.netology.nmedia.R.drawable.like_svgrepo_com
import ru.netology.nmedia.R.drawable.like_svgrepo_com__1_
import ru.netology.nmedia.WallService
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        val adapter = PostAdapter(
            { viewModel.likeById(it.id) },
            { viewModel.repost(it.id) },
            { viewModel.removeById(it.id) })
        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts) //при каждом изменении данных мы список постов записываем обновленный список постов
        }
        binding.buttonSave.setOnClickListener {
            with(binding.content) {
                val content = binding.content.text.toString() //получаем введенный текст
                if (content.isBlank()) { //если поле пустое
                    Toast.makeText(this@MainActivity, R.string.empty, Toast.LENGTH_SHORT)
                        .show() //show - отображает окно
                    return@setOnClickListener //выходим из метода
                }

                viewModel.changeContent(content)
                viewModel.save()
            }
        }


    }
}



