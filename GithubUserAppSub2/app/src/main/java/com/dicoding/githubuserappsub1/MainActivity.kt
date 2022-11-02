package com.dicoding.githubuserappsub1

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuserappsub1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.listUser.observe(this, { list ->
            showRecyclerView(list)
        })

        mainViewModel.isLoading.observe(this, {
            showLoading(it)
        })

        showSearchView()
    }

    private fun showSearchView() {
        binding.btnSearch.setOnClickListener {
            searchUser()
        }

        binding.edUser.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                searchUser()
                return@setOnKeyListener true
            } else {
                return@setOnKeyListener false
            }
        }
        mainViewModel.listUser.observe(this, {
            if (it != null) {
                showRecyclerView(it)
                showLoading(false)
            }
        })
    }

    private fun searchUser(){
        val usernameQuery = binding.edUser.text.toString()
        if (usernameQuery.isEmpty()) return
        showLoading(true)
        mainViewModel.findUserSearch(usernameQuery)
    }

    private fun showRecyclerView(list: List<UserResponseItem>) {
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.rvUser.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding.rvUser.layoutManager = LinearLayoutManager(this)
        }
        val listUser = ArrayList<UserResponseItem>()
        for (user in list) {
            listUser.add(user)
        }
        val listUserAdapter = ListUserAdapter(listUser, this)
        binding.rvUser.adapter = listUserAdapter

        listUserAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(user: UserResponseItem) {
                val intentDetail = Intent(this@MainActivity, DetailActivity::class.java)
                intentDetail.putExtra(DetailActivity.EXTRA_LOGIN, user.login)
                startActivity(intentDetail)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
        }
    }
}