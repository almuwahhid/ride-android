package id.ac.uny.riset.ride.menu.pertanyaanSaya

import id.ac.uny.riset.ride.base.BaseView
import id.ac.uny.riset.ride.data.model.TaskPertanyaanModel
import id.ac.uny.riset.ride.menu.surveySaya.model.SurveySayaModel

interface PertanyaanSayaView {
    interface Presenter {
        fun requestPertanyaan(detailSurveySayaModel: SurveySayaModel)
    }

    interface View : BaseView {
        fun onRequestPertanyaan(pertanyaanSaya: List<TaskPertanyaanModel>)
        fun onEmptyData()
    }
}