package com.enescakar.financeapp.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.enescakar.financeapp.Model.Expense
import com.enescakar.financeapp.Model.Income

class HomeViewModel: ViewModel() {
    val income = MutableLiveData<Income>()
    val expense = MutableLiveData<Expense>()

}