package com.dicoding.githubuserappsub1.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.githubuserappsub1.viewmodel.DetailViewModel
import com.dicoding.githubuserappsub1.R
import com.dicoding.githubuserappsub1.ViewModelFactory
import com.dicoding.githubuserappsub1.database.FavoritUser
import com.dicoding.githubuserappsub1.ui.adapter.SectionsPagerAdapter
import com.dicoding.githubuserappsub1.model.UserDetailResponse
import com.dicoding.githubuserappsub1.databinding.ActivityDetailBinding
import com.dicoding.githubuserappsub1.model.UserResponseItem
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var detailViewModel: DetailViewModel
    private lateinit var binding: ActivityDetailBinding
    private lateinit var bundle: Bundle
    private var userDetailData: UserResponseItem? = null
    private var mapUserFavorite: FavoritUser? = null

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

        userDetailData = intent.getParcelableExtra(EXTRA_USER)
        bundle = Bundle()

        detailViewModel = obtainViewModel(this@DetailActivity)

        var isChecked = false

        if (userDetailData != null) {
            userDetailData?.let { user ->
                mapUserFavorite = mappingItemUserToFavoriteUser(user)
                bundle.putParcelable(EXTRA_USER, user)
                detailViewModel.findUserDetail(user.login)
            }
        }

        if (mapUserFavorite != null) {
            mapUserFavorite?.let { favUser ->
                detailViewModel.checkUser(favUser.id).observe(this) {
                    if (it != null) {
                        if (it > 0) {
                            binding.tglFavorite.isChecked = true
                            isChecked = true
                        } else {
                            binding.tglFavorite.isChecked = false
                            isChecked = false
                        }
                    }
                }
            }
        }

        detailViewModel.user.observe(this) { userDetail ->
            setUserData(userDetail)
        }

        binding.tglFavorite.setOnClickListener {
            isChecked = !isChecked
            if (mapUserFavorite != null) {
                mapUserFavorite?.let { userFav ->
                    if (isChecked) {
                        detailViewModel.addFavoritUser(userFav)
                    } else {
                        detailViewModel.deleteFavoritUser(userFav.id)
                    }
                }
            }
            binding.tglFavorite.isChecked = isChecked
        }

        setTabLayout()

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun obtainViewModel(activity: DetailActivity): DetailViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[DetailViewModel::class.java]
    }

    private fun mappingItemUserToFavoriteUser(userResponseItem: UserResponseItem): FavoritUser {
        return FavoritUser(
            id = userResponseItem.id,
            login = userResponseItem.login,
            avatarUrl = userResponseItem.avatarUrl
        )
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
        const val EXTRA_USER = "extra_user"
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}