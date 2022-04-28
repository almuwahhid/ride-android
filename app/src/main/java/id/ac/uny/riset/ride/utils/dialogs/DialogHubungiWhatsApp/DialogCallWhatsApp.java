package id.ac.uny.riset.ride.utils.dialogs.DialogHubungiWhatsApp;

import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.telephony.PhoneNumberUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import id.ac.uny.riset.ride.R;
import lib.almuwahhid.utils.DialogBuilder;
import lib.almuwahhid.utils.LibUi;

public class DialogCallWhatsApp extends DialogBuilder {

    ImageView img_close;
    CardView card_wa, card_email;
    RelativeLayout lay_dialog;

    public DialogCallWhatsApp(Context context) {
        super(context, R.layout.dialog_call_whatsapp);

        initComponent(new OnInitComponent() {
            @Override
            public void initComponent(Dialog dialog) {
                card_wa = dialog.findViewById(R.id.card_wa);
                img_close = dialog.findViewById(R.id.img_close);
                card_email = dialog.findViewById(R.id.card_email);
                lay_dialog = dialog.findViewById(R.id.lay_dialog);

                setAnimation(R.style.DialogBottomAnimation);
                setFullWidth(lay_dialog);
                setGravity(Gravity.BOTTOM);

                img_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismiss();
                    }
                });

                card_wa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:085729626666"));
                        getContext().startActivity(intent);
                    }
                });

                card_email.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setType("message/rfc822");
                        i.putExtra(Intent.EXTRA_EMAIL, new String[]{"" + view.getTag(view.getId())});
                        i.putExtra(Intent.EXTRA_SUBJECT, "RIDE");
                        i.putExtra(Intent.EXTRA_TEXT, "");
                        try {
                            getActivity().startActivity(Intent.createChooser(i, "Kirim email"));
                        } catch (android.content.ActivityNotFoundException ex) {
                            LibUi.ToastShort(getActivity(), "Tidak ada aplikasi email terpasang");
                        }
                    }
                });
            }
        });

        show();
    }

    public void whatsapp(Activity activity, String phone) {

        try{
            Intent sendIntent = new Intent("android.intent.action.MAIN");
            sendIntent.setComponent(new  ComponentName("com.whatsapp","com.whatsapp.Conversation"));
            sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators("+6285729626666")+"@s.whatsapp.net");
            getContext().startActivity(sendIntent);
            activity.startActivity(sendIntent);
        }
        catch(Exception e)
        {
            Toast.makeText(activity,"Error/nWhatsapp tidak tersedia",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:085729626666"));
            getContext().startActivity(intent);
        }
    }
}
