package com.enescakar.financeapp.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.enescakar.financeapp.Model.Expense
import com.enescakar.financeapp.Model.Income
import java.time.LocalTime
import java.util.Objects

class HomeViewModel: ViewModel() {
    val income = MutableLiveData<Income>()
    val expense = MutableLiveData<MutableList<DataEntry>>()
    val data: MutableList<DataEntry> = ArrayList()

    fun datas(){
        income.value = Income(16750, LocalTime.now())

        converDataToDataEntry(Expense(1250, "Fuel", LocalTime.now()))
        converDataToDataEntry(Expense(50, "Fuel", LocalTime.now()))
        converDataToDataEntry(Expense(242, "Food", LocalTime.now()))
        converDataToDataEntry(Expense(4000, "Shopping", LocalTime.now()))
        converDataToDataEntry(Expense(231, "Telecom", LocalTime.now()))

        expense.value = data
    }
    private fun converDataToDataEntry(expense: Expense): MutableList<DataEntry> {
        data.add(ValueDataEntry(expense.category, expense.amount))
        return data
    }
}