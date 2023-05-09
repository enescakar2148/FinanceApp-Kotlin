package com.enescakar.financeapp.View

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.anychart.AnyChart
import com.anychart.chart.common.listener.Event
import com.anychart.chart.common.listener.ListenersInterface
import com.anychart.charts.Pie
import com.enescakar.financeapp.Util.StatusBuilder
import com.enescakar.financeapp.ViewModel.HomeViewModel
import com.enescakar.financeapp.databinding.ActivityHomeBinding


class Home : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var homeViewModel: HomeViewModel
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
            ListenersInterface.OnClickListener(arrayOf<String>("x", "value")) {
            override fun onClick(event: Event) {
                Toast.makeText(
                    // TODO: BURADA BASTIKTAN SONRA YENİ SAYFADA BU HARCAMAYA AİT DETAYLAR GÖSTERİLİR
                    this@Home,
                    event.getData().get("x") + ":" + event.getData().get("value"),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        binding.anyChartView.setChart(pie)
    }

    private fun observeData(pie: Pie){
        homeViewModel.expenseChart.observe(this, Observer {
            pie.data(it)
        })
        homeViewModel.income.observe(this, Observer {
            pie.title("Toplam ${it.wage} gelir ve Harcama dağılım grafiği güncelleme")
        })
        homeViewModel.status.observe(this, Observer {
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
        })
    }
}