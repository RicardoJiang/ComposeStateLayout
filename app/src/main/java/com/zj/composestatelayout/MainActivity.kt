package com.zj.composestatelayout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.zj.composestatelayout.ui.theme.ComposeStateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeStateTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    StateDemo()
                }
            }
        }
    }
}

@Composable
fun StateDemo() {
    var pageStateData by remember {
        mutableStateOf(PageState.CONTENT.bindData())
    }
    var isCustomLayout by remember {
        mutableStateOf(false)
    }
    Box(modifier = Modifier.fillMaxSize()) {
        DefaultStateLayout(
            modifier = Modifier.fillMaxSize(),
            pageStateData = pageStateData,
            onRetry = {
                pageStateData = PageState.LOADING.bindData()
            },
            loading = {
                if (isCustomLayout) {
                    CustomLoadingLayout(it)
                } else {
                    DefaultLoadingLayout(it)
                }
            }
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "加载成功内容", style = MaterialTheme.typography.h5)
            }
        }

        Column(
            modifier = Modifier
                .padding(0.dp, 20.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .align(Alignment.BottomCenter)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                BottomButton(text = "加载中") {
                    pageStateData = PageState.LOADING.bindData()
                }
                BottomButton(text = "空页面") {
                    pageStateData = PageState.EMPTY.bindData()
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                BottomButton(text = "加载失败") {
                    pageStateData = PageState.ERROR.bindData()
                }
                BottomButton(text = "加载成功") {
                    pageStateData = PageState.CONTENT.bindData()
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                BottomButton(text = "自定义加载中文案") {
                    pageStateData = PageState.LOADING.bindData(StateData(tipTex = "自定义加载中文案"))
                }
                BottomButton(text = "自定义加载中布局") {
                    isCustomLayout = !isCustomLayout
                }
            }
        }
    }
}

@Composable
fun BottomButton(text: String, callback: () -> Unit) {
    Text(
        text = text,
        color = Color.White,
        style = MaterialTheme.typography.body1,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(16.dp, 8.dp)
            .width(150.dp)
            .wrapContentHeight()
            .clip(RoundedCornerShape(10.dp))
            .background(Color.Blue)
            .padding(0.dp, 10.dp)
            .clickable {
                callback.invoke()
            })
}

@Composable
fun CustomLoadingLayout(stateLayoutData: StateLayoutData) {
    stateLayoutData.pageStateData.let {
        (it.tag as? StateData)?.let { item ->
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = item.tipTex ?: "",
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(16.dp)
                    )
                    CircularProgressIndicator()
                    Text(
                        text = "自定义布局",
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

