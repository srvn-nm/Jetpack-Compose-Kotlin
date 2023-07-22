package com.example.paginglearning.model

data class UserResponse(
    var data: List<UserData>
)

data class UserData(
    var id: Int,
    var email: String,
    var first_name: String,
    var last_name: String,
    var avatar: String
)