package com.lufthansa.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GiphyResponse(
    val data: List<GifDto> = emptyList()
)

@Serializable
data class GifDto(
    val id: String,
    val title: String = "",
    val username: String = "",
    val images: ImagesDto
)

@Serializable
data class ImagesDto(
    @SerialName("preview_gif") val previewGif: ImageDto? = null,
    @SerialName("downsized") val downsized: ImageDto? = null,
    @SerialName("original") val original: ImageDto? = null
)

@Serializable
data class ImageDto(
    val url: String? = null,
    val width: String? = null,
    val height: String? = null,
    val size: String? = null
)