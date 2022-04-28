package id.ac.uny.riset.ride.utils.dialogs.DialogSurveyResult;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import id.ac.uny.riset.ride.R;
import id.ac.uny.riset.ride.menu.surveySaya.model.SurveySayaModel;
import lib.almuwahhid.utils.DialogBuilder;

public class DialogSurveyResult extends DialogBuilder {

    RelativeLayout lay_dialog;
    ImageView img_close;
    TextView tv_content;

    public DialogSurveyResult(Context context, final SurveySayaModel surveySayaModel) {
        super(context, R.layout.dialog_survey_result);

        initComponent(new OnInitComponent() {
            @Override
            public void initComponent(Dialog dialog) {
                lay_dialog = dialog.findViewById(R.id.lay_dialog);
                img_close = dialog.findViewById(R.id.img_close);
                tv_content = dialog.findViewById(R.id.tv_content);

                setAnimation(R.style.DialogBottomAnimation);
                setFullWidth(lay_dialog);
                setGravity(Gravity.BOTTOM);

                img_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismiss();
                    }
                });

                tv_content.setText(surveySayaModel.getDeskripsi_status());
            }
        });

        show();
    }
}
