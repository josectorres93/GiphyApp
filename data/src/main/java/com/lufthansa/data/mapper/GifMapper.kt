package com.lufthansa.data.mapper

import com.lufthansa.data.dto.GifDto
import com.lufthansa.domain.model.Gif

object GifMapper {
    private fun fromDto(dto: GifDto): Gif {
        val preview = dto.images.previewGif?.url ?: dto.images.downsized?.url
        val full = dto.images.original?.url ?: dto.images.downsized?.url ?: preview
        val width = dto.images.original?.width?.toIntOrNull() ?: dto.images.downsized?.width?.toIntOrNull()
        val height = dto.images.original?.height?.toIntOrNull() ?: dto.images.downsized?.height?.toIntOrNull()
        return Gif(
            id = dto.id,
            title = dto.title,
            username = dto.username.ifBlank { null },
            previewUrl = preview,
            fullUrl = full,
            width = width,
            height = height
        )
    }

    fun fromDtoList(list: List<GifDto>): List<Gif> = list.map { fromDto(it) }
}