package com.enescakar.financeapp.Util

import com.enescakar.financeapp.Model.Status

open class StatusBuilder {

    fun Bad(): Int {
        return 3
    }
    fun Good(): Int{
        return 1
    }
    fun Middle(): Int{
        return 2
    }
}