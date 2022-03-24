package com.myapp.connectionsample.data.remote

import android.util.Log
import com.myapp.connectionsample.BuildConfig
import com.myapp.connectionsample.model.Message
import com.myapp.connectionsample.model.PeopleState
import com.myapp.connectionsample.model.RoomState
import com.myapp.connectionsample.model.User
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.*
import javax.inject.Inject

/**
 * Socket 管理クラス
 *
 */
class SocketManager @Inject constructor(
    private val externalScope: CoroutineScope,
    private val ioDispatcher: CoroutineDispatcher
) {

    companion object {
        private const val TAG = "SocketManager"
    }

    // 受信したデータ
    private val _receiveUserFlow = Channel<RoomState>()
    val receiveUserFlow: Flow<RoomState> = _receiveUserFlow.receiveAsFlow()
    private val _receiveMessageFlow = Channel<Message>()
    val receiveMessageFlow: Flow<Message> = _receiveMessageFlow.receiveAsFlow()

    // Socket
    private lateinit var socket: Socket

    /**
     * 受信情報
     *
     * @property url 受信URL
     * @property listener 動作
     */
    private data class Receiver(val url: String, val listener: Emitter.Listener)

    // 接続失敗
    private val onConnectError =
        Receiver(
            Socket.EVENT_CONNECT,
            socketReceiveListener {
                Log.d(TAG, "接続失敗")
            }
        )

    // 接続成功
    private val connection =
        Receiver(
            Socket.EVENT_CONNECT_ERROR,
            socketReceiveListener {
                Log.d(TAG, "接続成功")
            }
        )

    // 入室受信
    private val joinReceive =
        Receiver(
            "client_join_room",
            socketReceiveListener { response ->
                RoomState(Json.decodeFromString(response), PeopleState.IN)
                    .also{ _receiveUserFlow.send(it) }
            }
        )

    // メッセージ受信
    private val messageReceive =
        Receiver(
            "client_receive_message",
            socketReceiveListener { response ->
                Json.decodeFromString<Message>(response)
                    .also{ _receiveMessageFlow.send(it) }
            }
        )

    // 退出受信
    private val exitReceive =
        Receiver(
            "client_exit_room",
            socketReceiveListener { response ->
                RoomState(Json.decodeFromString(response), PeopleState.OUT)
                    .also{ _receiveUserFlow.send(it) }
            }
        )

    private val receiveList: List<Receiver> =
        listOf(
            onConnectError,
            connection,
            joinReceive,
            messageReceive,
            exitReceive
        )

    /**
     * 送信情報
     *
     * @param T 送信するデータのモデルクラス
     * @property url 送信先URL
     * @property data 送信するデータ
     */
    sealed class  SocketRequestType<T>(val url: String, val data: T) {
        abstract fun getJsonData(): String

        class Join(data: User) : SocketRequestType<User>("server_join_room", data) {
            override fun getJsonData() = Json.encodeToString(data)
        }
        class SendMessage(data: Message) : SocketRequestType<Message>("server_receive_message", data){
            override fun getJsonData() = Json.encodeToString(data)
        }
        class Exit(data: User) : SocketRequestType<User>("server_exit_room", data) {
            override fun getJsonData() = Json.encodeToString(data)
        }
    }

    /**
     * 接続
     *
     */
    fun create() {
        try {
            socket = IO.socket(BuildConfig.socketServer)
        } catch (e: URISyntaxException) {
            throw RuntimeException(e)
        }
        socket.connect()
        receiveList.forEach{ socket.on(it.url, it.listener) }
        Log.d(TAG, "接続 : " + BuildConfig.socketServer)
    }

    /**
     * 送信
     *
     * @param T 送信するモデルタイプ
     * @param socketRequest 送信する
     */
    fun <T> send(socketRequest: SocketRequestType<T>) {
        socket.emit(socketRequest.url, socketRequest.getJsonData())
    }

    /**
     * 切断
     *
     */
    fun close() {
        socket.disconnect()
        receiveList.forEach{ socket.off(it.url, it.listener) }
        Log.d(TAG, "切断 : " + BuildConfig.socketServer)
    }

    private fun socketReceiveListener(unit: suspend (String) -> Unit) = Emitter.Listener { response ->
        externalScope.launch {
            withContext(ioDispatcher) {
                if (response.isNotEmpty()) {
                    unit(response[0].toString())
                }
            }
        }
    }

}



