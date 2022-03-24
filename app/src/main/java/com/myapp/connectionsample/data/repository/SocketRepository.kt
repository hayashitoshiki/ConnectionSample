package com.myapp.connectionsample.data.repository

import com.myapp.connectionsample.model.Message
import com.myapp.connectionsample.model.RoomState
import kotlinx.coroutines.flow.Flow

interface SocketRepository {

    val receiveUserFlow: Flow<RoomState>
    val receiveMessageFlow: Flow<Message>

    fun create()

    fun close()

    fun sendMessage(message: String)
}