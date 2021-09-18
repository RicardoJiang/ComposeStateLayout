package com.zj.composestatelayout

data class StateData(
    val tipTex: String = "错误"
)

fun PageState.bindData(stateData: StateData? = null): PageStateData {
    when (this) {
        PageState.LOADING -> {
            val data = stateData ?: StateData()
            return PageStateData(this, data)
        }
        PageState.EMPTY -> {
            val data = stateData ?: StateData()
            return PageStateData(this, data)
        }
        PageState.ERROR -> {
            val data = stateData ?: StateData()
            return PageStateData(this, data)
        }
        PageState.CONTENT -> {
            return PageStateData(this, stateData)
        }
    }
}


