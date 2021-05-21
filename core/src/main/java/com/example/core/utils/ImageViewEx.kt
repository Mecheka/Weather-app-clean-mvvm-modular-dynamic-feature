package com.example.core.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

fun ImageView.loadImageUrl(url: String) {
    Glide.with(this.context).load(url)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}