package id.ac.uny.riset.ride.utils.dialogs.DialogSubmitTaskPertanyaan;

import id.ac.uny.riset.ride.base.BaseView;
import id.ac.uny.riset.ride.data.model.TaskPertanyaanModel;

public interface DialogSubmitTaskPertanyaanView {
    interface Presenter{
        void submitTaskPertanyaan(TaskPertanyaanModel taskIntervensiModel);
    }

    interface View extends BaseView {
        void onSubmitTaskPertanyaan(boolean isSuccess, String message, boolean isDone);
    }
}
