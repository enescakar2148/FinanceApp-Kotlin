package com.enescakar.financeapp.View

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.chart.common.listener.Event
import com.anychart.chart.common.listener.ListenersInterface
import com.anychart.charts.Pie
import com.enescakar.financeapp.Model.Expense
import com.enescakar.financeapp.Model.Income
import com.enescakar.financeapp.ViewModel.HomeViewModel
import com.enescakar.financeapp.databinding.ActivityHomeBinding
import java.time.LocalTime


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
        TODO: BU NEDENLE MODEL CLASSTAN İŞLEMLERİ YAPMAM VE DATAENTRY'E VERMEM GEREKİR
        TODO: DAHA DETAY VERİRSEM HARCAMA GRAFİĞİNİ OLUŞTURMAK İÇİN EXPENSE MODEL KULLANARAK HARCAMALARI OLUŞTURMAM/TOPLAMAM
        TODO: VE BÜTÜN OLUŞAN DATALAR İÇİNDEN GEREKEN (KATEGORİ İSMİ VE TUTAR -> (X, VALUE)
        TODO: ALIP GEREKEN YERE VERMEM GEREKİR. VALUE KISMINA İSE BİR KATEGORİYE AİT DEĞERLERİN TOPLAMINI SUNMAM GEREKİR
         */

        //data.add(Expense(2500, "Yakıt", LocalTime.now())
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
        homeViewModel.expense.observe(this, Observer {
            pie.data(it)
        })
        homeViewModel.income.observe(this, Observer {
            pie.title("Toplam ${it.wage} gelir ve Harcama dağılım grafiği güncelleme")

        })
    }
}