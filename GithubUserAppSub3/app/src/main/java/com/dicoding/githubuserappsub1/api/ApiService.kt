package com.dicoding.githubuserappsub1.api

import com.dicoding.githubuserappsub1.BuildConfig
import com.dicoding.githubuserappsub1.model.UserDetailResponse
import com.dicoding.githubuserappsub1.model.UserResponse
import com.dicoding.githubuserappsub1.model.UserResponseItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    @Headers("Authorization: token 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")
    fun getUsers(): Call<List<UserResponseItem>>

    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getUsersSearch(
        @Query("q") username: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUsersDetail(
        @Path("username") username: String
    ): Call<UserDetailResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getUsersFollower(
        @Path("username") username: String
    ): Call<List<UserResponseItem>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getUsersFollowing(
        @Path("username") username: String
    ): Call<List<UserResponseItem>>


}