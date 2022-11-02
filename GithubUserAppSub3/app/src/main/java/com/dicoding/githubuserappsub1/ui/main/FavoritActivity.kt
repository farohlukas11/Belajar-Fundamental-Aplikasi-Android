package com.dicoding.githubuserappsub1.ui.main

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuserappsub1.R
import com.dicoding.githubuserappsub1.ViewModelFactory
import com.dicoding.githubuserappsub1.database.FavoritUser
import com.dicoding.githubuserappsub1.databinding.ActivityFavoritBinding
import com.dicoding.githubuserappsub1.model.UserResponseItem
import com.dicoding.githubuserappsub1.ui.adapter.ListUserAdapter
import com.dicoding.githubuserappsub1.viewmodel.FavoritViewModel

class FavoritActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoritBinding
    private lateinit var favoriteViewModel: FavoritViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar?.let {
            it.title = getString(R.string.favorit_activity)
            it.setDisplayHomeAsUpEnabled(true)
        }

        favoriteViewModel = obtainViewModel(this@FavoritActivity)

        favoriteViewModel.mediator.observe(this, {})

        favoriteViewModel.listUserFavorite.observe(this) { favUser ->
            favUser?.let {
                val mapFavorite = mappingFavoriteUserToItemUser(it)
                showRecyclerView(mapFavorite)
            }
        }

        favoriteViewModel.isLoading.observe(this) {
            showLoading(it)
        }

    }

    private fun obtainViewModel(activity: FavoritActivity): FavoritViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoritViewModel::class.java]
    }

    private fun mappingFavoriteUserToItemUser(favoritUser: List<FavoritUser>? = null): ArrayList<UserResponseItem> {
        val listUser = ArrayList<UserResponseItem>()
        favoritUser.let { favUser ->
            if (favUser != null) {
                for (i in favUser) {
                    val userItem = UserResponseItem(
                        id = i.id,
                        login = i.login.toString(),
                        avatarUrl = i.avatarUrl.toString(),
                    )
                    listUser.add(userItem)
                }
            }
        }
        return listUser
    }


    private fun showRecyclerView(list: List<UserResponseItem>) {
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.rvUserFavorit.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding.rvUserFavorit.layoutManager = LinearLayoutManager(this)
        }
        val listUser = ArrayList<UserResponseItem>()
        for (user in list) {
            listUser.add(user)
        }
        val listUserAdapter = ListUserAdapter(listUser, this)
        binding.rvUserFavorit.adapter = listUserAdapter

        listUserAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(user: UserResponseItem) {
                val intentDetail = Intent(this@FavoritActivity, DetailActivity::class.java)
                intentDetail.putExtra(DetailActivity.EXTRA_USER, user)
                startActivity(intentDetail)
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
        }
    }
}