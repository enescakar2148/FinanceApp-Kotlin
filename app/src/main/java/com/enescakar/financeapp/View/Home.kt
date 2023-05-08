package com.enescakar.financeapp.View

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.chart.common.listener.Event
import com.anychart.chart.common.listener.ListenersInterface
import com.anychart.charts.Pie
import com.enescakar.financeapp.Model.Expense
import com.enescakar.financeapp.databinding.ActivityHomeBinding
import java.time.LocalTime


class Home : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val pie : Pie = AnyChart.pie()

        //Demo Data
        val wage = 12500
        val data: MutableList<DataEntry> = ArrayList()

        /*
        Todo: ANYCHART BENİM MODEL SINIFIMI DESTEKLEMİYOR
        TODO: BU NEDENLE MODEL CLASSTAN İŞLEMLERİ YAPMAM VE DATAENTRY'E VERMEM GEREKİR
        TODO: DAHA DETAY VERİRSEM HARCAMA GRAFİĞİNİ OLUŞTURMAK İÇİN EXPENSE MODEL KULLANARAK HARCAMALARI OLUŞTURMAM/TOPLAMAM
        TODO: VE BÜTÜN OLUŞAN DATALAR İÇİNDEN GEREKEN (KATEGORİ İSMİ VE TUTAR -> (X, VALUE)
        TODO: ALIP GEREKEN YERE VERMEM GEREKİR. VALUE KISMINA İSE BİR KATEGORİYE AİT DEĞERLERİN TOPLAMINI SUNMAM GEREKİR
         */

        //data.add(Expense(2500, "Yakıt", LocalTime.now())

        data.add(ValueDataEntry("yakıt", 2250))
        data.add(ValueDataEntry("yemek", 3000))
        data.add(ValueDataEntry("telekominasyon", 235))
        data.add(ValueDataEntry("alışveriş", 3234.5))

        pie.data(data)
        pie.setOnClickListener(object :
            ListenersInterface.OnClickListener(arrayOf<String>("x", "value")) {
            override fun onClick(event: Event) {
                Toast.makeText(
                    this@Home,
                    event.getData().get("x") + ":" + event.getData().get("value"),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        pie.title("Toplam ${wage} gelir ve Harcama dağılım grafiği güncelleme")

        binding.anyChartView.setChart(pie)

    }
}