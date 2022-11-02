package com.dicoding.githubuserappsub1

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var photo: Int,
    var name: String,
    var username: String,
    var following: String,
    var follower: String,
    var repository: String,
    var company: String,
    var location: String
) : Parcelable
