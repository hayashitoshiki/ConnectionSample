package com.myapp.connectionsample.ui.util

/**
 * Screen(UI描画）とViewModel(UIロジック）とのコンタクト定義
 *
 */
interface BaseContract {
    /**
     * 状態管理
     *
     */
    interface State

    /**
     * アクション管理
     *
     */
    interface Event

    /**
     * UI処理発火管理
     *
     */
    interface Effect
}
