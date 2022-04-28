package id.ac.uny.riset.ride.menu.main

import android.content.Context
import android.util.Log
import id.ac.uny.riset.ride.base.BasePresenter
import id.ac.uny.riset.ride.data.EndPoints
import id.ac.uny.riset.ride.data.model.SurveyModel
import id.ac.uny.riset.ride.data.model.TaskPertanyaanModel
import id.ac.uny.riset.ride.utils.Function
import lib.almuwahhid.utils.UiLibRequest
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap


class MainPresenter(context: Context?, view: MainView.View) : BasePresenter(context), MainView.Presenter {

    lateinit var view: MainView.View

    init {
        this.view = view
    }

    override fun createSurvey() {
        UiLibRequest.POST(EndPoints.stringMakeSurvey(), context, object : UiLibRequest.OnPostRequest {
            override fun onPreExecuted() {
                view!!.onLoading()
            }

            override fun onSuccess(response: JSONObject) {
                view!!.onHideLoading()
                try {
                    if(response.getString("result").equals("success")){
                        view.onCreateSurvey(gson.fromJson(response.getJSONObject("data").toString(), SurveyModel::class.java))
                    } else {

                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }

            override fun onFailure(error: String) {
                view!!.onHideLoading()
                view!!.onFailed("Bermasalah dengan Server")
            }

            override fun requestParam(): Map<String, String> {
                val param = HashMap<String, String>()
                param.put("data", gson.toJson(Function.getUserPreference(context)))
                return param
            }

            override fun requestHeaders(): Map<String, String> {
                return HashMap()
            }
        })
    }

    override fun checkSurvey() {
        UiLibRequest.POST(EndPoints.stringCheckSurvey(), context, object : UiLibRequest.OnPostRequest {
            override fun onPreExecuted() {
                view!!.onLoading()
            }

            override fun onSuccess(response: JSONObject) {
                view!!.onHideLoading()
                try {

                    view.onCheckNilai(response.getString("nilai"))

                    when(response.getString("result")){
                        "survey" -> {
                            var intervensis: MutableList<TaskPertanyaanModel> = ArrayList()
                            val array_intervensi: JSONArray = response.getJSONArray("intervensi")

                            for (i in 0..(array_intervensi.length() - 1)) {
                                intervensis!!.add(gson.fromJson(array_intervensi.get(i).toString(), TaskPertanyaanModel::class.java))
                            }

                            Log.d("contoh ", ""+intervensis.size)
                            view.onDoSurvey(
                                    gson.fromJson(response.getString("data"), SurveyModel::class.java),
                                    intervensis
                            )
                        }
                        "new" -> {
                            view.onStartSurvey()
                        }
                        "second" -> {
                            view.onAfterSecondSurvey()
                        }
                        "first" -> {
                            view.onAfterFirstSurvey()
                        }
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }

            override fun onFailure(error: String) {
                view!!.onHideLoading()
                view!!.onFailed("Bermasalah dengan Server")
            }

            override fun requestParam(): Map<String, String> {
                val param = HashMap<String, String>()
                param.put("data", gson.toJson(Function.getUserPreference(context)))
                return param
            }

            override fun requestHeaders(): Map<String, String> {
                return HashMap()
            }
        })
    }

    override fun confirmTask(taskIntervensiModel: TaskPertanyaanModel) {
        UiLibRequest.POST(EndPoints.stringCheckSurvey(), context, object : UiLibRequest.OnPostRequest {
            override fun onPreExecuted() {
                view!!.onLoading()
            }

            override fun onSuccess(response: JSONObject) {
                view!!.onHideLoading()
                try {
                    when(response.getString("result")){
                        "survey" -> {
                            var intervensis: MutableList<TaskPertanyaanModel> = ArrayList()
                            val array_intervensi: JSONArray = response.getJSONArray("intervensi")

                            for (i in 0..(array_intervensi.length() - 1)) {
                                intervensis!!.add(gson.fromJson(array_intervensi.get(i).toString(), TaskPertanyaanModel::class.java))
                            }
                            view.onDoSurvey(
                                    gson.fromJson(response.getString("data"), SurveyModel::class.java),
                                    intervensis
                            )
                        }
                        "new" -> {
                            view.onStartSurvey()
                        }
                        "second" -> {
                            view.onAfterSecondSurvey()
                        }
                        "first" -> {
                            view.onAfterFirstSurvey()
                        }
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }

            override fun onFailure(error: String) {
                view!!.onHideLoading()
                view!!.onFailed("Bermasalah dengan Server")
            }

            override fun requestParam(): Map<String, String> {
                val param = HashMap<String, String>()
                return param
            }

            override fun requestHeaders(): Map<String, String> {
                return HashMap()
            }
        })
    }
}