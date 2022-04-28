package id.ac.uny.riset.ride.utils.dialogs.DialogSubmitTaskPertanyaan;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import id.ac.uny.riset.ride.R;
import lib.almuwahhid.utils.DialogBuilder;

public class DialogKeteranganPertanyaan extends DialogBuilder {
    RelativeLayout lay_dialog;
    EditText edt_komentar;
    Button btn_submit;
    OnDialogKeteranganPertanyaan onDialogKomentarPertanyaan;

    public DialogKeteranganPertanyaan(Context context, final OnDialogKeteranganPertanyaan onDialogKomentarPertanyaan) {
        super(context, R.layout.dialog_kterangan_pertanyaan);
        this.onDialogKomentarPertanyaan = onDialogKomentarPertanyaan;

        initComponent(new OnInitComponent() {
            @Override
            public void initComponent(Dialog dialog) {
                lay_dialog = dialog.findViewById(R.id.lay_dialog);
                edt_komentar = dialog.findViewById(R.id.edt_komentar);
                btn_submit = dialog.findViewById(R.id.btn_submit);

                setFullWidth(lay_dialog);
                setGravity(Gravity.BOTTOM);
                setAnimation(R.style.DialogBottomAnimation);
                setCancellable(false);

                btn_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(edt_komentar.getText().toString().equals("")){
                            edt_komentar.setError("Keterangan harus diisi");
                        } else {
                            dismiss();
                            onDialogKomentarPertanyaan.onSubmitKeterangan(edt_komentar.getText().toString());
                        }
                    }
                });

            }
        });
        show();
    }

    public interface OnDialogKeteranganPertanyaan{
        void onSubmitKeterangan(String komentar);
    }
}
