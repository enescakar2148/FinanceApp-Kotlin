package com.enescakar.financeapp.View

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.anychart.AnyChart
import com.anychart.chart.common.listener.Event
import com.anychart.chart.common.listener.ListenersInterface
import com.anychart.charts.Pie
import com.enescakar.financeapp.Adapters.HomeRecyclerAdapter
import com.enescakar.financeapp.Model.Expense
import com.enescakar.financeapp.Util.Categories
import com.enescakar.financeapp.Util.StatusBuilder
import com.enescakar.financeapp.ViewModel.HomeViewModel
import com.enescakar.financeapp.databinding.ActivityHomeBinding
import java.time.LocalTime


class Home : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private val adapter = HomeRecyclerAdapter(arrayListOf())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        homeViewModel = HomeViewModel()
        homeViewModel.datas()

        //Create Chart-> Pie Chart
        val pie : Pie = AnyChart.pie()
        /*
        Todo: ANYCHART BENİM MODEL SINIFIMI DESTEKLEMİYOR
         */

        observeData(pie)

        pie.setOnClickListener(object :
            ListenersInterface.OnClickListener(arrayOf("x", "value")) {
            override fun onClick(event: Event) {
                Toast.makeText(
                    // TODO: BURADA BASTIKTAN SONRA YENİ SAYFADA BU HARCAMAYA AİT DETAYLAR GÖSTERİLİR
                    this@Home,
                    event.data["x"] + ":" + event.data["value"],
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        binding.anyChartView.setChart(pie)

        binding.homeRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.homeRecyclerView.adapter = adapter

    }

    @SuppressLint("SetTextI18n")
    private fun observeData(pie: Pie){
        homeViewModel.expenseChart.observe(this) {
            pie.data(it)
        }
        homeViewModel.income.observe(this){
            pie.title("Toplam ${it.wage} gelir ve Harcama dağılım grafiği güncelleme")
        }
        homeViewModel.status.observe(this){
            if (it.status == StatusBuilder().Bad()){
                binding.statusText.setBackgroundResource(android.R.color.holo_red_light)
                binding.statusText.text = "Durum: Kritik\n" +
                        "Harcamalarınız Gelir seviyenizin yarısından fazla!"
            }else if(it.status == StatusBuilder().Middle()){
                binding.statusText.setBackgroundResource(android.R.color.holo_orange_light)
                binding.statusText.text = "Durum: Orta\n" +
                        "Harcamalarınız Gelir seviyenizin yarısından az."
            }else{
                binding.statusText.setBackgroundResource(android.R.color.holo_green_light)
                binding.statusText.text = "Durum: Güzel\n" +
                        "Harcamalarınız Gelir seviyenizin çeyreğinden az."
            }
        }
        homeViewModel.expenses.observe(this) {
            it.let {
                adapter.updateDatas(it as ArrayList<Expense>)
            }
        }
    }

    fun addExpenses(v: View) {
        homeViewModel.updateExpense(
            Expense(30, Categories().Fuel(), LocalTime.now())
        )
        homeViewModel.updateExpense(
            Expense(10, Categories().Food(), LocalTime.now())
        )
    }
}