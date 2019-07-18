package id.ac.uny.riset.ride.menu.login

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.google.gson.Gson
import id.ac.uny.riset.ride.R
import id.ac.uny.riset.ride.data.Preferences
import id.ac.uny.riset.ride.data.model.UserModel
import id.ac.uny.riset.ride.menu.login.model.LoginUiModel
import id.ac.uny.riset.ride.menu.main.MainActivity
import id.ac.uny.riset.ride.menu.panduan.PanduanActivity
import id.ac.uny.riset.ride.menu.register.RegisterActivity
import id.ac.uny.riset.ride.utils.Function
import id.ac.uny.riset.ride.utils.dialogs.DialogLupaPassword.DialogLupaPassword
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.layout_helper.*
import lib.almuwahhid.utils.LibUi
import java.util.ArrayList

class LoginActivity : AppCompatActivity(), LoginView.View {

    var presenter: LoginPresenter? = null;
    var gson: Gson? = null;

    override fun onSuccessLogin(model: UserModel) {
        Function.setUserPreference(this, gson!!.toJson(model))
        if(LibUi.getSPString(baseContext, Preferences.USER_INTRO).equals("")){
            LibUi.setSPString(baseContext, Preferences.USER_INTRO, "ok")
            startActivity(Intent(this, PanduanActivity::class.java))
            finish()
        } else {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
//        startActivity(Intent(this, MainActivity::class.java))
//        finish()
    }

    override fun onFailedLogin(message: String) {
        tv_loading_top.setText(message)
        helper_loading_top.showForAWhile(this)
    }

    override fun onLoading() {
        LibUi.showLoadingDialog(this, R.drawable.ic_sand_clock)
    }

    override fun onHideLoading() {
        LibUi.hideLoadingDialog(this)
    }

    override fun onErrorConnection() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        gson = Gson()

        if(Function.checkUserPreference(this)){
            if(LibUi.getSPString(baseContext, Preferences.USER_INTRO).equals("")){
                LibUi.setSPString(baseContext, Preferences.USER_INTRO, "ok")
                startActivity(Intent(this, PanduanActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        presenter = LoginPresenter(this, this)
        setFormsToValidate()

        helper_loading_top.setInOutAnimation(R.anim.pull_in_bottom, R.anim.push_out_top)

        /*setSupportActionBar(toolbar)
        supportActionBar.let {
            it!!.setDisplayHomeAsUpEnabled(true)
        }*/

        YoYo.with(Techniques.FadeIn)
                .duration(1000)
                .repeat(0)
                .playOn(logo)

        val handler = Handler()
        handler.postDelayed({
            lay_login.show()
        }, 2000)

        tv_daftar.setOnClickListener({
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        })

        tv_lupapassword.setOnClickListener({
            DialogLupaPassword(this)
        })

        btn_login.setOnClickListener({
            validate()
        })
    }

    internal var forms: MutableList<Int> = ArrayList()
    private fun setFormsToValidate() {
        forms.add(R.id.edt_password)
        forms.add(R.id.edt_username)
    }

    private fun validate() {
        if (LibUi.isFormValid(this, window.decorView, forms)) {
            presenter!!.requestLogin(LoginUiModel(edt_username.text.toString(), edt_password.text.toString()))
        }
    }
}
