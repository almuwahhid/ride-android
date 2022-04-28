package id.ac.uny.riset.ride.utils.dialogs.DialogDetailSurveySaya;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import id.ac.uny.riset.ride.R;
import id.ac.uny.riset.ride.menu.detailSurveySaya.model.DetailSurveySayaModel;
import lib.almuwahhid.utils.DialogBuilder;

public class DialogDetailSurveySaya extends DialogBuilder {
    RelativeLayout lay_dialog;
    TextView tv_skor;
    TextView tv_content;
    ImageView img_close;
    DetailSurveySayaModel detailSurveySayaModel;


    public DialogDetailSurveySaya(Context context, final DetailSurveySayaModel detailSurveySayaModel) {
        super(context, R.layout.dialog_detail_survey_saya);
        this.detailSurveySayaModel = detailSurveySayaModel;

        initComponent(new OnInitComponent() {
            @Override
            public void initComponent(Dialog dialog) {
                tv_skor = dialog.findViewById(R.id.tv_skor);
                lay_dialog = dialog.findViewById(R.id.lay_dialog);
                tv_content = dialog.findViewById(R.id.tv_content);
                img_close = dialog.findViewById(R.id.img_close);

                img_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismiss();
                    }
                });

                setAnimation(R.style.DialogBottomAnimation);
                setFullWidth(lay_dialog);
                setGravity(Gravity.BOTTOM);

                tv_skor.setText(detailSurveySayaModel.getNilai_pertanyaan());
                tv_content.setText(detailSurveySayaModel.getNama_pernyataan());
            }

        });

        show();
    }
}
