package com.example.onlineimages.data.model

import androidx.room.Embedded
import kotlinx.serialization.*

@Serializable
data class User(
    @SerialName("links")
    @Embedded
    val userLinks: UserLinks,
    val username: String)
