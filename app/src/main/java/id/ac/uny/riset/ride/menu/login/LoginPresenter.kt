package id.ac.uny.riset.ride.menu.login

import android.content.Context
import id.ac.uny.riset.ride.base.BasePresenter
import id.ac.uny.riset.ride.data.EndPoints
import id.ac.uny.riset.ride.data.model.UserModel
import id.ac.uny.riset.ride.menu.login.model.LoginUiModel
import lib.almuwahhid.utils.LibConstant
import lib.almuwahhid.utils.UiLibRequest
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap

class LoginPresenter(context: Context?, view: LoginView.View) : BasePresenter(context), LoginView.Presenter {
    internal var view: LoginView.View? = null
    init {
        this.view = view
    }

    override fun requestLogin(loginUiModel: LoginUiModel) {
        UiLibRequest.POST(EndPoints.stringLogin(), context, object : UiLibRequest.OnPostRequest {
            override fun onPreExecuted() {
                view!!.onLoading()
            }

            override fun onSuccess(response: JSONObject) {
                view!!.onHideLoading()
                try {
                    if (response.getInt("status") == LibConstant.CODE_SUCCESS) {
                        view!!.onSuccessLogin(gson!!.fromJson(response.getJSONObject("data").toString(), UserModel::class.java))
                    } else {
                        view!!.onFailedLogin(response.getString("message"))
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }

            override fun onFailure(error: String) {
                view!!.onHideLoading()
                view!!.onFailedLogin("Bermasalah dengan Server")
            }

            override fun requestParam(): Map<String, String> {
                val param = HashMap<String, String>()
                param["data"] = gson.toJson(loginUiModel)
                return param
            }

            override fun requestHeaders(): Map<String, String> {
                return HashMap()
            }
        })
    }
}