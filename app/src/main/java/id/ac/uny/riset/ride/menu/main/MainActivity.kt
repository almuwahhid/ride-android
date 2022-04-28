package id.ac.uny.riset.ride.menu.main

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.design.internal.NavigationMenuView
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import id.ac.uny.riset.ride.R
import id.ac.uny.riset.ride.Service.AlarmReceiver
import id.ac.uny.riset.ride.Service.CheckIntervensiJovScheduler
import id.ac.uny.riset.ride.base.BaseActivity
import id.ac.uny.riset.ride.data.Preferences
import id.ac.uny.riset.ride.data.model.SurveyModel
import id.ac.uny.riset.ride.data.model.TaskPertanyaanModel
import id.ac.uny.riset.ride.menu.about.AboutActivity
import id.ac.uny.riset.ride.menu.biodata.BiodataActivity
import id.ac.uny.riset.ride.menu.login.LoginActivity
import id.ac.uny.riset.ride.menu.main.adapter.AdapterTaskPertanyaan
import id.ac.uny.riset.ride.menu.panduan.PanduanActivity
import id.ac.uny.riset.ride.menu.survey.SurveyActivity
import id.ac.uny.riset.ride.menu.surveySaya.SurveySayaActivity
import id.ac.uny.riset.ride.utils.Function
import id.ac.uny.riset.ride.utils.avatarview.AvatarPlaceholder
import id.ac.uny.riset.ride.utils.avatarview.loader.PicassoLoader
import id.ac.uny.riset.ride.utils.avatarview.views.AvatarView
import id.ac.uny.riset.ride.utils.dialogs.DialogConfirm.DialogConfirm
import id.ac.uny.riset.ride.utils.dialogs.DialogHubungiWhatsApp.DialogCallWhatsApp
import id.ac.uny.riset.ride.utils.dialogs.DialogSubmitTaskPertanyaan.DialogSubmitTaskPertanyaan
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import lib.almuwahhid.utils.LibUi

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener, MainView.View{
    lateinit var adapterTaskPertanyaan: AdapterTaskPertanyaan
    lateinit var intervensiModel: TaskPertanyaanModel
    lateinit var presenter: MainPresenter
    var fromRefresh: Boolean = false

    lateinit var taskIntervensiModels: MutableList<TaskPertanyaanModel>
    var dialogConfirm: DialogConfirm? = null

    private var alarmReceiver: AlarmReceiver? = null

    override fun onCheckNilai(nilai: String) {
        tv_nilai.setText("Poin : "+nilai)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        presenter = MainPresenter(context, this)
        lay_wa.setInOutAnimation(R.anim.pull_in_top, R.anim.push_out_bottom)

        taskIntervensiModels = ArrayList()
        adapterTaskPertanyaan = AdapterTaskPertanyaan(context, taskIntervensiModels, object : AdapterTaskPertanyaan.OnAdapterPertanyaan{
            override fun onPertanyaanClick(intvrl: TaskPertanyaanModel) {
                if(intvrl.status_task.equals("N")){
                    if(Function.isToday(intvrl.tanggal_task).equals("today")){
                        DialogSubmitTaskPertanyaan(context, intvrl, DialogSubmitTaskPertanyaan.OnDialogSubmitPertanyaan {
                            fromRefresh = true
                            presenter.checkSurvey()
                            if(it)
                                initDialogConfirm("Tampaknya seluruh tugas sudah kamu kerjakan, kamu bisa melakukan survey kembali besok ya", "Terima kasih banyak", R.drawable.ic_success, object : DialogConfirm.OnDialogConfirm{
                                    override fun onCancelConfirmClick() {

                                    }

                                    override fun onDialogConfirmClick() {

                                    }

                                })
                        })
                    } else if(Function.isToday(intvrl.tanggal_task).equals("yesterday")){
                        LibUi.ToastShort(context, "Tugas sudah berlalu, Anda tidak dapat melakukannya kembali")
                    } else if(Function.isToday(intvrl.tanggal_task).equals("tomorrow")){
                        LibUi.ToastShort(context, "Belum masuk waktu mengerjakan tugas")
                    }
                } else {
                    LibUi.ToastShort(context, "Tugas sudah dikerjakan, Terima kasih")
                }
            }
        })
        rv_pertanyaan.layoutManager = LinearLayoutManager(context)
        rv_pertanyaan.adapter = adapterTaskPertanyaan

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()



        nav_view.setNavigationItemSelectedListener(this)
        nav_view.itemIconTintList = null

        val navigationMenuView = nav_view.getChildAt(0) as NavigationMenuView
        if (navigationMenuView != null) {
            navigationMenuView.isVerticalScrollBarEnabled = false
        }

        btn_survey.setOnClickListener({
            presenter.createSurvey()
        })
        swipe.setOnRefreshListener {
            fromRefresh = true
            presenter.checkSurvey()
        }
        presenter.checkSurvey()

        img_close.setOnClickListener({
            lay_alert.visibility = View.GONE
        })

        alarmReceiver = AlarmReceiver()
        if(!alarmReceiver!!.isAlarmSet(context, AlarmReceiver.TYPE_REPEATING)){
            alarmReceiver!!.setRepeatingAlarm(this, AlarmReceiver.TYPE_REPEATING,"");
        }
    }

    override fun onResume() {
        super.onResume()

        //set name
        val navView = nav_view.getHeaderView(0)
        (navView.findViewById(R.id.tv_name) as TextView).setText(Function.getUserPreference(context).nama)
        (navView.findViewById(R.id.tv_email) as TextView).setText(Function.getUserPreference(context).email)
        (navView.findViewById(R.id.lay_bio) as LinearLayout).setOnClickListener({
            startActivity(Intent(context, BiodataActivity::class.java))
        })
        val avatarView = navView.findViewById(R.id.avatarView) as AvatarView
        val imageLoader = PicassoLoader()
        val refreshableAvatarPlaceholder = AvatarPlaceholder(Function.getUserPreference(context).nama)
        imageLoader.loadImage(avatarView, refreshableAvatarPlaceholder, "http://google.com")
    }

    override fun onFailed(message: String) {
        LibUi.ToastShort(context, message)
    }

    override fun onDoSurvey(surveyModel: SurveyModel, taskIntervensiModels: MutableList<TaskPertanyaanModel>) {
        tv_survey_title.setText(Function.parseTimestampToDate(surveyModel.tanggal_survey))
        this.taskIntervensiModels!!.clear()
        this.taskIntervensiModels!!.addAll(taskIntervensiModels)
        adapterTaskPertanyaan.notifyDataSetChanged()
        Log.d("MainActivityClass ", ""+taskIntervensiModels.size)
        lay_first.visibility = View.GONE
        if(!fromRefresh)
            initDialogConfirm("Rider, kamu memiliki beberapa tugas yang harus dilakukan loh! Yuk mulai!", "Okay, akan kumulai!", R.drawable.ic_man_thinking, object : DialogConfirm.OnDialogConfirm{
                override fun onCancelConfirmClick() {

                }

                override fun onDialogConfirmClick() {

                }

            })
    }

    override fun onAfterFirstSurvey() {
        taskIntervensiModels!!.clear()
        adapterTaskPertanyaan.notifyDataSetChanged()
        lay_first.visibility = View.VISIBLE
        tv_content.setText("Selamat, kamu telah menyelesaikan sekali, Yuk ikutan survey sekali lagi !")
        btn_survey.setText("Ya, Lakukan survey sekarang")
        img_content.setImageResource(R.drawable.ic_rating)
    }

    override fun onAfterSecondSurvey() {
        taskIntervensiModels!!.clear()
        adapterTaskPertanyaan.notifyDataSetChanged()
        lay_first.visibility = View.VISIBLE
        tv_content.setText("Selamat, kamu telah menyelesaikan survey wajib, Apakah kamu akan melakukan survey kembali?")
        btn_survey.setText("Ya, Lakukan survey sekarang")
        img_content.setImageResource(R.drawable.ic_rating)

        card_wa.setOnClickListener {
            /*val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:085729626666")
            context.startActivity(intent)*/
            DialogCallWhatsApp(context)
        }

        DialogCallWhatsApp(context)

        if(!lay_wa.isShow){
            lay_wa.show()
        }
    }

    override fun onStartSurvey() {
        taskIntervensiModels!!.clear()
        adapterTaskPertanyaan.notifyDataSetChanged()
        lay_first.visibility = View.VISIBLE
        tv_content.setText("Sepertinya kamu pengguna baru, yuk ikuti survey sekarang")
        btn_survey.setText("Survey sekarang")
    }

    override fun onConfirmTask(taskIntervensiModel: TaskPertanyaanModel) {

    }

    override fun onLoading() {
        LibUi.showLoadingDialog(this, R.drawable.ic_sand_clock)
    }

    override fun onHideLoading() {
        swipe.isRefreshing = false
        LibUi.hideLoadingDialog(this)
    }

    override fun onErrorConnection() {
        LibUi.ToastShort(context, "Ada yang bermasalah dengan server")
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    /*override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }*/

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_daftar_survey -> {
                startActivity(Intent(context, PanduanActivity::class.java))
            }
            R.id.nav_survey_saya -> {
                startActivity(Intent(context, SurveySayaActivity::class.java))
            }
            R.id.nav_info -> {
                startActivity(Intent(context, AboutActivity::class.java))
            }
            R.id.nav_call -> {
                DialogCallWhatsApp(context)
            }
            R.id.nav_logout -> {
                alarmReceiver!!.cancelAlarm(this, AlarmReceiver.TYPE_REPEATING);

                LibUi.setSPString(baseContext, Preferences.USER_INTRO, "")
                Function.logoutUser(context)
                startActivity(Intent(context, LoginActivity::class.java))
                finish()
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun initDialogConfirm(title: String, btn_title: String, img_source : Int, onDialogConfirm: DialogConfirm.OnDialogConfirm){
        dialogConfirm = DialogConfirm(context, title, btn_title, img_source, onDialogConfirm)
//        if(dialogConfirm == null){
//            dialogConfirm = DialogConfirm(context, title, btn_title, img_source, onDialogConfirm)
//        } else {
//            dialogConfirm!!.setComponent(title, btn_title, img_source, onDialogConfirm)
//            dialogConfirm!!.show()
//        }
    }

    override fun onCreateSurvey(surveyModel: SurveyModel) {
        startActivity(Intent(context, SurveyActivity::class.java).putExtra("data", surveyModel))
    }

    private val JOB_ID = 10

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun isJobRunning(context: Context): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            var isScheduled = false;
            var scheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler

            if (scheduler != null) {
                for (jobInfo in scheduler.getAllPendingJobs()) {
                    if (jobInfo.getId() == JOB_ID) {
                        isScheduled = true;
                        break;
                    }
                }
            }
            return isScheduled;
        } else {
            return false
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun startJob() {

        /*
        Cek running job terlebih dahulu
         */
        if (isJobRunning(this)) {
//            Toast.makeText(this, "Job Service is already scheduled", Toast.LENGTH_SHORT).show();
            return;
        }


        val mServiceComponent = ComponentName(this, CheckIntervensiJovScheduler::class.java)
        val builder = JobInfo.Builder(JOB_ID, mServiceComponent);

        /*
        Kondisi network,
        NETWORK_TYPE_ANY, berarti tidak ada ketentuan tertentu
        NETWORK_TYPE_UNMETERED, adalah network yang tidak dibatasi misalnya wifi
        */
        builder!!.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);

        /*
        Kondisi device, secara default sudah pada false
        false, berarti device tidak perlu idle ketika job ke trigger
        true, berarti device perlu dalam kondisi idle ketika job ke trigger
        */
        builder!!.setRequiresDeviceIdle(false);

        /*
        Kondisi charging
        false, berarti device tidak perlu di charge
        true, berarti device perlu dicharge
        */
        builder!!.setRequiresCharging(false);

        /*
        Periode interval sampai ke trigger
        Dalam milisecond, 1000ms = 1detik
        */
        builder!!.setPeriodic(180000);

        val scheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        if (scheduler != null) {
            scheduler!!.schedule(builder.build());
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
     private fun cancelJob() {
        val scheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
         if (scheduler != null) {
            scheduler.cancel(JOB_ID);
        }
//        Toast.makeText(this, "Job Service canceled", Toast.LENGTH_SHORT).show();
    }
}
