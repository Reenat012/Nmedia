package ru.netology.nmedia.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.constraintlayout.widget.Group
import ru.netology.nmedia.Post
import ru.netology.nmedia.PostCardLayout
import ru.netology.nmedia.R
import ru.netology.nmedia.viewmodel.PostViewModel
import ru.netology.nmedia.R.drawable.like_svgrepo_com
import ru.netology.nmedia.R.drawable.like_svgrepo_com__1_
import ru.netology.nmedia.WallService
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.databinding.ActivityNewPostBinding
import ru.netology.nmedia.databinding.ActivityPostCardLayoutBinding
import ru.netology.nmedia.util.AndroidUtils
import ru.netology.nmedia.util.focusAndShowKeyboard

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val group = findViewById<Group>(R.id.group) //теперь имеем возможность обращаться к группе элементов

        val viewModel: PostViewModel by viewModels()

        val newPostLauncher = registerForActivityResult(NewPostContract) {
            val result = it ?: return@registerForActivityResult
            viewModel.changeContentAndSave(result)
        }

        val adapter = PostAdapter(object : OnInteractionListener {
            override fun onEdit(post: Post) {
                viewModel.edit(post)
            }

            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
//                binding.content.setText("") //удаляем текст после добавления
//                AndroidUtils.hideKeyboard(binding.content) //убираем клавиатуру после добавления поста
                group.visibility = View.GONE
            }

            override fun onRepost(post: Post) {
                //всплывающее окно поделиться
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, post.content)
                }

                //делаем диалог более стилизованным(красивым)
                val shareIntent = Intent.createChooser(intent, getString(R.string.choser_share_post))
                startActivity(shareIntent)

                //подсчет количества репостов
                viewModel.repost(post.id)
            }
        })

        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            val newPost = posts.size > adapter.currentList.size
            adapter.submitList(posts) {
                if (newPost) {
                    binding.list.smoothScrollToPosition(0) //сверху сразу будет отображаться новый пост
                }
            } //при каждом изменении данных мы список постов записываем обновленный список постов
        }

        viewModel.edited.observe(this) {
            if (it.id != 0L) {
                group.visibility = View.VISIBLE //виджет редактирования  появляется на экране
                binding.tvCloneEditContent.setText(it.content) //устанавливаем в нижнее поле с текстом текст контента
                //передаем из одной активити в другую
                val intent = Intent(this@MainActivity, NewPostActivity::class.java)
                intent.putExtra("content", it.content)
                newPostLauncher.launch("content")
//                binding.content.setText(it.content) //устанавливаем в поле ввода текст редактированного поста
//                binding.content.focusAndShowKeyboard() //клавиатура будет появляться сама при редактировании поста
            }
        }

        binding.bottomSave.setOnClickListener {
            //обращаемся к контракту
            newPostLauncher.launch("")
        }

        binding.buttonCancelEdit.setOnClickListener {
            viewModel.cancelChangeContent() //функция отмены редактирования
//            binding.content.setText("") //удаляем текст после добавления
//            AndroidUtils.hideKeyboard(binding.content) //убираем клавиатуру после добавления поста
            group.visibility = View.GONE
        }
    }
}




