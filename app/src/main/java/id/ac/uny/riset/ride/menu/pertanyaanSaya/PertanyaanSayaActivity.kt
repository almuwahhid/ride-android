package id.ac.uny.riset.ride.menu.pertanyaanSaya

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import id.ac.uny.riset.ride.R
import id.ac.uny.riset.ride.base.BaseActivity
import id.ac.uny.riset.ride.data.model.TaskPertanyaanModel
import id.ac.uny.riset.ride.menu.main.adapter.AdapterTaskPertanyaan
import id.ac.uny.riset.ride.menu.surveySaya.model.SurveySayaModel
import id.ac.uny.riset.ride.utils.dialogs.DialogDetailPertanyaanSaya.DialogDetailPertanyaanSaya
import kotlinx.android.synthetic.main.activity_detail_survey_saya.*
import kotlinx.android.synthetic.main.layout_helper.*
import kotlinx.android.synthetic.main.main_toolbar.*

class PertanyaanSayaActivity : BaseActivity(), PertanyaanSayaView.View {

    lateinit var adapterTaskPertanyaan: AdapterTaskPertanyaan
    var list_task : MutableList<TaskPertanyaanModel> = ArrayList()
    lateinit var presenter: PertanyaanSayaPresenter
    lateinit var surveySayaModel: SurveySayaModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pertanyaan_saya)

        setSupportActionBar(toolbar)
        supportActionBar!!.setTitle("Task Intervensi")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        if(intent.hasExtra("data")){
            surveySayaModel = intent.getSerializableExtra("data") as SurveySayaModel
        } else {
            finish()
        }

        adapterTaskPertanyaan = AdapterTaskPertanyaan(context, list_task, object : AdapterTaskPertanyaan.OnAdapterPertanyaan{
            override fun onPertanyaanClick(intervensiModel: TaskPertanyaanModel) {
                DialogDetailPertanyaanSaya(context, intervensiModel)
            }
        })
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = adapterTaskPertanyaan

        presenter = PertanyaanSayaPresenter(context, this)
        presenter!!.requestPertanyaan(surveySayaModel)
    }

    override fun onRequestPertanyaan(pertanyaanSaya: List<TaskPertanyaanModel>) {
        this.list_task.clear()
        this.list_task.addAll(pertanyaanSaya)
        adapterTaskPertanyaan.notifyDataSetChanged()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
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
}

