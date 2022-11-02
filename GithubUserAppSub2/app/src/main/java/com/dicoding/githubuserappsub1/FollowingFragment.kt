package com.dicoding.githubuserappsub1

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
import com.dicoding.githubuserappsub1.databinding.FragmentFollowingBinding

class FollowingFragment : Fragment() {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private val followingViewModel: FollowingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val usernameUser = arguments?.getString(DetailActivity.EXTRA_LOGIN).toString()

        followingViewModel.findUserFollowing(usernameUser)

        followingViewModel.lisUserFollowing.observe(requireActivity()) { listUserFollowing ->
            showRecyclerView(listUserFollowing)
        }

        followingViewModel.isLoading.observe(requireActivity()) {
            showLoading(it)
        }
    }

    private fun showRecyclerView(listUserFollowing: List<UserResponseItem>) {
        if (requireActivity().resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.rvUserFollowing.layoutManager = GridLayoutManager(requireActivity(), 2)
        } else {
            binding.rvUserFollowing.layoutManager = LinearLayoutManager(requireContext())
        }
        val listFollowing = ArrayList<UserResponseItem>()
        for (following in listUserFollowing) {
            listFollowing.add(following)
        }
        val listFollowingAdapter = ListUserAdapter(listFollowing, requireActivity())
        binding.rvUserFollowing.adapter = listFollowingAdapter

        listFollowingAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback{
            override fun onItemClicked(user: UserResponseItem) {
                val intentDetail = Intent(this@FollowingFragment.context, DetailActivity::class.java)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}