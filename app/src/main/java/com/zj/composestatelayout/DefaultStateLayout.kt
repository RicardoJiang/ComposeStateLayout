package com.zj.composestatelayout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.zj.statelayout.*

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
            val data = stateData ?: StateData("默认空页面文案", R.mipmap.status_empty)
            return PageStateData(this, data)
        }
        PageState.ERROR -> {
            val data = stateData ?: StateData("默认错误页面文案", R.mipmap.status_error, "重试")
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
    stateLayoutData.pageStateData.let {
        (it.tag as? StateData)?.let { item ->
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = item.tipImg ?: 0),
                        modifier = Modifier.size(200.dp),
                        contentDescription = ""
                    )
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
fun DefaultErrorLayout(stateLayoutData: StateLayoutData) {
    stateLayoutData.pageStateData.let {
        (it.tag as? StateData)?.let { item ->
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = item.tipImg ?: 0),
                        modifier = Modifier.size(200.dp),
                        contentDescription = ""
                    )
                    Text(
                        text = item.tipTex ?: "",
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier
                            .padding(16.dp, 0.dp)
                            .offset(0.dp, -(20.dp))
                    )

                    Text(
                        text = item.btnText ?: "",
                        color = Color.White,
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(16.dp, 0.dp)
                            .width(100.dp)
                            .wrapContentHeight()
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color.Blue)
                            .padding(0.dp, 10.dp)
                            .clickable {
                                stateLayoutData.retry.invoke(it)
                            })
                }
            }
        }
    }
}