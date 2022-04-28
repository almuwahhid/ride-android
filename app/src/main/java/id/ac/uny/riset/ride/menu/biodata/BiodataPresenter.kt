package id.ac.uny.riset.ride.menu.biodata

import android.content.Context
import id.ac.uny.riset.ride.base.BasePresenter
import id.ac.uny.riset.ride.data.EndPoints
import id.ac.uny.riset.ride.data.model.UserModel
import lib.almuwahhid.utils.UiLibRequest
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap

class BiodataPresenter(context: Context?, view: BiodataView.View) : BasePresenter(context), BiodataView.Presenter {

    internal var view: BiodataView.View? = null
    init {
        this.view = view
    }

    override fun updateUser(userModel: UserModel) {
        UiLibRequest.POST(EndPoints.stringUpdateBiodata(), context, object : UiLibRequest.OnPostRequest {
            override fun onPreExecuted() {
                view!!.onLoading()
            }

            override fun onSuccess(response: JSONObject) {
                view!!.onHideLoading()
                try {
                    if(response.getString("result").equals("success")){
                        view!!.onRequestUser(response.getJSONObject("data").toString())
                    } else {
                        view!!.onErrorConnection()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }

            override fun onFailure(error: String) {
                view!!.onHideLoading()
                view!!.onErrorConnection()
            }

            override fun requestParam(): Map<String, String> {
                val param = HashMap<String, String>()
                param.put("data", gson.toJson(userModel))
                return param
            }

            override fun requestHeaders(): Map<String, String> {
                return HashMap()
            }
        })
    }
}