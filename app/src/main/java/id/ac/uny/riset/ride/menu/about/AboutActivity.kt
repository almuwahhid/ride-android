package id.ac.uny.riset.ride.menu.about

import android.os.Bundle
import android.view.MenuItem
import id.ac.uny.riset.ride.BuildConfig

import id.ac.uny.riset.ride.base.BaseActivity
import kotlinx.android.synthetic.main.activity_about.*
import kotlinx.android.synthetic.main.main_toolbar.*
import android.support.v4.content.res.ResourcesCompat
import android.graphics.Typeface
import id.ac.uny.riset.ride.R


class AboutActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        setSupportActionBar(toolbar)
        supportActionBar!!.setTitle("About")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        tv_appname.setText("RIDE versi "+BuildConfig.VERSION_NAME)

        val typeface = Typeface.create("cursive", Typeface.NORMAL)
        tv_tagline.setTypeface(typeface)
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
