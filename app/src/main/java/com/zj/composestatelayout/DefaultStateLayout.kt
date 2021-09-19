package com.zj.composestatelayout

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class StateData(
    val tipTex: String? = null,
    val tipImg: Int? = null,
    val btnText: String? = null
)

fun PageState.bindData(stateData: StateData? = null): PageStateData {
    when (this) {
        PageState.LOADING -> {
            val data = stateData ?: StateData("默认加载中文案")
            return PageStateData(this, data)
        }
        PageState.EMPTY -> {
            val data = stateData ?: StateData("默认空页面文案")
            return PageStateData(this, data)
        }
        PageState.ERROR -> {
            val data = stateData ?: StateData("默认错误页面文案")
            return PageStateData(this, data)
        }
        PageState.CONTENT -> {
            return PageStateData(this, stateData)
        }
    }
}

@Composable
fun DefaultStateLayout(
    modifier: Modifier = Modifier,
    pageStateData: PageStateData,
    onRetry: OnRetry = { },
    loading: @Composable (StateLayoutData) -> Unit = { DefaultLoadingLayout(it) },
    empty: @Composable (StateLayoutData) -> Unit = { DefaultEmptyLayout(it) },
    error: @Composable (StateLayoutData) -> Unit = { DefaultErrorLayout(it) },
    content: @Composable () -> Unit = { }
) {
    ComposeStateLayout(
        modifier = modifier,
        pageStateData = pageStateData,
        onRetry = onRetry,
        loading = { loading(it) },
        empty = { empty(it) },
        error = { error(it) },
        content = content
    )
}

@Composable
fun DefaultLoadingLayout(stateLayoutData: StateLayoutData) {
    stateLayoutData.pageStateData.let {
        (it.tag as? StateData)?.let { item ->
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                    Text(
                        text = item.tipTex ?: "",
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun DefaultEmptyLayout(stateLayoutData: StateLayoutData) {

}

@Composable
fun DefaultErrorLayout(stateLayoutData: StateLayoutData) {

}