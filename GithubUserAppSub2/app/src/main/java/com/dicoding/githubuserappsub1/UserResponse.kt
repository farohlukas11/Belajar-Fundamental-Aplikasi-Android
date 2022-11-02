package com.dicoding.githubuserappsub1

import com.google.gson.annotations.SerializedName

data class UserResponse(
	@field:SerializedName("items")
	val items: List<UserResponseItem>
)

data class UserResponseItem(
	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("id")
	val id: Int,
)