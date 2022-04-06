package com.myapp.connectionsample.data.remote

import android.util.Log
import com.myapp.connectionsample.BuildConfig
import com.myapp.connectionsample.GrpcLessonServiceGrpcKt
import com.myapp.connectionsample.Sample
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

/**
 * gRPC管理
 */
object GrpcService {

    private val channel = ManagedChannelBuilder
        .forAddress(BuildConfig.grpcServerHost, BuildConfig.grpcServerPort)
        .usePlaintext()
        .build()
    private val service = GrpcLessonServiceGrpcKt.GrpcLessonServiceCoroutineStub(channel)

    /**
     * Unary RPC　サンプル
     * 1 Request - 1 Response方式
     *
     * ここでばAとBを送り、C(A+B)が帰ってくるAPIを叩く
     *
     * @return unaryApiレスポンス
     */
    suspend fun unaryApi() : String {
        val request = Sample.SumRequest
            .newBuilder()
            .also{
                it.a = 1
                it.b = 2
            }
            .build()
        return service.sum(request).message
    }

    /**
     * Server streaming RPC　サンプル
     * 1 Request - N Response方式
     *
     * ここでばAとBを送り、C(A+B)が帰ってくるAPIを叩く
     *
     * @return unaryApiレスポンス
     */
    fun serverStreamingApi(userName: String) : Flow<Sample.SheepOutResponse> {
        val request = Sample.SheepOutRequest
            .newBuilder()
            .also{ it.userName  = userName }
            .build()
        return service.sheepOut(request)
    }
    private val _sheepInFlow: Channel<Sample.SheepInRequest> = Channel()
    val sheepInFlow: Flow<Sample.SheepInRequest> = _sheepInFlow.receiveAsFlow()
    /**
     * Client streaming RPC　サンプル
     * N Request - 1 Response方式
     *
     * ここでばAとBを送り、C(A+B)が帰ってくるAPIを叩く
     *
     * @return unaryApiレスポンス
     */
    suspend fun clientStreamingApi(sheepList: List<String>) {
        service.sheepIn(sheepInFlow).sheepCountResult
        sheepList.forEach { sheep ->
            val request = Sample.SheepInRequest
                .newBuilder()
                .also { it.sheepName = "羊$sheep" }
                .build()
            Log.d("TAG" , "request = " + request.sheepName)
            _sheepInFlow.send(request)
        }
    }

    /**
     * Bidirectional streaming RPC　サンプル
     * N Request - N Response方式
     *
     * ここでばAとBを送り、C(A+B)が帰ってくるAPIを叩く
     *
     * @return unaryApiレスポンス
     */
    fun bidirectionalStreamingApi(stream: Flow<String>) : Flow<Sample.ChatResponse> {
        val request = stream.map { message ->
           return@map Sample.ChatRequest
               .newBuilder()
               .also{ it.message =  message }
               .build()
        }
        return service.chatStream(request)
    }
}