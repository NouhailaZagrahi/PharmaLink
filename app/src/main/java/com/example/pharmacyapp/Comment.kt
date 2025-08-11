package com.example.pharmacyapp

data class Comment(
    val id: Int,
    val articleId: Int,
    val userName: String,
    val commentText: String
)