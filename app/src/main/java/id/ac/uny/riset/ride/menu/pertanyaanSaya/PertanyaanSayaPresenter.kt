package id.ac.uny.riset.ride.menu.pertanyaanSaya

import android.content.Context
import id.ac.uny.riset.ride.base.BasePresenter
import id.ac.uny.riset.ride.data.EndPoints
import id.ac.uny.riset.ride.data.model.TaskPertanyaanModel
import id.ac.uny.riset.ride.menu.surveySaya.model.SurveySayaModel
import lib.almuwahhid.utils.UiLibRequest
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap

class PertanyaanSayaPresenter (ctx: Context?, view: PertanyaanSayaView.View) : BasePresenter(ctx), PertanyaanSayaView.Presenter {
    lateinit var ctx: Context
    lateinit var view: PertanyaanSayaView.View

    init {
        this.ctx = ctx!!
        this.view = view
    }
    override fun requestPertanyaan(detailSurveySayaModel: SurveySayaModel) {
        UiLibRequest.POST(EndPoints.stringPertanyaanSaya(), context, object : UiLibRequest.OnPostRequest {
            override fun onPreExecuted() {
                view.onLoading()
            }

            override fun onSuccess(response: JSONObject) {
                view.onHideLoading()
                try {
                    if(response.getString("result").equals("success")){
                        var list_detail : MutableList<TaskPertanyaanModel> = ArrayList()
                        var data = response.getJSONArray("data")

                        for (i in 0..(data.length() - 1)) {
                            list_detail!!.add(gson.fromJson(data.get(i).toString(), TaskPertanyaanModel::class.java))
                        }

                        view.onRequestPertanyaan(list_detail)
                    } else {
                        view.onEmptyData()
                    }

                } catch (e: JSONException) {
                    e.printStackTrace()
                    view.onErrorConnection()
                }

            }

            override fun onFailure(error: String) {
                view.onHideLoading()
                view.onErrorConnection()
            }

            override fun requestParam(): Map<String, String> {
                val requestParam = HashMap<String, String>()
                requestParam.put("data", gson.toJson(detailSurveySayaModel))
                return requestParam

            }

            override fun requestHeaders(): Map<String, String>? {
                val requestParam = HashMap<String, String>()
                return requestParam
            }
        })
    }
}