package id.ac.uny.riset.ride.menu.main.adapter

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import id.ac.uny.riset.ride.R
import id.ac.uny.riset.ride.data.model.TaskPertanyaanModel
import id.ac.uny.riset.ride.utils.Function

class AdapterTaskPertanyaan(context: Context, list_intervensi: MutableList<TaskPertanyaanModel>, private val onAdapterPertanyaan: OnAdapterPertanyaan) : RecyclerView.Adapter<AdapterTaskPertanyaan.Holder>() {

    lateinit var list_intervensi: MutableList<TaskPertanyaanModel>
    lateinit var context: Context

    init {
        this.context = context
        this.list_intervensi = list_intervensi
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutView: View
        layoutView = LayoutInflater.from(parent.context).inflate(R.layout.adapter_pertanyaan, parent, false)
        return AdapterTaskPertanyaan.Holder(layoutView)
    }

    override fun getItemCount(): Int {
        return list_intervensi.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val intervensiModel = list_intervensi.get(position)
        holder.card_lay!!.setOnClickListener({
            onAdapterPertanyaan.onPertanyaanClick(intervensiModel)
        })
        holder.tv_content!!.setText(intervensiModel.intervensi_task)
        holder.tv_date!!.setText(Function.parseTimestampToSimpleDate(intervensiModel.tanggal_task))
        holder.tv_month_year!!.setText(Function.parseTimestampToSimpleMonthYear(intervensiModel.tanggal_task))


        Log.d("adapters1 ", intervensiModel.status_task)
        if(intervensiModel.status_task.equals("N")){
            holder.tv_time!!.visibility = View.GONE
            Log.d("adapters ", Function.isToday(intervensiModel.tanggal_task))
            if(Function.isToday(intervensiModel.tanggal_task).equals("today")){
//                val drawable = DrawableCompat.wrap(ContextCompat.getDrawable(
//                        context,
//                        R.drawable.ic_play_circle_filled_black_24dp)!!);
//                DrawableCompat.setTint(drawable, context.resources.getColor(R.color.colorPrimary));
//                holder.img_check!!.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.MULTIPLY);
                holder.img_check!!.setImageResource(R.drawable.ic_play_rounded_button);
            } else if(Function.isToday(intervensiModel.tanggal_task).equals("yesterday")){
                holder.img_check!!.setImageResource(R.drawable.ic_error);
            } else if(Function.isToday(intervensiModel.tanggal_task).equals("tomorrow")){
                holder.img_check!!.setImageResource(R.drawable.ic_stop);
            }
        } else {
            Log.d("adapters2 ", intervensiModel.status_task)
//            holder.img_check!!.setImageResource(R.drawable.ic_success)

            if (intervensiModel.status_task == "T") {
                holder.img_check!!.setImageResource(R.drawable.ic_success_grey)
            } else {
                holder.img_check!!.setImageResource(R.drawable.ic_success)
            }

            holder.tv_time!!.visibility = View.VISIBLE
            try {
                holder.tv_time!!.setText(intervensiModel.tanggal_submit.split(" ")[1])
            } catch (e: Exception){
                e.printStackTrace()
            }
//            holder.img_check!!.setColorFilter(ContextCompat.getColor(context, R.color.blue_400), android.graphics.PorterDuff.Mode.MULTIPLY);
        }

    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var tv_content: TextView? = null
        internal var tv_date: TextView? = null
        internal var tv_month_year: TextView? = null
        internal var img_check: ImageView? = null
        internal var tv_time: TextView? = null
        internal var card_lay: CardView? = null
        init {
            this.setIsRecyclable(false)
            tv_content = itemView.findViewById(R.id.tv_content)
            tv_date = itemView.findViewById(R.id.tv_date)
            tv_month_year = itemView.findViewById(R.id.tv_month_year)
            img_check = itemView.findViewById(R.id.img_check)
            tv_time = itemView.findViewById(R.id.tv_time)
            card_lay = itemView.findViewById(R.id.card_lay)

        }
    }

    interface OnAdapterPertanyaan{
        fun onPertanyaanClick(intervensiModel: TaskPertanyaanModel)
    }
}
