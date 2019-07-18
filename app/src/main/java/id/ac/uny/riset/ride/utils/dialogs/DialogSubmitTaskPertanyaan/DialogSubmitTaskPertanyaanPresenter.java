package id.ac.uny.riset.ride.utils.dialogs.DialogSubmitTaskPertanyaan;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import id.ac.uny.riset.ride.base.BasePresenter;
import id.ac.uny.riset.ride.data.EndPoints;
import id.ac.uny.riset.ride.data.model.TaskPertanyaanModel;
import id.ac.uny.riset.ride.utils.Function;
import lib.almuwahhid.utils.UiLibRequest;

public class DialogSubmitTaskPertanyaanPresenter extends BasePresenter implements DialogSubmitTaskPertanyaanView.Presenter{

    DialogSubmitTaskPertanyaanView.View view;

    public DialogSubmitTaskPertanyaanPresenter(Context context, DialogSubmitTaskPertanyaanView.View view) {
        super(context);
        this.view = view;
    }

    @Override
    public void submitTaskPertanyaan(final TaskPertanyaanModel taskIntervensiModel) {
        UiLibRequest.POST(EndPoints.stringUpdateTaskPertanyaan(), getContext(), new UiLibRequest.OnPostRequest() {
            @Override
            public void onPreExecuted() {
                view.onLoading();
            }

            @Override
            public void onSuccess(JSONObject response) {
                view.onHideLoading();
                try {
                    if(response.getString("result").equals("success")){

                        if(response.getBoolean("done")){
                            view.onSubmitTaskPertanyaan(true, "Berhasil submit", true);
                        } else {
                            view.onSubmitTaskPertanyaan(true, "Berhasil submit", false);
                        }
                    } else {
                        view.onSubmitTaskPertanyaan(false, "Ada yang bermasalah dengan service", false);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    view.onSubmitTaskPertanyaan(false, "Ada yang bermasalah dengan service", false);
                }
            }

            @Override
            public void onFailure(String error) {
                view.onHideLoading();
                view.onErrorConnection();
            }

            @Override
            public Map<String, String> requestParam() {
                Map<String, String> requestParam = new HashMap<>();
                requestParam.put("data", getGson().toJson(taskIntervensiModel));
                requestParam.put("user", getGson().toJson(Function.getUserPreference(getContext())));
                return requestParam;
            }

            @Override
            public Map<String, String> requestHeaders() {
                Map<String, String> requestParam = new HashMap<>();
                return requestParam;
            }
        });
    }
}
