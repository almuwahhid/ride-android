package id.ac.uny.riset.ride.utils.dialogs.DialogDetailPertanyaanSaya;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import id.ac.uny.riset.ride.R;
import id.ac.uny.riset.ride.data.model.TaskPertanyaanModel;
import id.ac.uny.riset.ride.utils.Function;
import lib.almuwahhid.utils.DialogBuilder;

public class DialogDetailPertanyaanSaya extends DialogBuilder {
    TextView tv_content, tv_status, tv_date, tv_tanggapan, tv_keterangan;
    ImageView img_close, img_status;
    RelativeLayout lay_dialog;
    LinearLayout lay_keterangan, lay_alasan;
    TaskPertanyaanModel taskPertanyaanModel;

    public DialogDetailPertanyaanSaya(Context context, final TaskPertanyaanModel taskPertanyaanModel) {
        super(context, R.layout.dialog_detail_pertanyaan_saya);
        this.taskPertanyaanModel = taskPertanyaanModel;

        initComponent(new OnInitComponent() {
            @Override
            public void initComponent(Dialog dialog) {
                tv_content = dialog.findViewById(R.id.tv_content);
                tv_status = dialog.findViewById(R.id.tv_status);
                tv_date = dialog.findViewById(R.id.tv_date);
                img_status = dialog.findViewById(R.id.img_status);
                img_close = dialog.findViewById(R.id.img_close);
                lay_dialog = dialog.findViewById(R.id.lay_dialog);
                tv_tanggapan = dialog.findViewById(R.id.tv_tanggapan);
                lay_keterangan = dialog.findViewById(R.id.lay_keterangan);
                lay_alasan = dialog.findViewById(R.id.lay_alasan);
                tv_keterangan = dialog.findViewById(R.id.tv_keterangan);

                img_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismiss();
                    }
                });

                setAnimation(R.style.DialogBottomAnimation);
                setFullWidth(lay_dialog);
                setGravity(Gravity.BOTTOM);

                tv_content.setText(taskPertanyaanModel.getIntervensi_task());
//                tv_keterangan.setText(taskPertanyaanModel.getKeterangan());
                if(taskPertanyaanModel.getKomentar_pertanyaan().equals("")){
                    tv_tanggapan.setText("Tidak ada alasan");
                    lay_alasan.setVisibility(View.GONE);
                } else {
                    tv_tanggapan.setText(taskPertanyaanModel.getKomentar_pertanyaan());
                }

                if(taskPertanyaanModel.getKeterangan().equals("")){
                    tv_keterangan.setText("Tidak ada keterangan");
                } else {
                    tv_keterangan.setText(taskPertanyaanModel.getKeterangan());
                }

                if(taskPertanyaanModel.getStatus_task().equals("N")){
                    if(Function.isToday(taskPertanyaanModel.getTanggal_task()).equals("today")){
                        img_status.setImageResource(R.drawable.ic_play_rounded_button);
                        tv_status.setText("Kerjakan task hari ini!");
                    } else if(Function.isToday(taskPertanyaanModel.getTanggal_task()).equals("yesterday")){
                        img_status.setImageResource(R.drawable.ic_error);
                        tv_status.setText("Task tidak dikerjakan");
                    } else if(Function.isToday(taskPertanyaanModel.getTanggal_task()).equals("tomorrow")){
                        tv_status.setText("Task belum dapat dikerjakan");
                        img_status.setImageResource(R.drawable.ic_stop);
                    }
                    try {
                        tv_date.setText(Function.parseTimestampToDate(taskPertanyaanModel.getTanggal_task().split(" ")[0]));
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                } else {
                    if(taskPertanyaanModel.getStatus_task().equals("T")){
                        img_status.setImageResource(R.drawable.ic_success_grey);
                        tv_status.setText("Anda tidak setuju dengan intervensi ini");
                    } else {
                        img_status.setImageResource(R.drawable.ic_success);
                        tv_status.setText("Anda setuju dengan intervensi ini");
                    }
                    try {
                        tv_date.setText(Function.parseTimestampToDate(taskPertanyaanModel.getTanggal_task().split(" ")[0])+" "+taskPertanyaanModel.getTanggal_submit().split(" ")[1]);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
        show();
    }
}
