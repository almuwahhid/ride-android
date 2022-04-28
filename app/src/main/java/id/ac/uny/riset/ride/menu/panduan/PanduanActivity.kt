package id.ac.uny.riset.ride.menu.panduan

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.github.paolorotolo.appintro.AppIntro2
import com.github.paolorotolo.appintro.AppIntroFragment
import id.ac.uny.riset.ride.R
import id.ac.uny.riset.ride.menu.main.MainActivity

class PanduanActivity: AppIntro2() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addSlide(AppIntroFragment.newInstance("Ukur Status Identitasmu!",
                "Coba cari tahu profil status identitas religiusmu melalui skala pengukuran status identitas religious pada Aplikasi RIDE ini ya, Rider!", R.drawable.ic_survey, ContextCompat.getColor(baseContext, R.color.green_300)));
        addSlide(AppIntroFragment.newInstance("Task Intervensi Harian!",
                "Tingkatkan dan kembangkan identitas religious yang kamu miliki melalui beberapa tugas-tugas yang harus kamu lakukan setiap harinya pada Aplikasi RIDE ini ya, Rider!", R.drawable.ic_text, ContextCompat.getColor(baseContext, R.color.brown_300)));
        addSlide(AppIntroFragment.newInstance("Laporan Pengukuran!",
                "Lihat perkembangan profil status identitas religiusmu yang dituangkan dalam bentuk grafik pada Aplikasi RIDE ini ya, Rider!", R.drawable.ic_graph, ContextCompat.getColor(baseContext, R.color.blue_300)));

        setSlideOverAnimation()

    }

    override fun onDonePressed() {
        super.onDonePressed()
        startActivity(Intent(baseContext, MainActivity::class.java))
        finish()
    }
}