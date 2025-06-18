package com.project.onycheck.data

import androidx.annotation.DrawableRes

data class NewsArticle(
    val id: Int,
    val title: String,
    val author: String,
    val date: String,
    @DrawableRes val imageRes: Int,
    val content: String
)