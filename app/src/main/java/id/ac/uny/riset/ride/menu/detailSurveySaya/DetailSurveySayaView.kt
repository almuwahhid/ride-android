package id.ac.uny.riset.ride.menu.detailSurveySaya

import id.ac.uny.riset.ride.base.BaseView
import id.ac.uny.riset.ride.menu.detailSurveySaya.model.DetailSurveySayaModel
import id.ac.uny.riset.ride.menu.surveySaya.model.SurveySayaModel

interface DetailSurveySayaView {
    interface Presenter {
        fun requestSurveySaya(surveySayaModel: SurveySayaModel)
    }

    interface View : BaseView {
        fun onRequestSurveySaya(surveySayaModelList: List<DetailSurveySayaModel>)
        fun onEmptyData()
    }
}