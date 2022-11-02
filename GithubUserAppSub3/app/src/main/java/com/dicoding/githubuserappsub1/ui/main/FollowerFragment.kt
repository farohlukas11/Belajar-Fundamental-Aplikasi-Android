package com.dicoding.githubuserappsub1.ui.main

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuserappsub1.viewmodel.FollowerViewModel
import com.dicoding.githubuserappsub1.ui.adapter.ListUserAdapter
import com.dicoding.githubuserappsub1.model.UserResponseItem
import com.dicoding.githubuserappsub1.databinding.FragmentFollowerBinding
import java.util.ArrayList

class FollowerFragment : Fragment() {

    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!
    private val followerViewModel: FollowerViewModel by viewModels()
    private var userFollower: UserResponseItem? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFollowerBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userFollower = arguments?.getParcelable(DetailActivity.EXTRA_USER)

        if (userFollower != null) {
            userFollower?.let {
                followerViewModel.findUserFollower(it.login)
            }
        }

        followerViewModel.listUserFollower.observe(requireActivity()) { listUserFollower ->
            showRecyclerView(listUserFollower)
        }

        followerViewModel.isLoading.observe(requireActivity()) {
            showLoading(it)
        }
    }

    private fun showRecyclerView(listUserFollower: List<UserResponseItem>) {
        if (requireActivity().resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.rvUserFollower.layoutManager = GridLayoutManager(requireActivity(), 2)
        } else {
            binding.rvUserFollower.layoutManager = LinearLayoutManager(requireContext())
        }
        val listFollower = ArrayList<UserResponseItem>()
        for (follower in listUserFollower) {
            listFollower.add(follower)
        }
        val listFollowerAdapter = ListUserAdapter(listFollower, requireActivity())
        binding.rvUserFollower.adapter = listFollowerAdapter

        listFollowerAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(user: UserResponseItem) {
                val intentDetail = Intent(this@FollowerFragment.context, DetailActivity::class.java)
                intentDetail.putExtra(DetailActivity.EXTRA_USER, user)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}