package com.enescakar.financeapp.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalTime

@Entity
data class Expense(
    @ColumnInfo
    var amount: Int,

    @ColumnInfo
    val category: String?,

    @ColumnInfo
    val date: LocalTime?
)
{
    @PrimaryKey(autoGenerate = true)
    val uuid: Int = 0
}