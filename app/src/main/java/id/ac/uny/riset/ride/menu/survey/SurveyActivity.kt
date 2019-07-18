package id.ac.uny.riset.ride.menu.survey

import android.content.Intent
import android.os.Bundle
import android.view.View
import id.ac.uny.riset.ride.R
import id.ac.uny.riset.ride.base.BaseActivity
import id.ac.uny.riset.ride.data.model.*
import id.ac.uny.riset.ride.menu.main.MainActivity
import id.ac.uny.riset.ride.menu.survey.helper.SurveyHelper
import id.ac.uny.riset.ride.utils.Function
import id.ac.uny.riset.ride.utils.dialogs.DialogConfirm.DialogConfirm
import id.ac.uny.riset.ride.utils.dialogs.dialogPicker.DialogPicker
import kotlinx.android.synthetic.main.activity_survey.*
import kotlinx.android.synthetic.main.main_toolbar.*
import lib.almuwahhid.utils.LibUi

class SurveyActivity : BaseActivity(), SurveyInterface.View {

    var presenter: SurveyPresenter? = null

    var surveyModel: SurveyModel? = null
    var list_pernyataan: MutableList<PernyataanModel>? = null
    var index: Int = 0
    var pernyataanModel: PernyataanModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey)

        list_pernyataan = ArrayList()
        presenter = SurveyPresenter(context, this)

        if(intent.hasExtra("data")){
            surveyModel = intent.getSerializableExtra("data") as SurveyModel?
        }

        setSupportActionBar(toolbar)
        supportActionBar!!.setTitle("Berikan Responmu!")
        tv_date_survey.setText(Function.parseTimestampToDate(surveyModel!!.tanggal_survey))

        lay_pertanyaan.setInOutAnimation(R.anim.pull_in_right, R.anim.push_out_left)
        presenter!!.requestPernyataan()
    }

    override fun onRequestPernyataan(modelList: List<PernyataanModel>?) {
        list_pernyataan!!.addAll(modelList!!)
        index = 0
        showPertanyaan(list_pernyataan!!.get(index), index)
    }

    override fun onLoading() {
        LibUi.showLoadingDialog(this, R.drawable.ic_sand_clock)
    }

    override fun onHideLoading() {
        LibUi.hideLoadingDialog(context)
    }

    override fun onErrorConnection() {
        LibUi.ToastShort(context, "Ada yang bermasalah dengan server")
    }

    override fun onSubmitPertanyaan(isIndikatorDone: Boolean, isSurveyDone: Boolean, indikator_message: String?, survey_message: String?) {
        index++
        lay_process.visibility = View.GONE
        edt_tv_pertanyaan.setText("")
        if(isIndikatorDone){
            DialogConfirm(context, indikator_message, "Saya Paham", R.drawable.ic_assigment, object : DialogConfirm.OnDialogConfirm{
                override fun onDialogConfirmClick() {
                    if(isSurveyDone){
                        DialogConfirm(context, survey_message, "Terima Kasih", R.drawable.ic_success, object : DialogConfirm.OnDialogConfirm{
                            override fun onDialogConfirmClick() {
                                DialogConfirm(context, "Bagus, Rider! Kamu telah selesai merespon. Apakah kamu akan melanjutkan untuk memulai intervensi!", "Okay, Lihat tugas", R.drawable.ic_rating, object : DialogConfirm.OnDialogConfirm{
                                    override fun onDialogConfirmClick() {
                                        startActivity(Intent(context, MainActivity::class.java))
                                        finish()
                                    }

                                    override fun onCancelConfirmClick() {
                                        startActivity(Intent(context, MainActivity::class.java))
                                        finish()
                                    }
                                }).setCancellable(false)
                            }

                            override fun onCancelConfirmClick() {
                                startActivity(Intent(context, MainActivity::class.java))
                                finish()
                            }
                        }).setCancellable(false)
                    }
                }
                override fun onCancelConfirmClick() {

                }
            }).setCancellable(false)
        }
        if(index<list_pernyataan!!.size)
            showPertanyaan(list_pernyataan!!.get(index), index)
    }

    override fun onFailedSubmitPertanyaan(message: String?) {
        LibUi.ToastShort(context, message)
    }

    private fun showPertanyaan(pernyataanModel: PernyataanModel, index: Int){
        this.pernyataanModel = pernyataanModel
        tv_index.setText(""+(index+1))
        lay_pertanyaan.hideForAWhile(this)
        tv_pertanyaan.setText(pernyataanModel.nama_pernyataan)
        edt_tv_pertanyaan.setText("")
        lay_process.visibility = View.GONE
        edt_tv_pertanyaan.setOnClickListener({
            DialogPicker(context, SurveyHelper.convertFromPernyataan(pernyataanModel.nilai), object : DialogPicker.OnDialogPicker{
                override fun onDialogClicked(model: PickerModel?) {
                    edt_tv_pertanyaan.setText(model!!.name)
                    lay_process.visibility = View.VISIBLE
                    fab_submit.setOnClickListener({
                        presenter!!.submitPertanyaan(PertanyaanSurveyModel(surveyModel!!.id_survey, pernyataanModel.id_pernyataan, model!!.other, model!!.name),
                                                        pernyataanModel)
                    })
                }
            })
        })
    }
}
