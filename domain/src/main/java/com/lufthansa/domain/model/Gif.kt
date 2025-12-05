package com.lufthansa.domain.model

data class Gif(
    val id: String,
    val title: String,
    val username: String?,
    val previewUrl: String?,
    val fullUrl: String?,
    val width: Int?,
    val height: Int?
)