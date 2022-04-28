package id.ac.uny.riset.ride.menu.splashscreen

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import id.ac.uny.riset.ride.R
import id.ac.uny.riset.ride.data.Preferences
import lib.almuwahhid.utils.LibUi
import id.ac.uny.riset.ride.menu.login.LoginActivity
import id.ac.uny.riset.ride.menu.main.MainActivity

class SplashActivity : AppCompatActivity() {
    internal var timer: Thread? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        initTimer()
        timer!!.start()
    }

    private fun initTimer() {
        timer = object : Thread() {
            override fun run() {
                try {
                    //Create the database
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    if(LibUi.getSPString(baseContext, Preferences.USER_INTRO).equals("")){
                        startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                    } else {
                        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    }

                    finish()
                }
            }
        }
    }
}
