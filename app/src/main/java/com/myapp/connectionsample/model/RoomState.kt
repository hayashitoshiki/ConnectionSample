package com.myapp.connectionsample.model

import kotlinx.serialization.Serializable

@Serializable
data class RoomState(val user: User, val state: PeopleState)