package com.enescakar.financeapp.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.enescakar.financeapp.Model.Expense
import com.enescakar.financeapp.Model.Income
import com.enescakar.financeapp.Model.Status
import com.enescakar.financeapp.Util.StatusBuilder
import java.time.LocalTime
import java.util.Objects

class HomeViewModel: ViewModel() {
    val income = MutableLiveData<Income>()
    val expenseChart = MutableLiveData<MutableList<DataEntry>>()
    val expenses= MutableLiveData<Expense>()
    val data: MutableList<DataEntry> = ArrayList()
    val status = MutableLiveData<Status>()

    fun datas(){
        //Fake
        expenses.value = Expense(6500, null, null)

        income.value = Income(12000, LocalTime.now())

        converDataToDataEntry(Expense(1250, "Fuel", LocalTime.now()))
        converDataToDataEntry(Expense(50, "Fuel", LocalTime.now()))
        converDataToDataEntry(Expense(242, "Food", LocalTime.now()))
        converDataToDataEntry(Expense(4000, "Shopping", LocalTime.now()))
        converDataToDataEntry(Expense(231, "Telecom", LocalTime.now()))

        expenseChart.value = data

        //Status
        //Use the StatusBuilder
            //harcama = maasş / 2 -> Middle
            if (expenses.value!!.amount <= income.value!!.wage/4){
                status.value = Status(StatusBuilder().Good())
            } else if (expenses.value!!.amount <= income.value!!.wage /2) {
                status.value = Status(StatusBuilder().Middle())
            } else{
                status.value = Status(StatusBuilder().Bad())
                return
            }
    }
    private fun converDataToDataEntry(expense: Expense): MutableList<DataEntry> {
        data.add(ValueDataEntry(expense.category, expense.amount))
        return data
    }
}