package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import ru.netology.nmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "21 мая в 18:36",
            likedByMe = false
        )
        with(binding){
            tvAuthor.text = post.author
            tvPublished.text = post.published
            tvContent.text= post.content
            if (post.likedByMe) {
                ivLikes?.setImageResource(R.drawable.like_svgrepo_com__1_)
            }

            var counterLikes = 0

            ivLikes.setOnClickListener{
                post.likedByMe = !post.likedByMe //меняем false на true
                ivLikes.setImageResource(
                    if (post.likedByMe) {
                        R.drawable.like_svgrepo_com__1_
                        counterLikes++
                    } else {
                        R.drawable.like_svgrepo_com
                        counterLikes--
                    }
                )
                tvLikes.text = counterLikes.toString()
            }
        }
    }
}

data class Post(
    val id: Int,
    val author: String,
    val content: String,
    val published: String,
    var likedByMe: Boolean = false
)