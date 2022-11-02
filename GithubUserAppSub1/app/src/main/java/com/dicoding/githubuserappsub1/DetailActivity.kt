package com.dicoding.githubuserappsub1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.githubuserappsub1.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar?.let {
            it.title = getString(R.string.detail_activity)
            it.setDisplayHomeAsUpEnabled(true)
        }

        val dataIntent = intent.getParcelableExtra<User>(EXTRA_USER) as User

        binding.apply {
            ivPhotoDetail.setImageResource(dataIntent.photo)
            tvFollowersDetail.text = dataIntent.follower
            tvFollowingDetail.text = dataIntent.following
            tvRepositoryDetail.text = dataIntent.repository
            tvNameDetail.text = dataIntent.name
            tvUsernameDetail.text = dataIntent.username
            tvCompanyDetail.text = dataIntent.company
            tvLocationDetail.text = dataIntent.location
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    companion object {
        const val EXTRA_USER = "extra_user"
    }
}