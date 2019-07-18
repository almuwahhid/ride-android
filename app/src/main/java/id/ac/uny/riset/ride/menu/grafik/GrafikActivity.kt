package id.ac.uny.riset.ride.menu.grafik

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import id.ac.uny.riset.ride.R
import id.ac.uny.riset.ride.base.BaseActivity
import id.ac.uny.riset.ride.menu.grafik.adapter.GrafikAdapter
import id.ac.uny.riset.ride.menu.grafik.helper.GrafikHelper
import id.ac.uny.riset.ride.menu.grafik.model.GrafikAspekUiModel
import id.ac.uny.riset.ride.menu.grafik.model.GrafikUiModel
import kotlinx.android.synthetic.main.activity_grafik.*
import kotlinx.android.synthetic.main.main_toolbar.*
import lib.almuwahhid.utils.LibUi
import com.github.mikephil.charting.components.XAxis.XAxisPosition


class GrafikActivity : BaseActivity(), GrafikView.View {

    lateinit var grafikPresenter: GrafikPresenter
    lateinit var grafikAdapter: GrafikAdapter
    var listGrafik: MutableList<GrafikAspekUiModel> = ArrayList()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grafik)
        setSupportActionBar(toolbar)
        supportActionBar!!.setTitle("Grafik ")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        grafikAdapter = GrafikAdapter(context, listGrafik, object : GrafikAdapter.OnGrafikAdapter{

        })
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = grafikAdapter

        grafikPresenter = GrafikPresenter(context, this)

        grafikPresenter.requestGrafikNilai()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRequestGrafikNilai(list: MutableList<GrafikUiModel>) {
        val entries = GrafikHelper.parseGrafikToBar(list);
        val dataSet = BarDataSet(entries, "Status Identitas")

        val data = BarData(dataSet)
        data.setBarWidth(0.4f)
        chart1!!.setData(data)
        chart1!!.animateXY(1000, 1000)
        chart1!!.invalidate()


        val xl = chart1.getXAxis()
        xl.setPosition(XAxisPosition.BOTTOM)
//        xl.setTypeface(tfLight)
        xl.setDrawAxisLine(true)
        xl.setDrawGridLines(false)
        xl.setGranularity(1f)

        val yl = chart1.getAxisLeft()
//        yl.setTypeface(tfLight)
        yl.setDrawAxisLine(true)
        yl.setDrawGridLines(true)
        yl.setAxisMinimum(20f) // this replaces setStartAtZero(true)
        yl.setAxisMaximum(160f) // this replaces setStartAtZero(true)
        chart1!!.invalidate()
//        yl.setInverted(true);

        val yr = chart1.getAxisRight()
        yr.setDrawAxisLine(true)
        yr.setDrawGridLines(false)
        yr.setAxisMinimum(20f) // this replaces setStartAtZero(true)
        yr.setAxisMaximum(160f) // this replaces setStartAtZero(true)
        yr.setGranularity(20f)
//        yr.setInverted(true);

        chart1.setFitBars(true)
        chart1.animateY(1500)

        val xAxis = chart1.getXAxis()
        xAxis.setValueFormatter(IAxisValueFormatter { value, axis ->
            return@IAxisValueFormatter GrafikHelper.dateRange((value.toInt()-1), list)
        })
        chart1!!.invalidate()

    }

    override fun onRequestGrafikAspek(list: MutableList<GrafikAspekUiModel>) {
        listGrafik.clear()
        listGrafik.addAll(list)
        grafikAdapter.notifyDataSetChanged()
    }

    override fun onFailedLoadGrafik(message: String) {
        LibUi.ToastShort(context, message)
        finish()
    }

    override fun onLoadingChart1() {
        pb_chart1.visibility = View.VISIBLE
    }

    override fun onLoadingChart2() {
        pb_chart2.visibility = View.VISIBLE
    }

    override fun onHideLoadingChart1() {
        pb_chart1.visibility = View.GONE
    }

    override fun onHideLoadingChart2() {
        pb_chart2.visibility = View.GONE
    }

    override fun onLoading() {

    }

    override fun onHideLoading() {

    }

    override fun onErrorConnection() {

    }
}
