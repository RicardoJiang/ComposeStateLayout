package com.zj.statelayout

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


enum class PageState {
    LOADING,
    EMPTY,
    ERROR,
    CONTENT
}

data class PageStateData(val status: PageState, val tag: Any? = null)

data class StateLayoutData(val pageStateData: PageStateData, val retry: OnRetry = {})

typealias OnRetry = (PageStateData) -> Unit

@Composable
fun ComposeStateLayout(
    modifier: Modifier = Modifier,
    pageStateData: PageStateData,
    onRetry: OnRetry = { },
    loading: @Composable (StateLayoutData) -> Unit = {},
    empty: @Composable (StateLayoutData) -> Unit = {},
    error: @Composable (StateLayoutData) -> Unit = {},
    content: @Composable () -> Unit = { }
) {
    val stateLayoutData = StateLayoutData(pageStateData, onRetry)
    Box(modifier = modifier) {
        when (pageStateData.status) {
            PageState.LOADING -> loading(stateLayoutData)
            PageState.EMPTY -> empty(stateLayoutData)
            PageState.ERROR -> error(stateLayoutData)
            PageState.CONTENT -> content()
        }
    }
}