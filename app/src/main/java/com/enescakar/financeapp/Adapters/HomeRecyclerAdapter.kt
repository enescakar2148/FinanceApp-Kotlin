package com.enescakar.financeapp.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.enescakar.financeapp.Model.Expense
import com.enescakar.financeapp.databinding.HomeRecyclerItemBinding

class HomeRecyclerAdapter(private val expenseList: ArrayList<Expense>): RecyclerView.Adapter<HomeRecyclerAdapter.ViewHolder>() {
    class ViewHolder(val binding: HomeRecyclerItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HomeRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return expenseList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.amountText.text = expenseList[position].amount.toString()
        holder.binding.dateText.text = expenseList[position].date.toString()
        holder.binding.detailsText.text = expenseList[position].category
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateDatas(newList: ArrayList<Expense>){
        expenseList.clear()
        expenseList.addAll(newList)
        notifyDataSetChanged()
    }
}