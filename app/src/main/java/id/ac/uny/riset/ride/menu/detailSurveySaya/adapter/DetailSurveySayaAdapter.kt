package id.ac.uny.riset.ride.menu.detailSurveySaya.adapter

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import id.ac.uny.riset.ride.R
import id.ac.uny.riset.ride.menu.detailSurveySaya.model.DetailSurveySayaModel

class DetailSurveySayaAdapter (context: Context, list_survey: MutableList<DetailSurveySayaModel>, private val onAdapterSurveySaya: OnDetailSurveySayaAdapter) : RecyclerView.Adapter<DetailSurveySayaAdapter.Holder>() {

    lateinit var list_survey: MutableList<DetailSurveySayaModel>
    lateinit var context: Context

    init {
        this.context = context
        this.list_survey = list_survey
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailSurveySayaAdapter.Holder {
        val layoutView: View
        layoutView = LayoutInflater.from(parent.context).inflate(R.layout.adapter_detail_survey_saya, parent, false)
        return DetailSurveySayaAdapter.Holder(layoutView, viewType)
    }

    override fun getItemCount(): Int {
        return list_survey.size
    }

    override fun onBindViewHolder(holder: DetailSurveySayaAdapter.Holder, position: Int) {
        val detailSurveySayaModel = list_survey.get(position)
        holder.tv_index!!.setText(""+(position+1))
        holder.tv_skor!!.setText(detailSurveySayaModel.nilai_pertanyaan)
        holder.tv_content!!.setText(detailSurveySayaModel.nama_pernyataan)
        holder.card_lay!!.setOnClickListener({
            onAdapterSurveySaya.onDetailClick(detailSurveySayaModel)
        })
    }

    interface OnDetailSurveySayaAdapter {
        fun onDetailClick(model: DetailSurveySayaModel)
    }

    class Holder (itemView: View, viewType: Int) : RecyclerView.ViewHolder(itemView) {
        internal var tv_index: TextView? = null
        internal var tv_skor: TextView? = null
        internal var card_lay: CardView? = null
        internal var tv_content: TextView? = null
        init {
            this.setIsRecyclable(false)
            tv_content = itemView.findViewById(R.id.tv_content)
            tv_index = itemView.findViewById(R.id.tv_index)
            tv_skor = itemView.findViewById(R.id.tv_skor)
            card_lay = itemView.findViewById(R.id.card_lay)

            this.setIsRecyclable(false)
        }
    }

}