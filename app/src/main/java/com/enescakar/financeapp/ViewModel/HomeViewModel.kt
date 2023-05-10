package com.enescakar.financeapp.ViewModel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.enescakar.financeapp.Model.Expense
import com.enescakar.financeapp.Model.Income
import com.enescakar.financeapp.Model.Status
import com.enescakar.financeapp.Util.StatusBuilder
import java.time.LocalTime

/*
TODO: CONVERT VE UPDATE EXPENSE FONKSIYONLARINDA BİR MANTIK HATASI VAR
TODO: TAM OLARAK DOĞRU ÇALIŞMIYOR
TODO: DEBUG EDİP ÇÖZ ONU!!!

TODO: VE ROOM EKLENECEK ARTIK. DAO VE VERITABANINI YAZ
 */
class HomeViewModel : ViewModel() {
    val income = MutableLiveData<Income>()
    val expenseChart = MutableLiveData<MutableList<DataEntry>>()
    val expenses = MutableLiveData<List<Expense>>()
    private val data: MutableList<DataEntry> = ArrayList()
    val status = MutableLiveData<Status>()
    private val expenseList: ArrayList<Expense> = arrayListOf()

    @SuppressLint("SuspiciousIndentation")
    fun datas() {

        income.value = Income(12000, LocalTime.now())

        //Find the total expense amount
        var totalExpenseAmount = 0.0
        if (expenses.value != null) {
            for (expense in expenses.value as ArrayList<Expense>) {
                totalExpenseAmount += expense.amount.toDouble()
            }
        }

        //set Status
        //Use the StatusBuilder
        if (totalExpenseAmount <= income.value!!.wage / 4) {
            status.value = Status(StatusBuilder().Good())
        } else if (totalExpenseAmount <= income.value!!.wage / 2) {
            status.value = Status(StatusBuilder().Middle())
        } else {
            status.value = Status(StatusBuilder().Bad())
            return
        }
    }

    private fun convertDataToDataEntry(expense: Expense): MutableList<DataEntry> {
        if (data.size != 0){
            for (i in data) {
                if (i.getValue("x").equals(expense.category)) {
                    data.set(0, ValueDataEntry(expense.category, expense.amount))
                    return data
                }
            }
            data.add(ValueDataEntry(expense.category, expense.amount))

        }else{
            data.add(ValueDataEntry(expense.category, expense.amount))
        }
        return data
    }

    fun updateExpense(expense: Expense) {
        //Fake
        val originalAmount = expense.amount
        if (expenseList.size >= 1) {
            for (element in expenseList) {
                if (element.category == expense.category) {
                    expense.amount = element.amount + expense.amount
                }

            }
            convertDataToDataEntry(
                expense
            )
            expense.amount = originalAmount
        } else {
            convertDataToDataEntry(
                expense
            )
        }

        expenseList.add(expense)
        expenses.value = expenseList

        expenseChart.value = data

    }
}