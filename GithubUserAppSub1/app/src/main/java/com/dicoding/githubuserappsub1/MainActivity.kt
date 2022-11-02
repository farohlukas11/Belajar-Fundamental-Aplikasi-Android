package com.dicoding.githubuserappsub1

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuserappsub1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val list = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvUser.setHasFixedSize(true)

        list.addAll(listUser)
        showRecyclerView()
    }

    private val listUser: ArrayList<User>
        get() {
            val dataPhoto = resources.obtainTypedArray(R.array.avatar)
            val dataName = resources.getStringArray(R.array.name)
            val dataUsername = resources.getStringArray(R.array.username)
            val dataFollower = resources.getStringArray(R.array.followers)
            val dataFollowing = resources.getStringArray(R.array.following)
            val dataRepository = resources.getStringArray(R.array.repository)
            val dataCompany = resources.getStringArray(R.array.company)
            val dataLocation = resources.getStringArray(R.array.location)
            val listUser = ArrayList<User>()

            for (i in dataName.indices) {
                val user = User(
                    photo = dataPhoto.getResourceId(i, -1),
                    name = dataName[i],
                    username = dataUsername[i],
                    follower = dataFollower[i],
                    following = dataFollowing[i],
                    repository = dataRepository[i],
                    company = dataCompany[i],
                    location = dataLocation[i]
                )
                listUser.add(user)
            }
            dataPhoto.recycle()

            return listUser
        }

    private fun showRecyclerView() {
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.rvUser.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding.rvUser.layoutManager = LinearLayoutManager(this)
        }
        val listUserAdapter = ListUserAdapter(list)
        binding.rvUser.adapter = listUserAdapter

        listUserAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(user: User) {
                val intentDetail = Intent(this@MainActivity, DetailActivity::class.java)
                intentDetail.putExtra(DetailActivity.EXTRA_USER, user)
                startActivity(intentDetail)
            }
        })
    }
}