package id.ac.uny.riset.ride.menu.surveySaya.adapter

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import id.ac.uny.riset.ride.R
import id.ac.uny.riset.ride.menu.surveySaya.model.SurveySayaModel
import id.ac.uny.riset.ride.utils.Function
import id.ac.uny.riset.ride.utils.timelineview.TimelineView

class AdapterSurveySaya(context: Context, list_survey: MutableList<SurveySayaModel>, private val onAdapterSurveySaya: OnAdapterSurveySaya) : RecyclerView.Adapter<AdapterSurveySaya.Holder>() {

    lateinit var list_survey: MutableList<SurveySayaModel>
    lateinit var context: Context

    init {
        this.context = context
        this.list_survey = list_survey
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutView: View
        layoutView = LayoutInflater.from(parent.context).inflate(R.layout.adapter_survey_saya, parent, false)
        return AdapterSurveySaya.Holder(layoutView, viewType)
    }

    override fun getItemCount(): Int {
        return list_survey.size;
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val surveyModel = list_survey.get(position)
        holder.card_survey!!.setOnClickListener({
            onAdapterSurveySaya.onAdapterSurveySaya(surveyModel)
        })
        holder.tv_date!!.setText(Function.parseTimestampToSimpleDate(surveyModel.tanggal_survey))
        holder.tv_month_year!!.setText(Function.parseTimestampToSimpleMonthYear(surveyModel.tanggal_survey))
        holder.tv_title!!.setText(surveyModel.nama_status)
        holder.tv_desc!!.setText(surveyModel.deskripsi_status)
    }

    override fun getItemViewType(position: Int): Int {
        return TimelineView.getTimeLineViewType(position, itemCount)
    }

    interface OnAdapterSurveySaya{
        fun onAdapterSurveySaya(surveySayaModel: SurveySayaModel)
    }

    class Holder (itemView: View, viewType: Int) : RecyclerView.ViewHolder(itemView) {
        internal var tv_date: TextView? = null
        internal var tv_month_year: TextView? = null
        internal var card_survey: CardView? = null
        internal var timeline: TimelineView? = null
        internal var tv_title: TextView? = null
        internal var tv_desc: TextView? = null
        init {
            this.setIsRecyclable(false)
            tv_title = itemView.findViewById(R.id.tv_title)
            tv_date = itemView.findViewById(R.id.tv_date)
            tv_month_year = itemView.findViewById(R.id.tv_month_year)
            tv_desc = itemView.findViewById(R.id.tv_desc)
            card_survey = itemView.findViewById(R.id.card_survey)
            timeline = itemView.findViewById(R.id.timeline)

            timeline!!.initLine(viewType)
        }
    }
}
