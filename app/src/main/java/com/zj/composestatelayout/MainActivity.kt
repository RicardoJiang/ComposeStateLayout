package com.zj.composestatelayout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.zj.composestatelayout.ui.theme.ComposeStateTheme
import kotlinx.coroutines.launch

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
    ComposeStateLayout(
        pageStateData = PageState.LOADING.bindData(),
        onRetry = {

        },
        loading = { LoadingLayout(it) },
        empty = { EmptyLayout(it) },
        error = { ErrorLayout(it) }
    ) {

    }
}

@Composable
fun LoadingLayout(stateLayoutData: StateLayoutData) {

}

@Composable
fun EmptyLayout(stateLayoutData: StateLayoutData) {

}

@Composable
fun ErrorLayout(stateLayoutData: StateLayoutData) {

}

@Composable
fun ScaffoldDemo() {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "抽屉组件中内容")
            }

        },

        //标题栏区域
        topBar = {
            TopAppBar(
                title = { Text(text = "脚手架示例") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            scope.launch {
                                scaffoldState.drawerState.open()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = null
                        )
                    }
                }
            )
        },

        //悬浮按钮
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("悬浮按钮") },
                onClick = { }
            )
        },
        floatingActionButtonPosition = FabPosition.End,

        //屏幕内容区域
        content = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "屏幕内容区域")
            }
        },
    )
}
