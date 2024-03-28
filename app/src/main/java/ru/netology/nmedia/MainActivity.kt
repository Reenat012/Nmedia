package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import ru.netology.nmedia.R.drawable.like_svgrepo_com
import ru.netology.nmedia.R.drawable.like_svgrepo_com__1_
import ru.netology.nmedia.databinding.ActivityMainBinding
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.roundToInt

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
            likedByMe = false,
            likes = 3_100_200,
        )

        val service = WallService()

        with(binding) {
            tvAuthor.text = post.author
            tvPublished.text = post.published
            tvContent.text = post.content
            tvRepost.text = post.countReposts.toString()
            if (post.likedByMe) {
                ivLikes?.setImageResource(like_svgrepo_com__1_)
            }
            tvLikes.text = post.likes.toString()
            tvRepost.text = post.reposts.toString()
            tvViews.text = post.views.toString()

            var counterLikes = 0

            ivLikes?.setOnClickListener {
                post.likedByMe = !post.likedByMe
                ivLikes.setImageResource(
                    if (post.likedByMe) like_svgrepo_com__1_ else like_svgrepo_com
                )
                if (post.likedByMe) {
                    counterLikes++
                } else {
                    if (counterLikes > 0) counterLikes--
                }
                tvLikes.text = counterLikes.toString()
            }

            var countReposts = 0

            ivRepost.setOnClickListener {
                countReposts++
                tvRepost.text = countReposts.toString()
            }
            tvLikes.text = service.amount(post.likes)
        }
    }
}

data class Post(
    val id: Int,
    val author: String,
    val content: String,
    val published: String,
    var likedByMe: Boolean = false,
    var likes: Int = 0,
    var reposts: Int = 0,
    var views: Int = 0,
    var countReposts: Int = 0
)

class WallService {
    fun amount(likes: Int): String {
        val df = DecimalFormat("#.#") //функция для ограничения двойного числа до 2-х десятичных точек с использованием шаблона #.##
        df.roundingMode = RoundingMode.DOWN

        return when (likes) {
            in 0..999 -> "$likes"
            in 1_000..9_999 -> "${df.format(likes / 1_000.0)}K"
            in 10_000..999_999 -> "${(likes / 10_000)}K"
            in 1_000_000..999_999_999 -> "${df.format(likes / 1_000_000.0)}M"
            else -> "Бесконечность"
        }
    }
}