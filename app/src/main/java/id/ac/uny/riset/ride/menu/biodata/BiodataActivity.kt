package id.ac.uny.riset.ride.menu.biodata

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import com.google.gson.Gson
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import id.ac.uny.riset.ride.BuildConfig
import id.ac.uny.riset.ride.R
import id.ac.uny.riset.ride.base.BaseActivity
import id.ac.uny.riset.ride.data.StaticData
import id.ac.uny.riset.ride.data.model.UserModel
import id.ac.uny.riset.ride.utils.Function
import id.ac.uny.riset.ride.utils.avatarview.AvatarPlaceholder
import id.ac.uny.riset.ride.utils.avatarview.loader.PicassoLoader
import id.ac.uny.riset.ride.utils.dialogs.dialogPicker.DialogPicker
import kotlinx.android.synthetic.main.activity_biodata.*
import kotlinx.android.synthetic.main.app_bar_main.*
import lib.almuwahhid.utils.LibUi
import java.util.*

class BiodataActivity : BaseActivity(), DatePickerDialog.OnDateSetListener, BiodataView.View {
    val gson: Gson = Gson()

    override fun onRequestUser(userModel: String) {
        LibUi.ToastShort(context, "Berhasil update biodata")
        Function.setUserPreference(context, userModel)
        initData(Function.getUserPreference(context))
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
    var dialogJK: DialogPicker? = null
    var dialogAgama: DialogPicker? = null

    lateinit var presenter : BiodataPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_biodata)

        setSupportActionBar(toolbar)
        supportActionBar.let {
            it!!.setDisplayHomeAsUpEnabled(true)
            it!!.setTitle("Biodata")
        }

        presenter = BiodataPresenter(context, this)

        initData(Function.getUserPreference(context))

        setFormsToValidate()

        edt_user_bdate.setOnClickListener({
            val now = Calendar.getInstance()
//                now.add(Calendar.YEAR,-7);
            val dpd = DatePickerDialog.newInstance(
                    this@BiodataActivity,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            )
            dpd.maxDate = now
            dpd.firstDayOfWeek = Calendar.MONDAY
            dpd.accentColor = ContextCompat.getColor(context, R.color.primary)
            dpd.show(fragmentManager, "Tanggal Kejadian")
        })

        edt_agama.setOnClickListener({
            initDialogAgama()
        })

        edt_jk.setOnClickListener({
            initDialogJk()
        })

        btn_register.setOnClickListener({
            validate()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.laporan_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            android.R.id.home -> {
                finish()
            }
            R.id.action_download -> {
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(BuildConfig.base_url+"laporan/laporanbiodata?data="+gson.toJson(Function.getUserPreference(context)))
                startActivity(i)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        userModel.setTgl_lahir(year.toString() + "-" + (monthOfYear + 1) + "-" + dayOfMonth)
        edt_user_bdate.error = null
        edt_user_bdate.setText(dayOfMonth.toString() + " " + LibUi.monthName(monthOfYear) + " " + year)
    }

    private fun validate() {
        if (LibUi.isFormValid(this, window.decorView, forms)) {
            userModel.setUserModel(Function.getUserPreference(context).email,
                    Function.getUserPreference(context).password,
                    edt_nama_lengkap.text.toString(),
                    edt_jk.text.toString(),
                    edt_fakultas.text.toString(),
                    edt_alamat_asal.text.toString(),
                    edt_alamat.text.toString(),
                    edt_agama.text.toString(),
                    edt_wa.text.toString())
            presenter!!.updateUser(userModel)
        }
    }

    private fun initData(userModel: UserModel){
        this.userModel = userModel
        edt_alamat.setText(userModel.getAlamat_tinggal())
        edt_alamat_asal.setText(userModel.getAlamat_asal())
        edt_nama_lengkap.setText(userModel.getNama())
        edt_jk.setText(userModel.getJenis_kelamin())
        edt_jk.setText(userModel.getJenis_kelamin())
        edt_user_bdate.setText(Function.parseDateStringToDate(userModel.getTgl_lahir()))
        edt_agama.setText(userModel.getAgama())
        edt_fakultas.setText(userModel.getFakultas())
        edt_wa.setText(userModel.getNo_wa())

        val imageLoader = PicassoLoader()
        val refreshableAvatarPlaceholder = AvatarPlaceholder(Function.getUserPreference(context).nama)
        imageLoader.loadImage(avatarView, refreshableAvatarPlaceholder, "http://google.com")

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

    internal var forms: ArrayList<Int> = ArrayList()
    private fun setFormsToValidate() {
        forms.add(R.id.edt_nama_lengkap)
        forms.add(R.id.edt_user_bdate)
        forms.add(R.id.edt_alamat)
        forms.add(R.id.edt_jk)
        forms.add(R.id.edt_user_bdate)
        forms.add(R.id.edt_agama)
//        forms.add(R.id.edt_fakultas)
        forms.add(R.id.edt_wa)
    }

}
