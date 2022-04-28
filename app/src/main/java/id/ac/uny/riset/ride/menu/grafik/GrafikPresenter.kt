package id.ac.uny.riset.ride.menu.grafik

import android.content.Context
import id.ac.uny.riset.ride.base.BasePresenter
import id.ac.uny.riset.ride.data.EndPoints
import id.ac.uny.riset.ride.menu.grafik.model.GrafikAspekUiModel
import id.ac.uny.riset.ride.menu.grafik.model.GrafikUiModel
import id.ac.uny.riset.ride.utils.Function
import lib.almuwahhid.utils.UiLibRequest
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap

class GrafikPresenter (ctx: Context?, view: GrafikView.View) : BasePresenter(ctx), GrafikView.Presenter {

    lateinit var ctx: Context
    lateinit var view: GrafikView.View

    init {
        this.ctx = ctx!!
        this.view = view
    }

    override fun requestGrafikNilai() {
        UiLibRequest.POST(EndPoints.stringNilaiSurvey(), context, object : UiLibRequest.OnPostRequest {
            override fun onPreExecuted() {
                view.onLoadingChart1()
            }

            override fun onSuccess(response: JSONObject) {
                view.onHideLoadingChart1()
                try {
                    var list_detail : MutableList<GrafikUiModel> = ArrayList()
                    var data = response.getJSONArray("data")

                    for (i in 0..(data.length() - 1)) {
                        list_detail!!.add(gson.fromJson(data.get(i).toString(), GrafikUiModel::class.java))
                    }

                    view.onRequestGrafikNilai(list_detail)
                    requestGrafikAspek()

                } catch (e: JSONException) {
                    e.printStackTrace()
                    view.onErrorConnection()
                }

            }

            override fun onFailure(error: String) {
                view.onHideLoadingChart1()
                view.onErrorConnection()
            }

            override fun requestParam(): Map<String, String> {
                val requestParam = HashMap<String, String>()
                requestParam.put("data", gson.toJson(Function.getUserPreference(context)))
                return requestParam

            }

            override fun requestHeaders(): Map<String, String>? {
                val requestParam = HashMap<String, String>()
                return requestParam
            }
        })
    }

    override fun requestGrafikAspek() {
        UiLibRequest.POST(EndPoints.stringNilaiSurveyByAspek(), context, object : UiLibRequest.OnPostRequest {
            override fun onPreExecuted() {
                view.onLoadingChart2()
            }

            override fun onSuccess(response: JSONObject) {
                view.onHideLoadingChart2()
                try {
                    if(response.getString("result").equals("success")){
                        var list_detail : MutableList<GrafikAspekUiModel> = ArrayList()
                        var data = response.getJSONArray("data")

                        for (i in 0..(data.length() - 1)) {
                            list_detail!!.add(gson.fromJson(data.get(i).toString(), GrafikAspekUiModel::class.java))
                        }

                        view.onRequestGrafikAspek(list_detail)
                    } else {
                        view.onFailedLoadGrafik("Bermasalah dengan server")
                    }

                } catch (e: JSONException) {
                    e.printStackTrace()
                    view.onErrorConnection()
                }

            }

            override fun onFailure(error: String) {
                view.onHideLoadingChart2()
                view.onErrorConnection()
            }

            override fun requestParam(): Map<String, String> {
                val requestParam = HashMap<String, String>()
                requestParam.put("data", gson.toJson(Function.getUserPreference(context)))
                return requestParam

            }

            override fun requestHeaders(): Map<String, String>? {
                val requestParam = HashMap<String, String>()
                return requestParam
            }
        })
    }
}