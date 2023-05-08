package com.enescakar.financeapp.Model

import java.time.LocalTime

data class Expense(
    val amount: Int,
    val category: String,
    val date: LocalTime
)