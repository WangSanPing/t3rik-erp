package com.t3rik.mobile.common.vo

class DictVo(val dictKey: String,val data: MutableList<DataItem>) {
    class DataItem(val label: String, val value: String, val color: String)
}