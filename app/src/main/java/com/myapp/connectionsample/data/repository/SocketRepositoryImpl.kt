package com.myapp.connectionsample.data.repository

import com.myapp.connectionsample.data.remote.*
import com.myapp.connectionsample.model.Message
import com.myapp.connectionsample.model.RoomState
import com.myapp.connectionsample.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SocketRepositoryImpl @Inject constructor(private val socketManager: SocketManager): SocketRepository {

    override val receiveUserFlow: Flow<RoomState> = socketManager.receiveUserFlow
    override val receiveMessageFlow: Flow<Message> = socketManager.receiveMessageFlow

    override fun create() {
        socketManager.create()
        val user = User("userA")
        val type = SocketManager.SocketRequestType.Join(user)
        socketManager.send(type)
    }

    override fun close() {
        val user = User("userA")
        val type = SocketManager.SocketRequestType.Exit(user)
        socketManager.send(type)
        socketManager.close()
    }

    override fun sendMessage(message: String) {
        val user = User("userA")
        val data = Message(user, message)
        val type = SocketManager.SocketRequestType.SendMessage(data)
        socketManager.send(type)
    }
}