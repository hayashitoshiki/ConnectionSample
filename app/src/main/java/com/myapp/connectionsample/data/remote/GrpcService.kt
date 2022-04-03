package com.myapp.connectionsample.data.remote

import com.myapp.connectionsample.BuildConfig
import com.myapp.connectionsample.GrpcLessonServiceGrpcKt
import com.myapp.connectionsample.Sample
import io.grpc.ManagedChannelBuilder

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
}