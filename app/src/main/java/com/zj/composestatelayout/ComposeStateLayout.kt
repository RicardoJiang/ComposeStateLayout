package com.zj.composestatelayout

import androidx.compose.runtime.Composable


enum class PageState {
    LOADING,
    EMPTY,
    ERROR,
    CONTENT
}

data class PageStateData(
    val status: PageState,
    val tag: Any? = null
)


data class StateLayoutData(
    val tag: Any?,
    val retry: OnRetry = {}
)

typealias OnRetry = (PageStateData) -> Unit

@Composable
fun ComposeStateLayout(
    pageStateData: PageStateData,
    onRetry: OnRetry = { },
    loading: @Composable (StateLayoutData) -> Unit = { },
    empty: @Composable (StateLayoutData) -> Unit = {},
    error: @Composable (StateLayoutData) -> Unit = {},
    content: @Composable () -> Unit = { }
) {
    val stateLayoutData = StateLayoutData(pageStateData, onRetry)
    when (pageStateData.status) {
        PageState.LOADING -> {
            loading(stateLayoutData)
        }
        PageState.EMPTY -> {
            empty(stateLayoutData)
        }
        PageState.ERROR -> {
            error(stateLayoutData)
        }
        else -> {
            content()
        }
    }
}