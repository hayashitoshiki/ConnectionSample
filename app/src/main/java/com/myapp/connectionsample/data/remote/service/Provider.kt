package com.myapp.connectionsample.data.remote.service

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.myapp.connectionsample.BuildConfig
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

/**
 * APIベース設定
 */
object Provider {

    // ネットワークタイムアウト
    private const val defaultTimeout = 5L

    // ネットワーク設定
    private fun build(): OkHttpClient {
        // Log設定
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            // 接続タイムアウト
            .connectTimeout(defaultTimeout, TimeUnit.SECONDS)
            // 書き込みタイムアウト
            .writeTimeout(defaultTimeout, TimeUnit.SECONDS)
            // 取得タイムアウト
            .readTimeout(defaultTimeout, TimeUnit.SECONDS)
            .build()
    }

    private val ConvertFormat = Json {
        // 「"true(Boolean)"」と「文字列("で囲まない)」を許可
        isLenient = true
        // Dataクラスに存在しない値は無視
        ignoreUnknownKeys = true
        // null値をデフォルト値へ変更
        coerceInputValues = true
        // @JsonNamesを使わない時のパフォーマンス向上(falseの時)
        useAlternativeNames = false
    }

    /**
     * httpSampleApi
     */
    @ExperimentalSerializationApi
    fun httpSampleApi(): HttpService {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            // URL
            .baseUrl(BuildConfig.httpServer)
            // client設定
            .client(build())
            // Json Convert　設定
            .addConverterFactory(ConvertFormat.asConverterFactory(contentType))
            .build()
            // API
            .create(HttpService::class.java)
    }

}