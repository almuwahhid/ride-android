package id.ac.uny.riset.ride.menu.grafik.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.mikephil.charting.charts.BarChart
import id.ac.uny.riset.ride.R
import id.ac.uny.riset.ride.menu.grafik.model.GrafikAspekUiModel
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import id.ac.uny.riset.ride.menu.grafik.helper.GrafikHelper
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.components.XAxis


class GrafikAdapter (context: Context, list_grafik: MutableList<GrafikAspekUiModel>, private val onGrafikAdapter: GrafikAdapter.OnGrafikAdapter) : RecyclerView.Adapter<GrafikAdapter.Holder>() {

    lateinit var context: Context
    lateinit var list_grafik: MutableList<GrafikAspekUiModel>

    init {
        this.context = context
        this.list_grafik = list_grafik
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GrafikAdapter.Holder {
        val layoutView: View
        layoutView = LayoutInflater.from(parent.context).inflate(R.layout.adapter_grafik, parent, false)
        return GrafikAdapter.Holder(layoutView)
    }

    override fun getItemCount(): Int {
        return list_grafik.size
    }

    override fun onBindViewHolder(holder: GrafikAdapter.Holder, position: Int) {
        val grafikAspekUiModel = list_grafik.get(position)
        holder.tv_title!!.setText("Grafik Skor berdasarkan Aspek "+grafikAspekUiModel.nama_aspek)

        val entries = GrafikHelper.parseGrafikToBar(grafikAspekUiModel.survey);
        val dataSet = BarDataSet(entries, "Skor Aspek "+grafikAspekUiModel.nama_aspek)

        val data = BarData(dataSet)
        data.setBarWidth(0.4f)
        holder.chart_bar!!.setData(data)
        holder.chart_bar!!.animateXY(1000, 1000)
        holder.chart_bar!!.setFitBars(true);
        holder.chart_bar!!.invalidate()

        val xl = holder.chart_bar!!.getXAxis()
        xl.setPosition(XAxis.XAxisPosition.BOTTOM)
        xl.setDrawAxisLine(true)
        xl.setDrawGridLines(false)
        xl.setGranularity(1f)
        xl.setAxisMinimum(0f)
        xl.setAxisMaximum(list_grafik.get(position).survey.size.toFloat()+1)

        val l = holder.chart_bar!!.getLegend()
        l.setXEntrySpace(0.1f)
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM)
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT)
//        l.setOrientation(Legend.LegendOrientation.HORIZONTAL)
//        l.setDrawInside(false)
//        l.setFormSize(8f)


        val yl = holder.chart_bar!!.getAxisLeft()
        yl.setDrawAxisLine(true)
        yl.setDrawGridLines(true)
        yl.setAxisMinimum(1f) // this replaces setStartAtZero(true)
        yl.setAxisMaximum(GrafikHelper.getMaxYAxis(position)) // this replaces setStartAtZero(true)
//        yl.setInverted(true);

        val yr = holder.chart_bar!!.getAxisRight()
//        yr.setTypeface(tfLight)
        yr.setDrawAxisLine(true)
        yr.setDrawGridLines(false)
        yr.setAxisMinimum(1f) // this replaces setStartAtZero(true)
        yr.setAxisMaximum(GrafikHelper.getMaxYAxis(position)) // this replaces setStartAtZero(true)

        val xAxis = holder.chart_bar!!.getXAxis()
        xAxis.setValueFormatter(IAxisValueFormatter { value, axis ->
            return@IAxisValueFormatter GrafikHelper.dateRange((value.toInt()-1), list_grafik.get(position).survey)
        })
        holder.chart_bar!!.invalidate()
    }

    interface OnGrafikAdapter{

    }

    class Holder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var tv_title: TextView? = null
        internal var chart_bar: BarChart? = null

        init {
            tv_title = itemView.findViewById(R.id.tv_title)
            chart_bar = itemView.findViewById(R.id.chart_bar)
            this.setIsRecyclable(false)
        }
    }
}