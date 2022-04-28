package id.ac.uny.riset.ride.menu.surveySaya;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.ac.uny.riset.ride.base.BasePresenter;
import id.ac.uny.riset.ride.data.EndPoints;
import id.ac.uny.riset.ride.menu.surveySaya.model.SurveySayaModel;
import id.ac.uny.riset.ride.utils.Function;
import lib.almuwahhid.utils.UiLibRequest;

public class SurveySayaPresenter extends BasePresenter implements SurveySayaView.Presenter{
    SurveySayaView.View view;

    public SurveySayaPresenter(Context context, SurveySayaView.View view) {
        super(context);
        this.view = view;
    }

    @Override
    public void requestSurveySaya() {
        UiLibRequest.POST(EndPoints.stringSurveySaya(), getContext(), new UiLibRequest.OnPostRequest() {
            @Override
            public void onPreExecuted() {
                view.onLoading();
            }

            @Override
            public void onSuccess(JSONObject response) {
                view.onHideLoading();
                try {
                    if(response.getString("result").equals("success")){
                        List<SurveySayaModel> surveySayaViewList = new ArrayList<>();
                        JSONArray data = response.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            surveySayaViewList.add(getGson().fromJson(data.getJSONObject(i).toString(), SurveySayaModel.class));
                        }
                        view.onRequestSurveySaya(surveySayaViewList);
                    } else {

                    }
                } catch (JSONException e){
                    e.printStackTrace();
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
                requestParam.put("data", getGson().toJson(Function.getUserPreference(getContext())));
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
