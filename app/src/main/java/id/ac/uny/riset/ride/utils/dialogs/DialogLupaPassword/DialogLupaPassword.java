package id.ac.uny.riset.ride.utils.dialogs.DialogLupaPassword;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import id.ac.uny.riset.ride.R;
import lib.almuwahhid.utils.DialogBuilder;
import lib.almuwahhid.utils.LibUi;

public class DialogLupaPassword extends DialogBuilder implements DialogLupaPasswordView.View{
    EditText edt_picker;
    ImageView img_ok;
    RelativeLayout lay_dialog;
    DialogLupaPasswordPresenter presenter;

    public DialogLupaPassword(Context context) {
        super(context, R.layout.dialog_lupa_password);
        presenter = new DialogLupaPasswordPresenter(getContext(), this);
        initComponent(new OnInitComponent() {
            @Override
            public void initComponent(Dialog dialog) {
                img_ok = dialog.findViewById(R.id.img_ok);
                edt_picker = dialog.findViewById(R.id.edt_picker);
                lay_dialog = dialog.findViewById(R.id.lay_dialog);

                setAnimation(R.style.DialogBottomAnimation);
                setFullWidth(lay_dialog);
                setGravity(Gravity.BOTTOM);

                img_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(edt_picker.getText().toString().equals("")){
                            edt_picker.setError("Isi kolom email terlebih dahulu");
                        } else {
                            presenter.requestLupaPassword(edt_picker.getText().toString());
                        }
                    }
                });
            }
        });
        show();
    }

    @Override
    public void onRequestLupaPassword(boolean isExist) {
        if(isExist){
            LibUi.ToastShort(getContext(), "Pesan sudah terkirim ke email Anda, silahkan cek email");
            dismiss();
        } else {
            LibUi.ToastShort(getContext(), "Email tidak tersedia");
        }
    }

    @Override
    public void onLoading() {
        LibUi.showLoadingDialog(getContext(), R.drawable.ic_sand_clock);
    }


    @Override
    public void onHideLoading() {
        LibUi.hideLoadingDialog(getContext());
    }

    @Override
    public void onErrorConnection() {
        LibUi.ToastShort(getContext(), "Ada yang bermasalah dengan server");
    }
}
