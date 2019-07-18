package id.ac.uny.riset.ride.menu.main

import id.ac.uny.riset.ride.base.BaseView
import id.ac.uny.riset.ride.data.model.SurveyModel
import id.ac.uny.riset.ride.data.model.TaskPertanyaanModel

interface MainView {
    interface Presenter{
        fun checkSurvey()
        fun confirmTask(taskIntervensiModel: TaskPertanyaanModel)
        fun createSurvey()
    }

    interface View: BaseView{
        fun onAfterFirstSurvey()
        fun onAfterSecondSurvey()
        fun onStartSurvey()
        fun onDoSurvey(surveyModel: SurveyModel, taskIntervensiModels: MutableList<TaskPertanyaanModel>)
        fun onConfirmTask(taskIntervensiModel: TaskPertanyaanModel)
        fun onFailed(message: String)
        fun onCreateSurvey(surveyModel: SurveyModel)
        fun onCheckNilai(nilai: String)
    }
}