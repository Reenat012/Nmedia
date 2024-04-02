package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.activity.viewModels
import ru.netology.nmedia.R.drawable.like_svgrepo_com
import ru.netology.nmedia.R.drawable.like_svgrepo_com__1_
import ru.netology.nmedia.databinding.ActivityMainBinding
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val service = WallService()

        val viewModel: PostViewModel by viewModels()
        viewModel.data.observe(this) { post ->
            with(binding) {
                tvAuthor.text = post.author
                tvPublished.text = post.published
                tvContent.text = post.content
                tvRepost.text = post.countReposts.toString()
                ivLikes.setImageResource(
                    if (post.likedByMe) like_svgrepo_com__1_ else like_svgrepo_com
                )
                tvLikes.text = service.amount(post.likes)
                tvRepost.text = service.amount(post.reposts)
            }
        }

        binding.ivLikes.setOnClickListener {
            viewModel.like()
        }

        binding.ivRepost.setOnClickListener {
            viewModel.repost()

        }
        //начальный вывод числа до клика
        /*tvLikes.text = service.amount(post.likes)
        tvRepost.text = service.amount(post.reposts)
        tvViews.text = service.amount(post.views)

        var counterLikes = post.likes

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
            //вывод числа лайков после клика
            tvLikes.text = service.amount(counterLikes)
        }

        var countReposts = post.reposts

        ivRepost.setOnClickListener {
            countReposts++
            tvRepost.text = service.amount(countReposts)
        }
        //вывод числа после клика
        tvViews.text = service.amount(post.views)*/
    }
}




