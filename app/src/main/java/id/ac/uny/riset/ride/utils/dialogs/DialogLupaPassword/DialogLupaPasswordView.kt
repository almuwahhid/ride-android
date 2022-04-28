package id.ac.uny.riset.ride.utils.dialogs.DialogLupaPassword

import id.ac.uny.riset.ride.base.BaseView

interface DialogLupaPasswordView {
    interface Presenter{
        fun requestLupaPassword(email: String)
    }

    interface View: BaseView{
        fun onRequestLupaPassword(isExist: Boolean)
    }
}