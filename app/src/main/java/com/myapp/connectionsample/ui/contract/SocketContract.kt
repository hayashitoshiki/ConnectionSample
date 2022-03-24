package com.myapp.connectionsample.ui.contract

import com.myapp.connectionsample.ui.util.BaseContract

interface SocketContract {
    /**
     * メイン画面 状態保持
     *
     */
    data class State(
        var accountName: String = ""
    ) : BaseContract.State

    /**
     * UIイベント
     *
     */
    sealed class Effect : BaseContract.Effect {
        data class ShowToast(val message: String): Effect()
    }

    /**
     * アクション
     *
     */
    sealed class Event : BaseContract.Event {
        object Connect: Event()
        object Cancel: Event()
        data class SendMessage(val message: String): Event()
    }
}