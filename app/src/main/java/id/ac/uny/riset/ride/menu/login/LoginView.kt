package id.ac.uny.riset.ride.menu.login

import id.ac.uny.riset.ride.base.BaseView
import id.ac.uny.riset.ride.data.model.UserModel
import id.ac.uny.riset.ride.menu.login.model.LoginUiModel

interface LoginView {
    interface Presenter{
        abstract fun requestLogin(loginUiModel: LoginUiModel)
    }

    interface View: BaseView{
        abstract fun onSuccessLogin(model: UserModel)
        abstract fun onFailedLogin(message: String)
    }
}