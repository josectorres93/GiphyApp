package com.lufthansa.presentation.ui

import android.content.Context
import coil.ImageLoader
import coil.decode.ImageDecoderDecoder

fun Context.gifImageLoader(): ImageLoader {
    return ImageLoader.Builder(this)
        .components {
            add(ImageDecoderDecoder.Factory())
        }
        .build()
}