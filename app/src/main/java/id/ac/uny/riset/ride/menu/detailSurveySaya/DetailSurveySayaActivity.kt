package id.ac.uny.riset.ride.menu.detailSurveySaya

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import id.ac.uny.riset.ride.R
import id.ac.uny.riset.ride.base.BaseActivity
import id.ac.uny.riset.ride.menu.detailSurveySaya.adapter.DetailSurveySayaAdapter
import id.ac.uny.riset.ride.menu.detailSurveySaya.model.DetailSurveySayaModel
import id.ac.uny.riset.ride.menu.pertanyaanSaya.PertanyaanSayaActivity
import id.ac.uny.riset.ride.menu.surveySaya.model.SurveySayaModel
import id.ac.uny.riset.ride.utils.Function
import id.ac.uny.riset.ride.utils.dialogs.DialogSurveyResult.DialogSurveyResult
import kotlinx.android.synthetic.main.activity_detail_survey_saya.*
import kotlinx.android.synthetic.main.layout_helper.*
import kotlinx.android.synthetic.main.main_toolbar.*

class DetailSurveySayaActivity : BaseActivity(), DetailSurveySayaView.View {

    lateinit var surveySayaModel: SurveySayaModel
    lateinit var detailSurveySayaPresenter: DetailSurveySayaPresenter
    lateinit var detalSurveySayaAdapter: DetailSurveySayaAdapter
    var detailSurveySayaModelList: MutableList<DetailSurveySayaModel> = ArrayList()

    var isDetailSurvey = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_survey_saya)

        if(intent.hasExtra("data")){
            surveySayaModel = intent.getSerializableExtra("data") as SurveySayaModel
        } else {
            finish()
        }

        setSupportActionBar(toolbar)
        supportActionBar!!.setTitle(Function.parseTimestampToDate(surveySayaModel.tanggal_survey))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        lay_survey.setInOutAnimation(R.anim.pull_in_bottom, R.anim.push_out_top)

        detailSurveySayaPresenter = DetailSurveySayaPresenter(context, this)
        detailSurveySayaPresenter.requestSurveySaya(surveySayaModel)

        tv_survey.setText(surveySayaModel.deskripsi_status)

        btn_detail.setOnClickListener({
            if(isDetailSurvey){
                lay_survey.hide()
                img_cont.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp)
            } else {
                img_cont.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp)
                lay_survey.show()
            }
            isDetailSurvey = !isDetailSurvey
        })

        detalSurveySayaAdapter = DetailSurveySayaAdapter(context, detailSurveySayaModelList, object : DetailSurveySayaAdapter.OnDetailSurveySayaAdapter{
            override fun onDetailClick(model: DetailSurveySayaModel) {
//                DialogDetailSurveySaya(context, model)
            }
        })
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = detalSurveySayaAdapter

        card_hasil.setOnClickListener({
            DialogSurveyResult(context, surveySayaModel)
        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            android.R.id.home -> {
                finish()
            }
            R.id.action_task -> {
                startActivity(Intent(context, PertanyaanSayaActivity::class.java).putExtra("data", surveySayaModel))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRequestSurveySaya(surveySayaModelList: List<DetailSurveySayaModel>) {
        this.detailSurveySayaModelList.clear()
        this.detailSurveySayaModelList.addAll(surveySayaModelList)
        detalSurveySayaAdapter.notifyDataSetChanged()
    }

    override fun onLoading() {
        helper_nodata.visibility = View.GONE
        helper_error.visibility = View.GONE
        helper_loading.visibility = View.VISIBLE
    }

    override fun onHideLoading() {
        helper_loading.visibility = View.GONE
    }

    override fun onErrorConnection() {
        helper_error.visibility = View.VISIBLE
    }

    override fun onEmptyData() {
        helper_nodata.visibility = View.VISIBLE
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.survey_menu, menu)
        return true
    }


    private fun initDetailSurvey(){

    }
}
