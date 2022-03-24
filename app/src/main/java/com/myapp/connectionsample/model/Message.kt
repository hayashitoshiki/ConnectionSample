package com.myapp.connectionsample.model

import kotlinx.serialization.Serializable


@Serializable
data class Message(val user: User, val message: String)