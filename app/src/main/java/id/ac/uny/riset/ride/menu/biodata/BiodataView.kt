package id.ac.uny.riset.ride.menu.biodata

import id.ac.uny.riset.ride.base.BaseView
import id.ac.uny.riset.ride.data.model.UserModel

interface BiodataView {
    interface Presenter{
        fun updateUser(userModel: UserModel)
    }

    interface View : BaseView{
        fun onRequestUser(userModel: String)
    }
}