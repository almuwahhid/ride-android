package id.ac.uny.riset.ride.menu.survey;

import java.util.List;

import id.ac.uny.riset.ride.base.BaseView;
import id.ac.uny.riset.ride.data.model.PernyataanModel;
import id.ac.uny.riset.ride.data.model.PertanyaanSurveyModel;

public interface SurveyInterface {
    interface Presenter{
        void submitPertanyaan(PertanyaanSurveyModel pertanyaanSurveyModel, PernyataanModel pernyataanModel);
        void requestPernyataan();

    }
    interface View extends BaseView{
        void onSubmitPertanyaan(boolean isIndikatorDone, boolean isSurveyDone, String indikator_message, String survey_message);
        void onRequestPernyataan(List<PernyataanModel> modelList);
        void onFailedSubmitPertanyaan(String message);
    }
}
