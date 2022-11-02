package com.dicoding.githubuserappsub1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.githubuserappsub1.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private val detailViewModel: DetailViewModel by viewModels()
    private lateinit var binding: ActivityDetailBinding
    private lateinit var bundle: Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar?.let {
            it.title = getString(R.string.detail_activity)
            it.setDisplayHomeAsUpEnabled(true)
        }

//        val dataIntent = intent.getParcelableExtra<User>(EXTRA_USER) as User
//
//        binding.apply {
//            ivPhotoDetail.setImageResource(dataIntent.photo)
//            tvFollowersDetail.text = dataIntent.follower
//            tvFollowingDetail.text = dataIntent.following
//            tvRepositoryDetail.text = dataIntent.repository
//            tvNameDetail.text = dataIntent.name
//            tvUsernameDetail.text = dataIntent.username
//            tvCompanyDetail.text = dataIntent.company
//            tvLocationDetail.text = dataIntent.location
//        }

        val userDetailData = intent.getStringExtra(EXTRA_LOGIN) as String
        bundle = Bundle()
        bundle.putString(EXTRA_LOGIN, userDetailData)

        detailViewModel.findUserDetail(userDetailData)

        detailViewModel.user.observe(this) { userDetail ->
            setUserData(userDetail)
        }

        setTabLayout()

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun setTabLayout() {
        val sectionsPagerAdapter = SectionsPagerAdapter(this, bundle)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun setUserData(userDetail: UserDetailResponse) {
        Glide.with(this)
            .load(userDetail.avatarUrl)
            .into(binding.ivPhotoDetail)
        binding.apply {
            tvNameDetail.text = userDetail.name
            tvUsernameDetail.text = userDetail.login
            tvCompanyDetail.text = userDetail.company
            tvLocationDetail.text = userDetail.location
        }

    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    companion object {
        const val EXTRA_LOGIN = "extra_username"
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}