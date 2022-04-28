package id.ac.uny.riset.ride.menu.grafik

import id.ac.uny.riset.ride.base.BaseView
import id.ac.uny.riset.ride.menu.grafik.model.GrafikAspekUiModel
import id.ac.uny.riset.ride.menu.grafik.model.GrafikUiModel

interface GrafikView {
    interface Presenter{
        fun requestGrafikNilai()
        fun requestGrafikAspek()
    }

    interface View: BaseView{
        fun onRequestGrafikNilai(list: MutableList<GrafikUiModel>)
        fun onRequestGrafikAspek(list: MutableList<GrafikAspekUiModel>)
        fun onFailedLoadGrafik(message: String)
        fun onLoadingChart1()
        fun onLoadingChart2()
        fun onHideLoadingChart1()
        fun onHideLoadingChart2()
    }
}