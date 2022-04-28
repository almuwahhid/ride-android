package id.ac.uny.riset.ride.menu.register

import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import id.ac.uny.riset.ride.R
import id.ac.uny.riset.ride.base.BaseActivity
import id.ac.uny.riset.ride.data.StaticData
import id.ac.uny.riset.ride.data.model.UserModel
import id.ac.uny.riset.ride.utils.dialogs.dialogPicker.DialogPicker
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.app_bar_main.*
import lib.almuwahhid.utils.LibUi
import lib.almuwahhid.utils.LibUi.monthName
import java.util.*

class RegisterActivity : BaseActivity(), DatePickerDialog.OnDateSetListener, RegisterView.View {
    override fun onSubmitRegister(isSuccess: Boolean, message: String) {
        LibUi.ToastShort(context, message)
        if(isSuccess)
            finish()

    }

    override fun onLoading() {
        LibUi.showLoadingDialog(this, R.drawable.ic_sand_clock)
    }

    override fun onHideLoading() {
        LibUi.hideLoadingDialog(this)
    }

    override fun onErrorConnection() {

    }

    var userModel: UserModel = UserModel()
    var presenter: RegisterPresenter? = null

    var dialogJK: DialogPicker? = null
    var dialogAgama: DialogPicker? = null

    internal var forms: ArrayList<Int> = ArrayList()
    private fun setFormsToValidate() {
        forms.add(R.id.edt_user_email)
        forms.add(R.id.edt_password)
        forms.add(R.id.edt_nama_lengkap)
        forms.add(R.id.edt_user_bdate)
        forms.add(R.id.edt_alamat)
        forms.add(R.id.edt_jk)
        forms.add(R.id.edt_user_bdate)
        forms.add(R.id.edt_agama)
//        forms.add(R.id.edt_fakultas)
        forms.add(R.id.edt_wa)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setSupportActionBar(toolbar)
        supportActionBar.let {
            it!!.setDisplayHomeAsUpEnabled(true)
            it!!.setTitle("Mendaftar")
        }
        presenter = RegisterPresenter(context, this)

        setFormsToValidate()

        edt_user_bdate.setOnClickListener({
            val now = Calendar.getInstance()
//                now.add(Calendar.YEAR,-7);
            val dpd = DatePickerDialog.newInstance(
                    this@RegisterActivity,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            )
            dpd.maxDate = now
            dpd.firstDayOfWeek = Calendar.MONDAY
            dpd.accentColor = ContextCompat.getColor(context, R.color.primary)
            dpd.show(fragmentManager, "Tanggal Kejadian")
        })

        btn_register.setOnClickListener({
            validate()
        })

        edt_agama.setOnClickListener({
            initDialogAgama()
        })

        edt_jk.setOnClickListener({
            initDialogJk()
        })
    }

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        userModel.setTgl_lahir(year.toString() + "-" + (monthOfYear + 1) + "-" + dayOfMonth)
        edt_user_bdate.error = null
        edt_user_bdate.setText(dayOfMonth.toString() + " " + monthName(monthOfYear) + " " + year)
    }

    private fun validate() {
        if (LibUi.isFormValid(this, window.decorView, forms)) {
            userModel.setUserModel(edt_user_email.text.toString(),
                        edt_password.text.toString(),
                        edt_nama_lengkap.text.toString(),
                        edt_jk.text.toString(),
                        edt_fakultas.text.toString(),
                        edt_alamat_asal.text.toString(),
                        edt_alamat.text.toString(),
                        edt_agama.text.toString(),
                        edt_wa.text.toString())
            presenter!!.submitRegister(userModel)
        }
    }

    private fun initDialogJk(){
        if(dialogJK == null){
            dialogJK = DialogPicker(context, StaticData.dataJenisKelamin(), DialogPicker.OnDialogPicker {
                edt_jk.setText(it.name)
                edt_jk.setError(null)
            })
        } else {
            dialogJK!!.show()
        }
    }

    private fun initDialogAgama(){
        if(dialogAgama == null){
            dialogAgama = DialogPicker(context, StaticData.dataAgama(), DialogPicker.OnDialogPicker {
                edt_agama.setText(it.name)
                edt_agama.setError(null)
            })
        } else {
            dialogAgama!!.show()
        }
    }


}
