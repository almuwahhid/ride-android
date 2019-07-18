package id.ac.uny.riset.ride.menu.register

import id.ac.uny.riset.ride.base.BaseView
import id.ac.uny.riset.ride.data.model.UserModel

interface RegisterView {
    interface Presenter{
        fun submitRegister(userModel: UserModel)
    }

    interface View: BaseView{
        fun onSubmitRegister(isSuccess: Boolean, message: String)
    }
}