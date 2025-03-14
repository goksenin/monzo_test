package com.monzo.headlines.data.model

import java.time.LocalDateTime

/**
 * Domain models
 */
data class Article(
    val id: String,
    val thumbnail: String,
    val published: LocalDateTime,
    val title: String,
    val url: String
)
