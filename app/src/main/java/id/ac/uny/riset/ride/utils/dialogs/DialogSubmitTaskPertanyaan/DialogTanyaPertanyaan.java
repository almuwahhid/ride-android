package id.ac.uny.riset.ride.utils.dialogs.DialogSubmitTaskPertanyaan;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import id.ac.uny.riset.ride.R;
import lib.almuwahhid.utils.DialogBuilder;

public class DialogTanyaPertanyaan extends DialogBuilder {

    RelativeLayout lay_dialog;
    TextView tv_taskintervensi, tv_tanya;
    OnDialogTanyaPertanyaan onDialogTanyaPertanyaan;

    Button btn_ya, btn_no;

    public DialogTanyaPertanyaan(Context context, final String pertanyaan, final OnDialogTanyaPertanyaan onDialogTanyaPertanyaan) {
        super(context, R.layout.dialog_tanya_pertanyaan);
        this.onDialogTanyaPertanyaan = onDialogTanyaPertanyaan;

        initComponent(new OnInitComponent() {
            @Override
            public void initComponent(Dialog dialog) {
                btn_no = dialog.findViewById(R.id.btn_no);
                btn_ya = dialog.findViewById(R.id.btn_ya);
                tv_tanya = dialog.findViewById(R.id.tv_tanya);
                lay_dialog = dialog.findViewById(R.id.lay_dialog);
                setFullWidth(lay_dialog);
                setAnimation(R.style.DialogBottomAnimation);
                setGravity(Gravity.BOTTOM);

                tv_tanya.setText(pertanyaan);

                btn_no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onDialogTanyaPertanyaan.onTidak();
                        dismiss();
                    }
                });

                btn_ya.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onDialogTanyaPertanyaan.onYa();
                        dismiss();
                    }
                });
            }
        });
        show();
    }

    public interface OnDialogTanyaPertanyaan{
        void onYa();
        void onTidak();
    }
}
